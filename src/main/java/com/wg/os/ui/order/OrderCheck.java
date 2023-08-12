/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wg.os.ui.order;

import com.wg.os.common.Global;
import com.wg.os.document.ExpNote;
import com.wg.os.document.Order;
import com.wg.os.document.TotalAmount;
import com.wg.os.service.CityService;
import com.wg.os.service.ExpenseNoteService;
import com.wg.os.service.GenderService;
import com.wg.os.service.OrderService;
import com.wg.os.ui.ApplicationMainFrame;
import com.wg.os.ui.common.OrderCheckTabelModel;
import com.wg.os.ui.common.TotalAmtTableModel;
import com.wg.os.util.BindingUtil;
import com.wg.os.util.Util1;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.table.DefaultTableCellRenderer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author Lenovo
 */
@Component
public class OrderCheck extends javax.swing.JPanel {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(OrderCheck.class);

    /**
     * Creates new form SessionCheck
     */
    @Autowired
    private OrderCheckTabelModel orderCheckTabelModel;
    @Autowired
    private TotalAmtTableModel amtTableModel;
    @Autowired
    private OrderService orderService;
    @Autowired
    private CityService cityService;
    @Autowired
    private ExpenseNoteService expenseNoteService;
    @Autowired
    private GenderService genderService;
    @Autowired
    private ApplicationMainFrame applicationMainFrame;
    
    public OrderCheck() {
        initComponents();
    }
    
    private void setTodayDate() {
        txtFromDate.setDate(Util1.getTodayDate());
        txtToDate.setDate(Util1.getTodayDate());
    }
    
    private void initCombo() {
        setTodayDate();
        BindingUtil.BindComboFilter(cboGender, genderService.findAll(), null, true, true);
        BindingUtil.BindComboFilter(cboCity, cityService.findAll(), null, true, true);
    }
    
    public void initTable() {
        Util1.setLookAndFeel(this);
        setCurrentFont();
        setControl();
        initCombo();
        tblOrderCheck.setModel(orderCheckTabelModel);
        orderCheckTabelModel.setTable(tblOrderCheck);
        tblCheckAmt.setModel(amtTableModel);
        tblOrderCheck.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tblCheckAmt.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
    }
    
    private void search() {
        int delOrderCount = 0;
        int orderCount = 0;
        List<Order> listOrder = orderService.search(txtFromDate.getDate(), txtToDate.getDate(), "-", "-", "-");
        for (Order od : listOrder) {
            if (od.getDeleted()) {
                delOrderCount++;
            } else {
                orderCount++;
            }
        }
        lblDelete.setText(String.valueOf(delOrderCount));
        lblTotalItem.setText(String.valueOf(orderCount));
        orderCheckTabelModel.setlistOrder(listOrder);
        calTotalAmt();
        
    }
    
    private void calTotalAmt() {
        double ttlNPrice = 0.0;
        double ttlOPrice = 0.0;
        double ttlPaidAmt = 0.0;
        double ttlProfit = 0.0;
        double ttlExpense = 0.0;
        for (int row = 0; row < tblOrderCheck.getRowCount(); row++) {
            Double nPrice = (Double) tblOrderCheck.getValueAt(row, 6);
            ttlNPrice += nPrice;
        }
        for (int row = 0; row < tblOrderCheck.getRowCount(); row++) {
            Double nPrice = (Double) tblOrderCheck.getValueAt(row, 7);
            ttlOPrice += nPrice;
        }
        for (int row = 0; row < tblOrderCheck.getRowCount(); row++) {
            Double nPrice = (Double) tblOrderCheck.getValueAt(row, 9);
            ttlPaidAmt += nPrice;
        }
        for (int row = 0; row < tblOrderCheck.getRowCount(); row++) {
            Double nPrice = (Double) tblOrderCheck.getValueAt(row, 10);
            ttlProfit += nPrice;
        }
        List<ExpNote> listExp = expenseNoteService.search(txtFromDate.getDate(), txtToDate.getDate());
        for (ExpNote e : listExp) {
            ttlExpense += e.getAmt();
        }
        TotalAmount tNormal = new TotalAmount("Total Normal Amount", ttlNPrice);
        TotalAmount tOrder = new TotalAmount("Total Order Amount", ttlOPrice);
        TotalAmount tPaid = new TotalAmount("Total Paid Amount", ttlPaidAmt);
        TotalAmount tProfit = new TotalAmount("Total Profit Amount", ttlProfit);
        TotalAmount tExpense = new TotalAmount("Total Expense Amount", ttlExpense);
        TotalAmount nProfit = new TotalAmount("Net Profit", ttlProfit - ttlExpense);
        
        List<TotalAmount> listTotalAmt = new ArrayList<>();
        listTotalAmt.add(tNormal);
        listTotalAmt.add(tOrder);
        listTotalAmt.add(tPaid);
        listTotalAmt.add(tProfit);
        listTotalAmt.add(tExpense);
        listTotalAmt.add(nProfit);
        amtTableModel.setListAmt(listTotalAmt);
        
    }
    
    public void clearAll() {
        setTodayDate();
        txtCustomer.setText(null);
        cboCity.setSelectedIndex(0);
        cboGender.setSelectedIndex(0);
        orderCheckTabelModel.clear();
        amtTableModel.clear();
        
    }
    
    public void setControl() {
        applicationMainFrame.setEditableNew(true);
        applicationMainFrame.setEditableDelete(false);
        applicationMainFrame.setEditableHistory(false);
        applicationMainFrame.setEditableSave(false);
    }
    
    private void setCurrentFont() {
        java.awt.Component[] entryComps = panelFilter.getComponents();
        for (java.awt.Component entryComp : entryComps) {
            entryComp.setFont(Global.font);
        }
        java.awt.Component[] ocComps = panleOC.getComponents();
        for (java.awt.Component ocComp : ocComps) {
            ocComp.setFont(Global.font);
        }
        java.awt.Component[] amtComps = panelCheckAmt.getComponents();
        for (java.awt.Component amtComp : amtComps) {
            amtComp.setFont(Global.font);
        }
        tblCheckAmt.setFont(Global.font);
        tblOrderCheck.setFont(Global.font);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelFilter = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txtFromDate = new com.toedter.calendar.JDateChooser();
        jLabel2 = new javax.swing.JLabel();
        txtToDate = new com.toedter.calendar.JDateChooser();
        jLabel3 = new javax.swing.JLabel();
        cboCity = new javax.swing.JComboBox<>();
        jLabel4 = new javax.swing.JLabel();
        cboGender = new javax.swing.JComboBox<>();
        jButton1 = new javax.swing.JButton();
        txtCustomer = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        panleOC = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblOrderCheck = new javax.swing.JTable();
        jLabel7 = new javax.swing.JLabel();
        lblDelete = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        lblTotalItem = new javax.swing.JLabel();
        panelCheckAmt = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblCheckAmt = new javax.swing.JTable();

        panelFilter.setBackground(new java.awt.Color(255, 255, 255));
        panelFilter.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Filter", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, Global.font));

        jLabel1.setFont(Global.font);
        jLabel1.setText("From");

        txtFromDate.setCalendar(Calendar.getInstance(Locale.getDefault()));
        txtFromDate.setDateFormatString("dd-MM-yyyy");
        txtFromDate.setFont(Global.font);

        jLabel2.setFont(Global.font);
        jLabel2.setText("To");

        txtToDate.setCalendar(Calendar.getInstance(Locale.getDefault()));
        txtToDate.setDateFormatString("dd-MM-yyyy");
        txtToDate.setFont(Global.font);

        jLabel3.setFont(Global.font);
        jLabel3.setText("City");

        cboCity.setFont(Global.font);

        jLabel4.setFont(Global.font);
        jLabel4.setText("Gender");

        cboGender.setFont(Global.font);

        jButton1.setFont(Global.font);
        jButton1.setText("Search");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        txtCustomer.setFont(Global.font);

        jLabel6.setFont(Global.font);
        jLabel6.setText("Customer");

        javax.swing.GroupLayout panelFilterLayout = new javax.swing.GroupLayout(panelFilter);
        panelFilter.setLayout(panelFilterLayout);
        panelFilterLayout.setHorizontalGroup(
            panelFilterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelFilterLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelFilterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(panelFilterLayout.createSequentialGroup()
                        .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(14, 14, 14)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelFilterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtFromDate, javax.swing.GroupLayout.DEFAULT_SIZE, 198, Short.MAX_VALUE)
                    .addComponent(txtToDate, javax.swing.GroupLayout.DEFAULT_SIZE, 198, Short.MAX_VALUE)
                    .addComponent(txtCustomer))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelFilterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, 67, Short.MAX_VALUE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, 67, Short.MAX_VALUE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(29, 29, 29)
                .addGroup(panelFilterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(panelFilterLayout.createSequentialGroup()
                        .addGap(126, 126, 126)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 1, Short.MAX_VALUE))
                    .addComponent(cboCity, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(cboGender, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(30, 30, 30))
        );
        panelFilterLayout.setVerticalGroup(
            panelFilterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelFilterLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelFilterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelFilterLayout.createSequentialGroup()
                        .addGroup(panelFilterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(cboCity, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(16, 16, 16)
                        .addGroup(panelFilterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(cboGender, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(panelFilterLayout.createSequentialGroup()
                        .addGroup(panelFilterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtFromDate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(panelFilterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtToDate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelFilterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(panelFilterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jButton1)
                        .addComponent(txtCustomer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        panelFilterLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {cboCity, cboGender, txtFromDate, txtToDate});

        panelFilterLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jLabel3, jLabel4});

        panleOC.setBackground(new java.awt.Color(255, 255, 255));
        panleOC.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Order Check", javax.swing.border.TitledBorder.LEFT, javax.swing.border.TitledBorder.DEFAULT_POSITION, Global.font));

        tblOrderCheck.setAutoCreateRowSorter(true);
        tblOrderCheck.setFont(Global.font);
        tblOrderCheck.setModel(new javax.swing.table.DefaultTableModel(
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
        tblOrderCheck.setRowHeight(22);
        jScrollPane1.setViewportView(tblOrderCheck);

        jLabel7.setFont(Global.font);
        jLabel7.setText("Order Delete :");

        lblDelete.setFont(Global.font);
        lblDelete.setText("0");

        jLabel8.setText("Total Item :");

        lblTotalItem.setText("0");

        javax.swing.GroupLayout panleOCLayout = new javax.swing.GroupLayout(panleOC);
        panleOC.setLayout(panleOCLayout);
        panleOCLayout.setHorizontalGroup(
            panleOCLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 1083, Short.MAX_VALUE)
            .addGroup(panleOCLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblTotalItem, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panleOCLayout.setVerticalGroup(
            panleOCLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panleOCLayout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 340, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panleOCLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(lblDelete)
                    .addComponent(jLabel8)
                    .addComponent(lblTotalItem))
                .addContainerGap())
        );

        panelCheckAmt.setBackground(new java.awt.Color(255, 255, 255));
        panelCheckAmt.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Amount", javax.swing.border.TitledBorder.LEFT, javax.swing.border.TitledBorder.DEFAULT_POSITION, Global.font));

        tblCheckAmt.setFont(Global.font);
        tblCheckAmt.setModel(new javax.swing.table.DefaultTableModel(
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
        tblCheckAmt.setRowHeight(22);
        jScrollPane2.setViewportView(tblCheckAmt);

        javax.swing.GroupLayout panelCheckAmtLayout = new javax.swing.GroupLayout(panelCheckAmt);
        panelCheckAmt.setLayout(panelCheckAmtLayout);
        panelCheckAmtLayout.setHorizontalGroup(
            panelCheckAmtLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 443, Short.MAX_VALUE)
        );
        panelCheckAmtLayout.setVerticalGroup(
            panelCheckAmtLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 143, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panleOC, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addComponent(panelFilter, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(0, 0, 0)
                .addComponent(panelCheckAmt, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(panelCheckAmt, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panelFilter, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(0, 0, 0)
                .addComponent(panleOC, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        search();
    }//GEN-LAST:event_jButton1ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> cboCity;
    private javax.swing.JComboBox<String> cboGender;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblDelete;
    private javax.swing.JLabel lblTotalItem;
    private javax.swing.JPanel panelCheckAmt;
    private javax.swing.JPanel panelFilter;
    private javax.swing.JPanel panleOC;
    private javax.swing.JTable tblCheckAmt;
    private javax.swing.JTable tblOrderCheck;
    private javax.swing.JTextField txtCustomer;
    private com.toedter.calendar.JDateChooser txtFromDate;
    private com.toedter.calendar.JDateChooser txtToDate;
    // End of variables declaration//GEN-END:variables
}
