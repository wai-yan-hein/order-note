/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wg.os.ui;

import com.wg.os.common.Global;
import com.wg.os.report.ReportPanel;
import com.wg.os.ui.order.ExpenseNote;
import com.wg.os.ui.order.OrderCheck;
import com.wg.os.ui.order.OrderNote;
import com.wg.os.ui.order.Payment;
import com.wg.os.ui.setting.SettingPanel;
import com.wg.os.ui.setup.CustomerSetup;
import com.wg.os.ui.setup.SetupMainPanel;
import com.wg.os.util.Util1;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

/**
 *
 * @author Lenovo
 */
@Component
public class ApplicationMainFrame extends javax.swing.JFrame {

    private static final Logger LOGGER = LoggerFactory.getLogger(ApplicationMainFrame.class);
    private final HashMap<Integer, String> hmTabIndex = new HashMap();
    @Autowired
    private CustomerSetup customerSetup;
    @Autowired
    private OrderNote orderNote;
    @Autowired
    private SetupMainPanel setupMainPanel;
    @Autowired
    private Payment payment;
    @Autowired
    private OrderCheck orderCheck;
    @Autowired
    private ExpenseNote expense;
    @Autowired
    private SettingPanel setting;
    @Autowired
    private ReportPanel report;
    @Autowired
    private ApplicationContext applicationContext;

    private final ActionListener menuListener = (java.awt.event.ActionEvent evt) -> {
        JMenuItem actionMenu = (JMenuItem) evt.getSource();
        String menuName = actionMenu.getText();
        LOGGER.info("Selected Menu : " + menuName);
        JPanel panel = getPanel(menuName);
        //tabMain.addTab(menuName, panel);
        addTabMain(panel, menuName);
    };

    /**
     * Creates new form ApplicationMainFrame
     */
    public ApplicationMainFrame() {
        initComponents();
        initMenu();
        ImageIcon size = new ImageIcon(getClass().getResource("/image/order_note.png"));
        setIconImage(size.getImage());
    }

    public void defaultPanel() {
        setCurrentFont();
        JPanel panel = getPanel("Order Note");
        addTabMain(panel, "Order Note");
        tabMain.addChangeListener((ChangeEvent e) -> {
            try {
                JPanel pan = (JPanel) tabMain.getSelectedComponent();
                String className = pan.getClass().getSimpleName();
                if (className != null) {
                    switch (className) {
                        case "OrderNote":
                            orderNote.newState();
                            break;
                        case "Payment":
                            payment.setControl();
                            break;
                        case "ExpenseNote":
                            expense.setControl();
                            break;
                        case "CustomerSetup":
                            customerSetup.setControl();
                            break;
                        case "OrderCheck":
                            orderCheck.setControl();
                            break;
                        default:
                            setEditableDelete(false);
                            setEditableHistory(false);
                            setEditableNew(false);
                            setEditableSave(false);
                            break;
                    }
                } else {
                    setEditableDelete(false);
                    setEditableHistory(false);
                    setEditableNew(false);
                    setEditableSave(false);

                }
            } catch (Exception ex) {
                LOGGER.error("Tab Change Listener :" + ex.getMessage());

            }
        });

    }

    private void addTabMain(JPanel panel, String menuName) {
        Integer tabIndex = tabMain.getTabCount() - 1;
        LOGGER.info("index : " + tabIndex);
        hmTabIndex.put(tabIndex + 1, menuName);
        tabMain.setSelectedIndex(tabIndex);
        tabMain.add(panel);
        tabMain.setTabComponentAt(tabMain.indexOfComponent(panel), getTitlePanel(tabMain, panel, menuName));
        tabMain.setSelectedComponent(panel);

    }

    public void assignDefalutValue() {
        Util1.setLookAndFeel(this);
    }

    private void initMenu() {
        menuItemOrderNote.addActionListener(menuListener);
        menuItemCusSetup.addActionListener(menuListener);
        menuItemOtherSetup.addActionListener(menuListener);
        menuItemPayment.addActionListener(menuListener);
        menuItemOrderCheck.addActionListener(menuListener);
        menuItemExpense.addActionListener(menuListener);
        menuItemSetting.addActionListener(menuListener);
        menuItemReport.addActionListener(menuListener);
        menuItemReport.setVisible(false);
        Global.parentFrame = this;

    }

    private JPanel getPanel(String panelName) {
        switch (panelName) {
            case "Customer Setup":
                return customerSetup;
            case "Order Note":
                orderNote.assingDefaultValue();
                return orderNote;
            case "Other Setup":
                return setupMainPanel;
            case "Payment":
                payment.initCombo();
                payment.initTable();
                return payment;
            case "Order Check":
                orderCheck.initTable();
                return orderCheck;
            case "Expense Note":
                expense.initTable();
                return expense;
            /* expense.initTable();
                return expense;
             */
 /* expense.initTable();
                return expense;
             */ case "Report":
                return report;
            case "Setting":
                return setting;
            default:
                return null;
        }
    }

    private JPanel getTitlePanel(final JTabbedPane tabbedPane, final JPanel panel, String title) {
        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        titlePanel.setOpaque(false);
        JLabel titleLbl = new JLabel(title);
        titleLbl.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 5));
        titlePanel.add(titleLbl);
        JLabel closeButton = new JLabel("x", SwingConstants.RIGHT);
        closeButton.setToolTipText("Click to close");
        closeButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                tabbedPane.remove(panel);
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                closeButton.setForeground(Color.RED); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void mouseExited(MouseEvent e) {
                closeButton.setForeground(Color.BLACK); //To change body of generated methods, choose Tools | Templates.
                //To change body of generated methods, choose Tools | Templates.
            }

        });
        titlePanel.add(closeButton);

        return titlePanel;
    }

    private void newAction() {
        JPanel panel = (JPanel) tabMain.getSelectedComponent();
        String className = panel.getClass().getSimpleName();
        if (className != null) {
            switch (className) {
                case "OrderNote":
                    orderNote.clear();
                    break;
                case "ExpenseNote":
                    expense.clearAll();
                    break;
                case "Payment":
                    payment.clearAll();
                    break;
                case "OrderCheck":
                    orderCheck.clearAll();
                    break;
                case "CustomerSetup":
                    customerSetup.clear();
                    break;

            }
        }

    }

    private void saveAction() {
        JPanel panel = (JPanel) tabMain.getSelectedComponent();
        String className = panel.getClass().getSimpleName();
        if (className != null) {
            switch (className) {
                case "OrderNote":
                    orderNote.saveOrder();
                    break;
                case "ExpenseNote":
                    expense.save();
                    break;
                case "CustomerSetup":
                    customerSetup.saveCusomer();
                    break;
            }
        }
    }

    private void deleteAction() {
        JPanel panel = (JPanel) tabMain.getSelectedComponent();
        String className = panel.getClass().getSimpleName();
        if (className != null) {
            switch (className) {
                case "OrderNote":
                    orderNote.deleteOrder();
                    break;
                case "ExpenseNote":
                    expense.delete();
                    break;
            }
        }
    }

    private void historyAction() {
        JPanel panel = (JPanel) tabMain.getSelectedComponent();
        String className = panel.getClass().getSimpleName();
        if (className != null) {
            switch (className) {
                case "OrderNote":
                    orderNote.searchOrderDialog();
                    break;
                case "CustomerSetup":
                    break;
            }
        }
    }

    public void setEditableDelete(boolean stataus) {
        btnDel.setEnabled(stataus);
    }

    public void setEditableSave(boolean stataus) {
        btnSave.setEnabled(stataus);
    }

    public void setEditableNew(boolean stataus) {
        btnNew.setEnabled(stataus);
    }

    public void setEditableHistory(boolean stataus) {
        btnHistory.setEnabled(stataus);
    }

    private void setCurrentFont() {
        menuOrder.setFont(Global.font);
        menuSetup.setFont(Global.font);
        menuSystem.setFont(Global.font);
        menuItemOrderNote.setFont(Global.font);
        menuItemCusSetup.setFont(Global.font);
        menuItemOtherSetup.setFont(Global.font);
        menuItemPayment.setFont(Global.font);
        menuItemOrderCheck.setFont(Global.font);
        menuItemExpense.setFont(Global.font);
        menuItemSetting.setFont(Global.font);
        menuItemReport.setFont(Global.font);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        tabMain = new javax.swing.JTabbedPane();
        toolBar = new javax.swing.JToolBar();
        btnNew = new javax.swing.JButton();
        btnHistory = new javax.swing.JButton();
        btnSave = new javax.swing.JButton();
        btnDel = new javax.swing.JButton();
        menubar = new javax.swing.JMenuBar();
        menuOrder = new javax.swing.JMenu();
        menuItemOrderNote = new javax.swing.JMenuItem();
        menuItemPayment = new javax.swing.JMenuItem();
        menuItemOrderCheck = new javax.swing.JMenuItem();
        menuItemExpense = new javax.swing.JMenuItem();
        menuItemReport = new javax.swing.JMenuItem();
        menuSetup = new javax.swing.JMenu();
        menuItemCusSetup = new javax.swing.JMenuItem();
        menuItemOtherSetup = new javax.swing.JMenuItem();
        menuSystem = new javax.swing.JMenu();
        menuItemSetting = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Order Note");

        tabMain.setBackground(new java.awt.Color(255, 255, 255));
        tabMain.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        tabMain.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                tabMainComponentShown(evt);
            }
        });

        toolBar.setRollover(true);

        btnNew.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/new_20.png"))); // NOI18N
        btnNew.setToolTipText("New");
        btnNew.setContentAreaFilled(false);
        btnNew.setFocusable(false);
        btnNew.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnNew.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnNew.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnNewMouseClicked(evt);
            }
        });
        btnNew.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNewActionPerformed(evt);
            }
        });
        toolBar.add(btnNew);

        btnHistory.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/history20.png"))); // NOI18N
        btnHistory.setToolTipText("History");
        btnHistory.setFocusable(false);
        btnHistory.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnHistory.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnHistory.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnHistoryMouseClicked(evt);
            }
        });
        btnHistory.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHistoryActionPerformed(evt);
            }
        });
        toolBar.add(btnHistory);

        btnSave.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/save_20.png"))); // NOI18N
        btnSave.setToolTipText("Save");
        btnSave.setContentAreaFilled(false);
        btnSave.setFocusable(false);
        btnSave.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnSave.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnSave.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnSaveMouseClicked(evt);
            }
        });
        btnSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveActionPerformed(evt);
            }
        });
        toolBar.add(btnSave);

        btnDel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/del_20.png"))); // NOI18N
        btnDel.setToolTipText("Delete");
        btnDel.setContentAreaFilled(false);
        btnDel.setFocusable(false);
        btnDel.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnDel.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnDel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnDelMouseClicked(evt);
            }
        });
        btnDel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDelActionPerformed(evt);
            }
        });
        toolBar.add(btnDel);

        menubar.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        menuOrder.setText("Order");

        menuItemOrderNote.setFont(Global.font);
        menuItemOrderNote.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/order_note.png"))); // NOI18N
        menuItemOrderNote.setText("Order Note");
        menuOrder.add(menuItemOrderNote);

        menuItemPayment.setFont(Global.font);
        menuItemPayment.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/payment20.png"))); // NOI18N
        menuItemPayment.setText("Payment");
        menuOrder.add(menuItemPayment);

        menuItemOrderCheck.setFont(Global.font);
        menuItemOrderCheck.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/order_check_20.png"))); // NOI18N
        menuItemOrderCheck.setText("Order Check");
        menuOrder.add(menuItemOrderCheck);

        menuItemExpense.setFont(Global.font);
        menuItemExpense.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/expense_note_20.png"))); // NOI18N
        menuItemExpense.setText("Expense Note");
        menuOrder.add(menuItemExpense);

        menuItemReport.setText("Report");
        menuOrder.add(menuItemReport);

        menubar.add(menuOrder);

        menuSetup.setText("Setup");

        menuItemCusSetup.setFont(Global.font);
        menuItemCusSetup.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/reg_icon.png"))); // NOI18N
        menuItemCusSetup.setText("Customer Setup");
        menuSetup.add(menuItemCusSetup);

        menuItemOtherSetup.setFont(Global.font);
        menuItemOtherSetup.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/setup_icon.png"))); // NOI18N
        menuItemOtherSetup.setText("Other Setup");
        menuSetup.add(menuItemOtherSetup);

        menubar.add(menuSetup);

        menuSystem.setText("System");

        menuItemSetting.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/setting_icon.png"))); // NOI18N
        menuItemSetting.setText("Setting");
        menuSystem.add(menuItemSetting);

        menubar.add(menuSystem);

        setJMenuBar(menubar);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tabMain, javax.swing.GroupLayout.DEFAULT_SIZE, 385, Short.MAX_VALUE)
            .addComponent(toolBar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(toolBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(tabMain, javax.swing.GroupLayout.DEFAULT_SIZE, 237, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void tabMainComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_tabMainComponentShown
        // TODO add your handling code here:
    }//GEN-LAST:event_tabMainComponentShown

    private void btnNewMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnNewMouseClicked
        // TODO add your handling code here:
        //newAction();
    }//GEN-LAST:event_btnNewMouseClicked

    private void btnSaveMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnSaveMouseClicked
        // TODO add your handling code here:
        //saveAction();
    }//GEN-LAST:event_btnSaveMouseClicked

    private void btnDelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnDelMouseClicked
        // TODO add your handling code here:
        //deleteAction();
    }//GEN-LAST:event_btnDelMouseClicked

    private void btnHistoryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHistoryActionPerformed
        // TODO add your handling code here:
        historyAction();
    }//GEN-LAST:event_btnHistoryActionPerformed

    private void btnHistoryMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnHistoryMouseClicked
        // TODO add your handling code here:
        //historyAction();
    }//GEN-LAST:event_btnHistoryMouseClicked

    private void btnDelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDelActionPerformed
        // TODO add your handling code here:
        deleteAction();
    }//GEN-LAST:event_btnDelActionPerformed

    private void btnNewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNewActionPerformed
        // TODO add your handling code here:
        newAction();
    }//GEN-LAST:event_btnNewActionPerformed

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
        // TODO add your handling code here:
        saveAction();
    }//GEN-LAST:event_btnSaveActionPerformed

    /**
     * @param args the command line arguments
     */

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnDel;
    private javax.swing.JButton btnHistory;
    private javax.swing.JButton btnNew;
    private javax.swing.JButton btnSave;
    private javax.swing.JMenuItem menuItemCusSetup;
    private javax.swing.JMenuItem menuItemExpense;
    private javax.swing.JMenuItem menuItemOrderCheck;
    private javax.swing.JMenuItem menuItemOrderNote;
    private javax.swing.JMenuItem menuItemOtherSetup;
    private javax.swing.JMenuItem menuItemPayment;
    private javax.swing.JMenuItem menuItemReport;
    private javax.swing.JMenuItem menuItemSetting;
    private javax.swing.JMenu menuOrder;
    private javax.swing.JMenu menuSetup;
    private javax.swing.JMenu menuSystem;
    private javax.swing.JMenuBar menubar;
    private javax.swing.JTabbedPane tabMain;
    private javax.swing.JToolBar toolBar;
    // End of variables declaration//GEN-END:variables
}
