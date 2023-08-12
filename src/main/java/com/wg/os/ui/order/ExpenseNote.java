/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wg.os.ui.order;

import com.mongodb.client.result.DeleteResult;
import com.wg.os.common.Global;
import com.wg.os.document.ExpNote;
import com.wg.os.document.Expense;
import com.wg.os.service.ExpenseNoteService;
import com.wg.os.service.ExpenseService;
import com.wg.os.ui.ApplicationMainFrame;
import com.wg.os.ui.common.ExpenseNoteTabelModel;
import com.wg.os.ui.common.SelectionObserver;
import com.wg.os.ui.setup.ExpenseSetup;
import com.wg.os.util.BindingUtil;
import com.wg.os.util.NumberUtil;
import com.wg.os.util.Util1;
import java.awt.Dialog;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Date;
import java.util.List;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.event.ListSelectionEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author Lenovo
 */
@Component
public class ExpenseNote extends javax.swing.JPanel implements KeyListener, SelectionObserver {

    private static final Logger LOGGER = LoggerFactory.getLogger(ExpenseNote.class);

    private ExpNote expNote;
    private int selectRow = -1;
    List<Expense> listExp;
    /**
     * Creates new form Expense
     */
    @Autowired
    private ExpenseNoteTabelModel expenseNoteTabelModel;
    @Autowired
    private ExpenseNoteService expenseNoteService;
    @Autowired
    private ExpenseService expenseService;
    @Autowired
    private ExpenseSetup expenseSetup;
    @Autowired
    private ApplicationMainFrame applicationMainFrame;

    public ExpenseNote() {
        initComponents();
    }

    private void setTodayDate() {
        txtExpDate.setDate(Util1.getTodayDate());
        txtFromDate.setDate(Util1.getTodayDate());
        txtToDate.setDate(Util1.getTodayDate());
    }

    public void initTable() {
        setLookAndFeel();
        setCurrentFont();
        setControl();
        setTodayDate();
        initCombo();
        tblExpense.setModel(expenseNoteTabelModel);
        tblExpense.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tblExpense.getSelectionModel().addListSelectionListener((ListSelectionEvent e) -> {
            if (e.getValueIsAdjusting()) {
                selectRow = tblExpense.convertRowIndexToModel(tblExpense.getSelectedRow());
                ExpNote expense = expenseNoteTabelModel.getExpense(selectRow);
                setExpense(expense);
            }
        });
        tblExpense.getInputMap().put(KeyStroke.getKeyStroke("DELETE"), "Del-Action");
        tblExpense.getActionMap().put("Del-Action", actionItemDelete);

    }

    private void setExpense(ExpNote e) {
        labelStatus.setText("EDIT");
        txtExpDate.setDate(e.getExpDate());
        cboExpense.setSelectedItem(e.getExp());
        txtAmount.setText(String.valueOf(e.getAmt()));

    }

    private void setLookAndFeel() {
        try {
            UIManager.setLookAndFeel(Global.setting.getTheme().getPath());
            SwingUtilities.updateComponentTreeUI(this);
        } catch (UnsupportedLookAndFeelException e) {
            LOGGER.error("Look and Feel :" + e.getMessage());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException ex) {
            LOGGER.error("Look And Feel :" + ex.getMessage());
        }
    }
    private final Action actionItemDelete = new AbstractAction() {

        @Override
        public void actionPerformed(ActionEvent e) {
            int row = tblExpense.getSelectedRow();

            if (row >= 0) {
                delete();
            }
        }
    };

    public void delete() {
        int row = tblExpense.getSelectedRow();
        if (row >= 0) {
            int showConfirmDialog = JOptionPane.showConfirmDialog(Global.parentFrame, "Are you sure to Delete");
            if (showConfirmDialog == JOptionPane.YES_OPTION) {
                ExpNote exp = expenseNoteTabelModel.getExpense(row);
                DeleteResult remove = expenseNoteService.remove(exp.getId());
                if (remove.getDeletedCount() == 1) {
                    expenseNoteTabelModel.deleteExpense(row);
                    clear();
                }
            }
        } else {
            JOptionPane.showMessageDialog(Global.parentFrame, "Select row you want to to delete...");
        }
    }

    private void initCombo() {
        listExp = expenseService.findAll();
        BindingUtil.BindComboFilter(cboExpense, listExp, null, true, false);
    }

    public void save() {
        if (isValidEntry()) {
            if (labelStatus.getText().equals("EDIT")) {
                ExpNote expense = expenseNoteTabelModel.getExpense(selectRow);
                expNote.setId(expense.getId());
            }
            ExpNote save = expenseNoteService.save(expNote);
            if (!save.getId().isEmpty()) {
                if (labelStatus.getText().equals("EDIT")) {
                    expenseNoteTabelModel.setExpense(selectRow, save);
                } else {
                    expenseNoteTabelModel.addExpense(save);
                }
                JOptionPane.showMessageDialog(Global.parentFrame, "Expense Saved");
                clear();
            }
        }

    }

    public void clearAll() {
        clear();
        expenseNoteTabelModel.clear();
    }

    private void clear() {
        setTodayDate();
        labelStatus.setText("NEW");
        txtAmount.setText("");
    }

    private void search() {
        Date stDate = txtFromDate.getDate();
        Date enDate = txtToDate.getDate();
        List<ExpNote> listExpNote = expenseNoteService.search(stDate, enDate);
        expenseNoteTabelModel.setListExp(listExpNote);
        double ttlAmt = 0.0;
        for (ExpNote ex : listExpNote) {
            ttlAmt += ex.getAmt();
        }
        lblTotalItem.setText(String.valueOf(listExp.size()));
        lblTotalAmt.setText(String.valueOf(ttlAmt));
    }

    private boolean isValidEntry() {
        boolean status;
        String amt = txtAmount.getText();
        Expense exp = (Expense) cboExpense.getSelectedItem();
        Date expDate = txtExpDate.getDate();
        if (amt.isEmpty() || !NumberUtil.isNumber(amt)) {
            JOptionPane.showMessageDialog(Global.parentFrame, "Invalid Amount Entry...");
            status = false;
        } else {
            expNote = new ExpNote();
            expNote.setExpDate(expDate);
            expNote.setExp(exp);
            expNote.setAmt(Double.valueOf(amt));
            status = true;
        }
        return status;
    }

    private void setExpense() {
        JDialog dialog = new JDialog(Global.parentFrame, Dialog.ModalityType.DOCUMENT_MODAL);
        ImageIcon size = new ImageIcon(getClass().getResource("/image/expense.png"));
        dialog.setIconImage(size.getImage());
        dialog.setTitle("Expense Setup");
        expenseSetup.initTabel();
        expenseSetup.setObserver(this);
        dialog.add(expenseSetup);
        dialog.setSize(490, 420);
        dialog.setResizable(false);
        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);

    }

    public void setControl() {
        applicationMainFrame.setEditableSave(true);
        applicationMainFrame.setEditableNew(true);
        applicationMainFrame.setEditableDelete(true);
        applicationMainFrame.setEditableHistory(false);
    }

    private void setCurrentFont() {
        java.awt.Component[] filterComps = panelFilter.getComponents();
        for (java.awt.Component filterComp : filterComps) {
            filterComp.setFont(Global.font);
        }
        java.awt.Component[] expComps = panelExpense.getComponents();
        for (java.awt.Component expComp : expComps) {
            expComp.setFont(Global.font);
        }
        java.awt.Component[] entryComps = panelEntry.getComponents();
        for (java.awt.Component entryComp : entryComps) {
            entryComp.setFont(Global.font);
        }
        tblExpense.setFont(Global.font);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelEntry = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txtExpDate = new com.toedter.calendar.JDateChooser();
        jLabel2 = new javax.swing.JLabel();
        cboExpense = new javax.swing.JComboBox<>();
        jLabel3 = new javax.swing.JLabel();
        txtAmount = new javax.swing.JTextField();
        labelStatus = new javax.swing.JLabel();
        btnAdd = new javax.swing.JButton();
        panelExpense = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblExpense = new javax.swing.JTable();
        jLabel6 = new javax.swing.JLabel();
        lblTotalItem = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        lblTotalAmt = new javax.swing.JLabel();
        panelFilter = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        txtFromDate = new com.toedter.calendar.JDateChooser();
        jLabel5 = new javax.swing.JLabel();
        txtToDate = new com.toedter.calendar.JDateChooser();
        btnFind = new javax.swing.JButton();

        panelEntry.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Data Entry"));

        jLabel1.setFont(Global.font);
        jLabel1.setText("Date");

        txtExpDate.setDateFormatString("dd-MM-yyyy");
        txtExpDate.setFont(Global.font);

        jLabel2.setFont(Global.font);
        jLabel2.setText("Expense");

        cboExpense.setFont(Global.font);

        jLabel3.setFont(Global.font);
        jLabel3.setText("Amount");

        txtAmount.setFont(Global.font);
        txtAmount.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtAmountActionPerformed(evt);
            }
        });

        labelStatus.setFont(Global.font);
        labelStatus.setText("NEW");

        btnAdd.setText("+");
        btnAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelEntryLayout = new javax.swing.GroupLayout(panelEntry);
        panelEntry.setLayout(panelEntryLayout);
        panelEntryLayout.setHorizontalGroup(
            panelEntryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelEntryLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelEntryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(labelStatus, javax.swing.GroupLayout.DEFAULT_SIZE, 136, Short.MAX_VALUE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 136, Short.MAX_VALUE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelEntryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtAmount, javax.swing.GroupLayout.DEFAULT_SIZE, 259, Short.MAX_VALUE)
                    .addComponent(txtExpDate, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 259, Short.MAX_VALUE)
                    .addGroup(panelEntryLayout.createSequentialGroup()
                        .addComponent(cboExpense, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnAdd)))
                .addContainerGap())
        );
        panelEntryLayout.setVerticalGroup(
            panelEntryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelEntryLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelEntryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtExpDate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(panelEntryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(panelEntryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(cboExpense)
                        .addComponent(btnAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(17, 17, 17)
                .addGroup(panelEntryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtAmount)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(19, 19, 19)
                .addComponent(labelStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        panelEntryLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {btnAdd, cboExpense, txtAmount, txtExpDate});

        panelExpense.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Expense", javax.swing.border.TitledBorder.LEFT, javax.swing.border.TitledBorder.DEFAULT_POSITION, Global.font));

        tblExpense.setFont(Global.font);
        tblExpense.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tblExpense.setRowHeight(22);
        jScrollPane1.setViewportView(tblExpense);

        jLabel6.setFont(Global.font);
        jLabel6.setText("Total Item :");

        lblTotalItem.setFont(Global.font);
        lblTotalItem.setText("0.0");

        jLabel7.setFont(Global.font);
        jLabel7.setText("Total Amt :");

        lblTotalAmt.setFont(Global.font);
        lblTotalAmt.setText("0.0");

        javax.swing.GroupLayout panelExpenseLayout = new javax.swing.GroupLayout(panelExpense);
        panelExpense.setLayout(panelExpenseLayout);
        panelExpenseLayout.setHorizontalGroup(
            panelExpenseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 416, Short.MAX_VALUE)
            .addGroup(panelExpenseLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblTotalItem, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblTotalAmt, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        panelExpenseLayout.setVerticalGroup(
            panelExpenseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelExpenseLayout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 379, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelExpenseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel7)
                    .addGroup(panelExpenseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(lblTotalItem, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(lblTotalAmt, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );

        panelExpenseLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jLabel6, jLabel7});

        panelFilter.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Filter", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, Global.font));

        jLabel4.setFont(Global.font);
        jLabel4.setText("From");

        txtFromDate.setDateFormatString("dd-MM-yyyy");
        txtFromDate.setFont(Global.font);

        jLabel5.setFont(Global.font);
        jLabel5.setText("To");

        txtToDate.setDateFormatString("dd-MM-yyyy");
        txtToDate.setFont(Global.font);

        btnFind.setFont(Global.font);
        btnFind.setText("Find");
        btnFind.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFindActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelFilterLayout = new javax.swing.GroupLayout(panelFilter);
        panelFilter.setLayout(panelFilterLayout);
        panelFilterLayout.setHorizontalGroup(
            panelFilterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelFilterLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtFromDate, javax.swing.GroupLayout.DEFAULT_SIZE, 103, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel5)
                .addGap(18, 18, 18)
                .addComponent(txtToDate, javax.swing.GroupLayout.DEFAULT_SIZE, 108, Short.MAX_VALUE)
                .addGap(37, 37, 37)
                .addComponent(btnFind)
                .addContainerGap())
        );
        panelFilterLayout.setVerticalGroup(
            panelFilterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelFilterLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelFilterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtToDate, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtFromDate, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnFind, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addContainerGap(25, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panelExpense, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panelFilter, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(0, 0, 0)
                .addComponent(panelEntry, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(panelFilter, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(panelExpense, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(panelEntry, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void txtAmountActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtAmountActionPerformed
        // TODO add your handling code here:
        save();
    }//GEN-LAST:event_txtAmountActionPerformed

    private void btnFindActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFindActionPerformed
        // TODO add your handling code here:
        search();

    }//GEN-LAST:event_btnFindActionPerformed

    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddActionPerformed
        // TODO add your handling code here:
        setExpense();

    }//GEN-LAST:event_btnAddActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnFind;
    private javax.swing.JComboBox<String> cboExpense;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel labelStatus;
    private javax.swing.JLabel lblTotalAmt;
    private javax.swing.JLabel lblTotalItem;
    private javax.swing.JPanel panelEntry;
    private javax.swing.JPanel panelExpense;
    private javax.swing.JPanel panelFilter;
    private javax.swing.JTable tblExpense;
    private javax.swing.JTextField txtAmount;
    private com.toedter.calendar.JDateChooser txtExpDate;
    private com.toedter.calendar.JDateChooser txtFromDate;
    private com.toedter.calendar.JDateChooser txtToDate;
    // End of variables declaration//GEN-END:variables

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    @Override
    public void selected(Object source, Object selectObj) {
        switch (source.toString()) {
            case "ExpenseSetup":
                Expense exp = (Expense) selectObj;
                listExp.add(exp);
                BindingUtil.BindComboFilter(cboExpense, listExp, null, true, false);
                break;
        }
    }

}
