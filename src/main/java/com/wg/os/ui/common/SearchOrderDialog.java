/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wg.os.ui.common;

import com.wg.os.common.Global;
import com.wg.os.document.City;
import com.wg.os.document.Order;
import com.wg.os.document.Payment;
import com.wg.os.service.CityService;
import com.wg.os.util.Util1;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.wg.os.service.OrderService;
import com.wg.os.service.PaymentService;
import com.wg.os.util.BindingUtil;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;

/**
 *
 * @author Lenovo
 */
@Component
public class SearchOrderDialog extends javax.swing.JDialog {

    private static final Logger LOGGER = LoggerFactory.getLogger(SearchOrderDialog.class);
    private SelectionObserver observer;
    private int selectRow = -1;
    private List<Order> listOrder;

    public void setObserver(SelectionObserver observer) {
        this.observer = observer;
    }

    /**
     * Creates new form SearchOrderDialog
     */
    @Autowired
    private OrderService orderItemService;
    @Autowired
    private CityService cityService;
    @Autowired
    private PaymentService paymentService;
    @Autowired
    private SearchOrderTabelModel orderTabelModel;

    public SearchOrderDialog() {
        super(new javax.swing.JFrame(), true);
        initComponents();
    }

    private void initCombo() {
        BindingUtil.BindComboFilter(cboCity, cityService.findAll(), null, true, true);
        BindingUtil.BindComboFilter(cboPayment, paymentService.findAll(), null, true, true);
    }

    private void initTable() {
        initCombo();
        tblOrder.setModel(orderTabelModel);
        tblOrder.getColumnModel().getColumn(0).setPreferredWidth(70);// Date
        tblOrder.getColumnModel().getColumn(1).setPreferredWidth(110);// Order Id
        tblOrder.getColumnModel().getColumn(2).setPreferredWidth(140);// Name

        tblOrder.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tblOrder.getSelectionModel().addListSelectionListener((ListSelectionEvent e) -> {
            if (e.getValueIsAdjusting()) {
                if (tblOrder.getSelectedRow() >= 0) {
                    selectRow = tblOrder.convertRowIndexToModel(tblOrder.getSelectedRow());
                    btnSelect.setEnabled(true);
                }
            }
        });
        
        /*if (!orderTabelModel.getlistOrder().isEmpty()){
        orderTabelModel.clear();
        }*/
            /*tblOrder.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
        @Override
        public java.awt.Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        final java.awt.Component component = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        Object valueAt = table.getValueAt(row, 3);
        Color c;
        if ("Credit".equals(valueAt)) {
        c = Color.red;
        } else {
        c = Color.white;
        }
        component.setBackground(c);
        
        return component;
        
        }
        });*/ 
            
        
    }

    public void assignDefaultValue() {
        Util1.setLookAndFeel(this);
        setCurrentFont();
        initTable();
        txtFromDate.setDate(Util1.getTodayDate());
        txtToDate.setDate(Util1.getTodayDate());
    }

    private void select() {
        //int row = tblOrder.convertRowIndexToModel(tblOrder.getSelectedRow());
        Order order = orderTabelModel.getOrder(selectRow);
        LOGGER.info("Order Detail :" + order.getListOrderDetail().size());
        observer.selected("SearchOrderDialog", order);
        this.dispose();
    }

    private void search() {
        Date stDate = txtFromDate.getDate();
        Date enDate = txtToDate.getDate();
        String cusCode = txtCusCode.getText();
        String cityName = cboCity.getSelectedItem().toString();
        String payName = cboPayment.getSelectedItem().toString();
        String cityId;
        String payId;
        if (cusCode.isEmpty()) {
            cusCode = "-";
        }
        if (cityName.equals("All")) {
            cityId = "-";
        } else {
            City city = (City) cboCity.getSelectedItem();
            cityId = city.getId();
        }
        if (payName.equals("All")) {
            payId = "-";
        } else {
            Payment pay = (Payment) cboPayment.getSelectedItem();
            payId = pay.getId();
        }

        listOrder = orderItemService.search(stDate, enDate, cusCode, cityId, payId);
        setNoOrderDelList();
    }

    private void setNoOrderDelList() {
        List<Order> listCopy = new ArrayList<>(listOrder);
        listCopy.removeIf(order -> order.getDeleted().equals(Boolean.TRUE));
        orderTabelModel.setlistOrder(listCopy);
        setTotalAmt(listCopy);
    }

    private void setTotalAmt(List<Order> list) {
        if (!list.isEmpty()) {
            double ttlAmt = 0.0;
            for (Order o : list) {
                ttlAmt += o.getOrderTotalAmt();
            }
            labelTotalItem.setText(String.valueOf(list.size()));
            labelTotalAmt.setText(String.valueOf(ttlAmt).concat(" MMK"));
        }
    }

    private void setCurrentFont() {
        java.awt.Component[] fComps = panelFilter.getComponents();
        for (java.awt.Component fComp : fComps) {
            fComp.setFont(Global.font);
        }
        tblOrder.setFont(Global.font);
        java.awt.Component[] components = getComponents();
        for (java.awt.Component com : components) {
            com.setFont(Global.font);
        }
        labelTotalAmt.setFont(Global.font);
        labelTotalItem.setFont(Global.font);
        lblAmt.setFont(Global.font);
        lblItem.setFont(Global.font);
        chkDeleted.setFont(Global.font);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jCalendar1 = new com.toedter.calendar.JCalendar();
        panelFilter = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        txtFromDate = new com.toedter.calendar.JDateChooser();
        txtToDate = new com.toedter.calendar.JDateChooser();
        jLabel3 = new javax.swing.JLabel();
        txtCusCode = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        cboCity = new javax.swing.JComboBox<>();
        jLabel5 = new javax.swing.JLabel();
        cboPayment = new javax.swing.JComboBox<>();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblOrder = new javax.swing.JTable();
        lblItem = new javax.swing.JLabel();
        labelTotalItem = new javax.swing.JLabel();
        lblAmt = new javax.swing.JLabel();
        labelTotalAmt = new javax.swing.JLabel();
        btnSelect = new javax.swing.JButton();
        chkDeleted = new javax.swing.JCheckBox();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                formComponentShown(evt);
            }
        });

        panelFilter.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Filter"));

        jLabel1.setFont(Global.font);
        jLabel1.setText("From");

        jLabel2.setFont(Global.font);
        jLabel2.setText("To");

        jButton1.setFont(Global.font);
        jButton1.setText("Search");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        txtFromDate.setDateFormatString("dd-MM-yyyy");
        txtFromDate.setFont(Global.font);

        txtToDate.setDateFormatString("dd-MM-yyyy");
        txtToDate.setFont(Global.font);

        jLabel3.setFont(Global.font);
        jLabel3.setText("Customer");

        txtCusCode.setFont(Global.font);

        jLabel4.setFont(Global.font);
        jLabel4.setText("City");

        cboCity.setFont(Global.font);

        jLabel5.setText("Payment");

        javax.swing.GroupLayout panelFilterLayout = new javax.swing.GroupLayout(panelFilter);
        panelFilter.setLayout(panelFilterLayout);
        panelFilterLayout.setHorizontalGroup(
            panelFilterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelFilterLayout.createSequentialGroup()
                .addGroup(panelFilterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelFilterLayout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton1))
                    .addGroup(panelFilterLayout.createSequentialGroup()
                        .addGroup(panelFilterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panelFilterLayout.createSequentialGroup()
                                .addGap(16, 16, 16)
                                .addGroup(panelFilterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                            .addGroup(panelFilterLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel5)
                                .addGap(29, 29, 29)))
                        .addGroup(panelFilterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cboCity, javax.swing.GroupLayout.Alignment.TRAILING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelFilterLayout.createSequentialGroup()
                                .addGroup(panelFilterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtCusCode)
                                    .addComponent(txtToDate, javax.swing.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                                    .addComponent(txtFromDate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(1, 1, 1))
                            .addComponent(cboPayment, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap())
        );
        panelFilterLayout.setVerticalGroup(
            panelFilterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelFilterLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelFilterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtFromDate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(panelFilterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtToDate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelFilterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtCusCode)
                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelFilterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cboCity, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelFilterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5)
                    .addComponent(cboPayment, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(53, 53, 53)
                .addComponent(jButton1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        tblOrder.setFont(Global.font);
        tblOrder.setRowHeight(20);
        jScrollPane1.setViewportView(tblOrder);

        lblItem.setFont(Global.font);
        lblItem.setText("Total Item :");

        labelTotalItem.setFont(Global.font);
        labelTotalItem.setText("0");

        lblAmt.setFont(Global.font);
        lblAmt.setText("Order Total Amt");

        labelTotalAmt.setFont(Global.font);
        labelTotalAmt.setText("0");

        btnSelect.setFont(Global.font);
        btnSelect.setText("Select");
        btnSelect.setEnabled(false);
        btnSelect.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSelectActionPerformed(evt);
            }
        });

        chkDeleted.setText("Deleted");
        chkDeleted.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                chkDeletedMouseClicked(evt);
            }
        });
        chkDeleted.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkDeletedActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(panelFilter, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblItem, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(labelTotalItem, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lblAmt, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(labelTotalAmt, javax.swing.GroupLayout.DEFAULT_SIZE, 130, Short.MAX_VALUE)
                        .addGap(278, 278, 278)
                        .addComponent(chkDeleted, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addComponent(btnSelect, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelFilter, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblAmt)
                    .addComponent(labelTotalAmt)
                    .addComponent(btnSelect)
                    .addComponent(labelTotalItem)
                    .addComponent(lblItem)
                    .addComponent(chkDeleted))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_formComponentShown
        // TODO add your handling code here:


    }//GEN-LAST:event_formComponentShown

    private void btnSelectActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSelectActionPerformed
        // TODO add your handling code here:
        select();
    }//GEN-LAST:event_btnSelectActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        search();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void chkDeletedActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkDeletedActionPerformed
        // TODO add your handling code here:

    }//GEN-LAST:event_chkDeletedActionPerformed

    private void chkDeletedMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_chkDeletedMouseClicked
        // TODO add your handling code here:
        if (chkDeleted.isSelected()) {
            orderTabelModel.setlistOrder(listOrder);
            setTotalAmt(listOrder);

        } else {
            setNoOrderDelList();
        }
    }//GEN-LAST:event_chkDeletedMouseClicked

    /**
     * @param args the command line arguments
     */

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnSelect;
    private javax.swing.JComboBox<String> cboCity;
    private javax.swing.JComboBox<String> cboPayment;
    private javax.swing.JCheckBox chkDeleted;
    private javax.swing.JButton jButton1;
    private com.toedter.calendar.JCalendar jCalendar1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel labelTotalAmt;
    private javax.swing.JLabel labelTotalItem;
    private javax.swing.JLabel lblAmt;
    private javax.swing.JLabel lblItem;
    private javax.swing.JPanel panelFilter;
    private javax.swing.JTable tblOrder;
    private javax.swing.JTextField txtCusCode;
    private com.toedter.calendar.JDateChooser txtFromDate;
    private com.toedter.calendar.JDateChooser txtToDate;
    // End of variables declaration//GEN-END:variables
}
