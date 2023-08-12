/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wg.os.ui.order;

import com.wg.os.common.Global;
import com.wg.os.document.Order;
import com.wg.os.document.PaymentDetail;
import com.wg.os.service.CityService;
import com.wg.os.service.OrderService;
import com.wg.os.service.PaymentService;
import com.wg.os.ui.ApplicationMainFrame;
import com.wg.os.ui.common.SearchOrderPaymentTabelModel;
import com.wg.os.ui.common.SearchPaymentTableModel;
import com.wg.os.util.BindingUtil;
import com.wg.os.util.Util1;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;
import javax.swing.ListSelectionModel;
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
public class Payment extends javax.swing.JPanel {

    private static final Logger LOGGER = LoggerFactory.getLogger(Payment.class);

    @Autowired
    private SearchOrderPaymentTabelModel orderTabelModel;
    @Autowired
    private OrderService orderService;
    @Autowired
    private SearchPaymentTableModel paymentTableModel;
    @Autowired
    private CityService cityService;
    @Autowired
    private PaymentService paymentService;
    private int selectRow = -1;
    @Autowired
    private ApplicationMainFrame applicationMainFrame;

    /**
     * Creates new form Payment
     */
    public Payment() {
        initComponents();
    }

    public void setControl() {
        applicationMainFrame.setEditableDelete(false);
        applicationMainFrame.setEditableHistory(false);
        applicationMainFrame.setEditableSave(false);
    }

    private void setTodayDate() {
        txtFromDate.setDate(Util1.getTodayDate());
        txtToDate.setDate(Util1.getTodayDate());
        txtPayDate.setDate(Util1.getTodayDate());
    }

    public void initTable() {
        Util1.setLookAndFeel(this);
        setCurrentFont();
        setControl();
        setTodayDate();
        clearAll();
        tblOrder.setModel(orderTabelModel);
        tblPayment.setModel(paymentTableModel);
        tblOrder.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tblOrder.getSelectionModel().addListSelectionListener((ListSelectionEvent e) -> {
            if (e.getValueIsAdjusting()) {
                if (tblOrder.getSelectedRow() >= 0) {
                    selectRow = tblOrder.convertRowIndexToModel(tblOrder.getSelectedRow());
                    Order order = orderTabelModel.getOrder(selectRow);
                    setPayment(order);
                }
            }
        });
        tblOrder.getColumnModel().getColumn(0).setPreferredWidth(50); //Date
        tblOrder.getColumnModel().getColumn(1).setPreferredWidth(70); //Order Id
        tblOrder.getColumnModel().getColumn(2).setPreferredWidth(120);//Name 
        tblOrder.getColumnModel().getColumn(3).setPreferredWidth(40);//payment
        tblOrder.getColumnModel().getColumn(4).setPreferredWidth(60);//Order Total
        tblOrder.getColumnModel().getColumn(5).setPreferredWidth(60);//Pay Amt
        tblOrder.getColumnModel().getColumn(6).setPreferredWidth(60);// Balance
        // tblOrder.getColumnModel().getColumn(7).setPreferredWidth(120);

        tblPayment.getInputMap().put(KeyStroke.getKeyStroke("DELETE"), "Del-Action");
        tblPayment.getActionMap().put("Del-Action", actionItemDelete);
    }
    private final Action actionItemDelete = new AbstractAction() {

        @Override
        public void actionPerformed(ActionEvent e) {
            int row = tblOrder.getSelectedRow();

            if (row >= 0) {
                try {
                    int showConfirmDialog = JOptionPane.showConfirmDialog(Global.parentFrame, "Are you sure to Delete");
                    if (showConfirmDialog == JOptionPane.YES_OPTION) {
                        paymentTableModel.deletePaymentDetail(row);
                    }
                } catch (HeadlessException ex) {
                    LOGGER.info("Remove City :" + ex.getMessage());
                }
            }
        }
    };

    private void setPayment(Order order) {
        if (order.getPayment().getPayment().equals("Cash")) {
            txtPayAmount.setEditable(false);
            txtPayAmount.setText(String.valueOf(order.getPaidAmount()));
        } else {
            txtPayAmount.setEditable(true);
            txtPayAmount.setText("");

        }
        txtCusCode.setText(order.getCustomer().getCusId());
        txtCusName.setText(order.getCustomer().getCusName());
        txtTotalAmt.setText(order.getOrderTotalAmt().toString());
        List<PaymentDetail> listOrderDetail = order.getListPaymentDetail();
        paymentTableModel.setListPaymentDetail(listOrderDetail);
    }

    public void initCombo() {
        BindingUtil.BindComboFilter(cboCity, cityService.findAll(), null, true, true);
        BindingUtil.BindComboFilter(cboPayment, paymentService.findAll(), null, true, true);

    }

    private void search() {
        String city = cboCity.getSelectedItem().toString();
        String pay = cboPayment.getSelectedItem().toString();
        String cusCode = txtCustomerCode.getText();
        Date stDate = txtFromDate.getDate();
        Date enDate = txtToDate.getDate();
        if (city.equals("All")) {
            city = "-";
        }
        if (pay.equals("All")) {
            pay = "-";
        }
        if (cusCode.isEmpty()) {
            cusCode = "-";
        }
        List<Order> listOrder = orderService.search(stDate, enDate, cusCode, city, pay);
        orderTabelModel.setlistOrder(listOrder);
        clear();
    }

    private void setCurrentFont() {
        java.awt.Component[] filterComps = panelFilter.getComponents();
        for (java.awt.Component filterComp : filterComps) {
            filterComp.setFont(Global.font);
        }
        java.awt.Component[] tblComps = panelTbl.getComponents();
        for (java.awt.Component tblComp : tblComps) {
            tblComp.setFont(Global.font);
        }
        java.awt.Component[] paymentComps = panelPayment.getComponents();
        for (java.awt.Component paymentComp : paymentComps) {
            paymentComp.setFont(Global.font);
        }
        tblPayment.setFont(Global.font);
        tblOrder.setFont(Global.font);

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jSeparator1 = new javax.swing.JSeparator();
        jYearChooser1 = new com.toedter.calendar.JYearChooser();
        panelTbl = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblOrder = new javax.swing.JTable();
        jLabel11 = new javax.swing.JLabel();
        lblRecord = new javax.swing.JLabel();
        panelPayment = new javax.swing.JPanel();
        btnSave = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        txtCusName = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtCusCode = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txtPayDate = new com.toedter.calendar.JDateChooser();
        jLabel9 = new javax.swing.JLabel();
        txtPayAmount = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        txtTotalAmt = new javax.swing.JTextField();
        panelTblPayment = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblPayment = new javax.swing.JTable();
        panelFilter = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txtCustomerCode = new javax.swing.JTextField();
        cboCity = new javax.swing.JComboBox<>();
        txtFromDate = new com.toedter.calendar.JDateChooser();
        txtToDate = new com.toedter.calendar.JDateChooser();
        cboPayment = new javax.swing.JComboBox<>();
        jLabel7 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();

        panelTbl.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        tblOrder.setFont(Global.font);
        tblOrder.setModel(new javax.swing.table.DefaultTableModel(
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
        tblOrder.setRowHeight(22);
        jScrollPane1.setViewportView(tblOrder);

        jLabel11.setText("Total Record :");

        lblRecord.setText("0");

        javax.swing.GroupLayout panelTblLayout = new javax.swing.GroupLayout(panelTbl);
        panelTbl.setLayout(panelTblLayout);
        panelTblLayout.setHorizontalGroup(
            panelTblLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 790, Short.MAX_VALUE)
            .addGroup(panelTblLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel11)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblRecord, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panelTblLayout.setVerticalGroup(
            panelTblLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelTblLayout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 464, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelTblLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(lblRecord))
                .addGap(12, 12, 12))
        );

        panelPayment.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        btnSave.setFont(Global.font);
        btnSave.setText("Save");
        btnSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveActionPerformed(evt);
            }
        });

        jLabel8.setFont(Global.font);
        jLabel8.setText("Customer Name");

        txtCusName.setEditable(false);
        txtCusName.setFont(Global.font);

        jLabel4.setFont(Global.font);
        jLabel4.setText("Customer Code");

        txtCusCode.setEditable(false);
        txtCusCode.setFont(Global.font);

        jLabel5.setFont(Global.font);
        jLabel5.setText("Pay Date");

        txtPayDate.setDateFormatString("dd-MM-yyyy");
        txtPayDate.setFont(Global.font);

        jLabel9.setFont(Global.font);
        jLabel9.setText("Pay Amt");

        txtPayAmount.setFont(Global.font);
        txtPayAmount.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPayAmountActionPerformed(evt);
            }
        });

        jLabel10.setFont(Global.font);
        jLabel10.setText("Total Amt");

        txtTotalAmt.setEditable(false);
        txtTotalAmt.setFont(Global.font);

        panelTblPayment.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Payment", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.TOP, Global.font));

        tblPayment.setFont(Global.font);
        tblPayment.setModel(new javax.swing.table.DefaultTableModel(
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
        tblPayment.setRowHeight(22);
        jScrollPane2.setViewportView(tblPayment);

        javax.swing.GroupLayout panelTblPaymentLayout = new javax.swing.GroupLayout(panelTblPayment);
        panelTblPayment.setLayout(panelTblPaymentLayout);
        panelTblPaymentLayout.setHorizontalGroup(
            panelTblPaymentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelTblPaymentLayout.createSequentialGroup()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );
        panelTblPaymentLayout.setVerticalGroup(
            panelTblPaymentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 357, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout panelPaymentLayout = new javax.swing.GroupLayout(panelPayment);
        panelPayment.setLayout(panelPaymentLayout);
        panelPaymentLayout.setHorizontalGroup(
            panelPaymentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelPaymentLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelPaymentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(panelTblPayment, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, panelPaymentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(btnSave, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(panelPaymentLayout.createSequentialGroup()
                            .addGroup(panelPaymentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(jLabel10, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel9, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel8, javax.swing.GroupLayout.Alignment.LEADING))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addGroup(panelPaymentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(txtTotalAmt, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(txtCusName, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(txtCusCode, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(txtPayAmount)
                                .addComponent(txtPayDate, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 203, Short.MAX_VALUE)))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panelPaymentLayout.setVerticalGroup(
            panelPaymentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelPaymentLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelPaymentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtPayDate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelPaymentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtCusCode, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(panelPaymentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtCusName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelPaymentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(txtTotalAmt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelPaymentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtPayAmount, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(panelTblPayment, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnSave)
                .addContainerGap(18, Short.MAX_VALUE))
        );

        panelPaymentLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {txtCusCode, txtCusName, txtPayAmount, txtPayDate, txtTotalAmt});

        panelPaymentLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jLabel10, jLabel4, jLabel5, jLabel8, jLabel9});

        panelFilter.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Filter", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, Global.font));

        jLabel1.setFont(Global.font);
        jLabel1.setText("From");

        jLabel3.setFont(Global.font);
        jLabel3.setText("City");

        jLabel6.setFont(Global.font);
        jLabel6.setText("Cus Code");

        txtCustomerCode.setFont(Global.font);

        cboCity.setFont(Global.font);

        txtFromDate.setDateFormatString("dd-MM-yyyy");
        txtFromDate.setFont(Global.font);

        txtToDate.setDateFormatString("dd-MM-yyyy");
        txtToDate.setFont(Global.font);

        cboPayment.setFont(Global.font);

        jLabel7.setFont(Global.font);
        jLabel7.setText("Payment");

        jLabel2.setFont(Global.font);
        jLabel2.setText("To");

        jButton1.setFont(Global.font);
        jButton1.setText("Search");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 44, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout panelFilterLayout = new javax.swing.GroupLayout(panelFilter);
        panelFilter.setLayout(panelFilterLayout);
        panelFilterLayout.setHorizontalGroup(
            panelFilterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelFilterLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelFilterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6)
                    .addComponent(jLabel1))
                .addGap(26, 26, 26)
                .addGroup(panelFilterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(cboCity, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtFromDate, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 271, Short.MAX_VALUE)
                    .addComponent(txtCustomerCode))
                .addGap(18, 18, 18)
                .addGroup(panelFilterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelFilterLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jButton1))
                    .addGroup(panelFilterLayout.createSequentialGroup()
                        .addGroup(panelFilterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel7))
                        .addGap(18, 18, 18)
                        .addGroup(panelFilterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtToDate, javax.swing.GroupLayout.DEFAULT_SIZE, 271, Short.MAX_VALUE)
                            .addComponent(cboPayment, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        panelFilterLayout.setVerticalGroup(
            panelFilterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelFilterLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelFilterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelFilterLayout.createSequentialGroup()
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelFilterLayout.createSequentialGroup()
                        .addGroup(panelFilterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(panelFilterLayout.createSequentialGroup()
                                .addGroup(panelFilterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtFromDate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel1))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(panelFilterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(cboCity, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel3)))
                            .addGroup(panelFilterLayout.createSequentialGroup()
                                .addGroup(panelFilterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel2)
                                    .addComponent(txtToDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(panelFilterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel7)
                                    .addComponent(cboPayment, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(panelFilterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelFilterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(txtCustomerCode, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jButton1))
                        .addContainerGap())))
        );

        panelFilterLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {cboCity, txtCustomerCode, txtFromDate});

        panelFilterLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {cboPayment, txtToDate});

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panelTbl, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panelFilter, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(0, 0, 0)
                .addComponent(panelPayment, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panelPayment, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(panelFilter, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(panelTbl, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGap(0, 0, 0))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        search();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void txtPayAmountActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPayAmountActionPerformed
        // TODO add your handling code here:
        Double payAmt = Util1.getDouble(txtPayAmount.getText());
        Order order = orderTabelModel.getOrder(selectRow);

        if (order.getListPaymentDetail() != null) {
            for (PaymentDetail pd : order.getListPaymentDetail()) {
                payAmt += pd.getPayAmt();
            }
        }
        if (payAmt <= order.getOrderTotalAmt()) {
            PaymentDetail pd = new PaymentDetail();
            pd.setPayDate(txtPayDate.getDate());
            pd.setPayAmt(Util1.getDouble(txtPayAmount.getText()));
            paymentTableModel.addPaymentDetail(pd);
        } else {
            JOptionPane.showMessageDialog(Global.parentFrame, "Your Pay Amount is greater than total amount !");
        }

    }//GEN-LAST:event_txtPayAmountActionPerformed

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
        // TODO add your handling code here:
        savePayment();
    }//GEN-LAST:event_btnSaveActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnSave;
    private javax.swing.JComboBox<String> cboCity;
    private javax.swing.JComboBox<String> cboPayment;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSeparator jSeparator1;
    private com.toedter.calendar.JYearChooser jYearChooser1;
    private javax.swing.JLabel lblRecord;
    private javax.swing.JPanel panelFilter;
    private javax.swing.JPanel panelPayment;
    private javax.swing.JPanel panelTbl;
    private javax.swing.JPanel panelTblPayment;
    private javax.swing.JTable tblOrder;
    private javax.swing.JTable tblPayment;
    private javax.swing.JTextField txtCusCode;
    private javax.swing.JTextField txtCusName;
    private javax.swing.JTextField txtCustomerCode;
    private com.toedter.calendar.JDateChooser txtFromDate;
    private javax.swing.JTextField txtPayAmount;
    private com.toedter.calendar.JDateChooser txtPayDate;
    private com.toedter.calendar.JDateChooser txtToDate;
    private javax.swing.JTextField txtTotalAmt;
    // End of variables declaration//GEN-END:variables

    private void savePayment() {
        List<PaymentDetail> listPaymentDetail = paymentTableModel.getListPaymentDetail();
        double ttlAmt = 0.0;
        for (PaymentDetail pd : listPaymentDetail) {
            ttlAmt += pd.getPayAmt();
        }
        Order order = orderTabelModel.getOrder(selectRow);
        if (order.getOrderTotalAmt() == ttlAmt) {
            order.getPayment().setId("1");
            order.getPayment().setPayment("Cash");
            order.setPaidAmount(ttlAmt);
        } else {
            order.getPayment().setId("2");
            order.getPayment().setPayment("Credit");
            order.setPaidAmount(ttlAmt);
        }
        order.setListPaymentDetail(listPaymentDetail);
        Order save = orderService.save(order);
        if (save != null) {
            //orderTabelModel.setOrder(selectRow, save)
            search();
            JOptionPane.showMessageDialog(Global.parentFrame, "Payment Saved");
            clear();
        }
    }

    private void clear() {
        txtCusCode.setText("");
        txtCusName.setText("");
        txtTotalAmt.setText("");
        txtPayAmount.setText("");
        paymentTableModel.setListPaymentDetail(new ArrayList<>());

        //tblOrder.clearSelection();
        //paymentTableModel.clear();
    }

    public void clearAll() {
        clear();
        setTodayDate();
        cboCity.setSelectedIndex(0);
        cboPayment.setSelectedIndex(0);
        txtCustomerCode.setText("");
        orderTabelModel.clear();
    }

}
