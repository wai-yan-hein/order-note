/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wg.os.ui.order;

import com.wg.os.common.Global;
import com.wg.os.document.City;
import com.wg.os.document.Customer;
import com.wg.os.document.GenId;
import com.wg.os.document.Order;
import com.wg.os.document.OrderDetail;
import com.wg.os.service.CityService;
import com.wg.os.document.Payment;
import com.wg.os.document.PaymentDetail;
import com.wg.os.service.CustomerService;
import com.wg.os.ui.common.ImageEntryDialog;
import com.wg.os.ui.common.SearchOrderDialog;
import com.wg.os.ui.common.SelectionObserver;
import com.wg.os.util.Util1;
import java.awt.*;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.FocusAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.List;
import com.wg.os.service.OrderService;
import com.wg.os.service.PaymentService;
import com.wg.os.ui.ApplicationMainFrame;
import com.wg.os.ui.common.SearchCustomerListDialog;
import com.wg.os.ui.setting.FontPanel;
import com.wg.os.util.BindingUtil;
import com.wg.os.util.NumberUtil;
import javax.swing.SwingConstants;
import org.springframework.context.ApplicationContext;

/**
 *
 * @author Lenovo
 */
@Component
public class OrderNote extends javax.swing.JPanel implements KeyListener, SelectionObserver {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderNote.class);
    HashMap<String, OrderDetail> hmOrderItem = new HashMap<>();
    private final List<String> listDelImg = new ArrayList<>();
    private final List<JPanel> listDelPanel = new ArrayList<>();
    private Order order;
    private Customer customer = new Customer();
    private List<OrderDetail> listOrderDetail = new ArrayList<>();
    private GenId genId = new GenId();
    private boolean eventActive = true;
    Double totalAmt;
    @Autowired
    private FontPanel fontPanel;
    private final FocusAdapter fa = new FocusAdapter() {
        @Override
        public void focusGained(java.awt.event.FocusEvent evt) {
            Object sourceObj = evt.getSource();
            if (sourceObj instanceof JComboBox) {
                JComboBox jcb = (JComboBox) sourceObj;
                LOGGER.info("Control Name : " + jcb.getName());
            } else if (sourceObj instanceof JFormattedTextField) {
                JFormattedTextField jftf = (JFormattedTextField) sourceObj;
                jftf.selectAll();
                LOGGER.info("Control Name : " + jftf.getName());
            } else if (sourceObj instanceof JTextField) {
                JTextField jtf = (JTextField) sourceObj;
                jtf.selectAll();
                LOGGER.info("Control Name : " + jtf.getName());
            }
        }

        @Override
        public void focusLost(java.awt.event.FocusEvent evt) {

        }
    };

    /**
     * Creates new form OrderNote
     */
    @Autowired
    private CustomerService customerService;
    @Autowired
    private CityService cityService;

    @Autowired
    private OrderService orderService;
    @Autowired
    private PaymentService paymentService;
    @Autowired
    private ImageEntryDialog dialog;
    @Autowired
    private SearchOrderDialog searchOrderDialog;
    @Autowired
    private SearchCustomerListDialog customerListDialog;
    @Autowired
    private ApplicationMainFrame applicationMainFrame;
    @Autowired
    ApplicationContext applicationContext;

    public OrderNote() {
        initComponents();
        initKeyListener();
        initFocusListner();

    }

    public OrderNote newInstance() {
        return applicationContext.getBean(OrderNote.class);
    }

    private void setFont() {
        java.awt.Component[] cusComps = panelCustomer.getComponents();
        for (java.awt.Component cusComp : cusComps) {
            cusComp.setFont(Global.font);
        }
    }

    public void assingDefaultValue() {
        setFont();
        Util1.setLookAndFeel(this);
    }

    private void initCombo() {
        BindingUtil.BindComboFilter(cboCity, cityService.findAll(), null, true, false);
        BindingUtil.BindComboFilter(cboPayment, paymentService.findAll(), null, true, false);

    }

    private void setTodayDate() {
        txtOrderDate.setDate(Util1.getTodayDate());
        txtDueDate.setDate(Util1.getTodayDate());
    }

    private void assignDefaultValue() {
        newState();
        setTodayDate();
        panelImage.setLayout(new GridLayout(0, 3, 0, 0));
    }

    private void initKeyListener() {

        txtCusCode.addKeyListener(this);
        //txtDeliverFee.addKeyListener(this);
        txtDiscount.addKeyListener(this);
        //txtPaidAmt.addKeyListener(this);
        panelImage.addKeyListener(this);
    }

    private void initFocusListner() {
        txtCusCode.addFocusListener(fa);
    }

    private void setCustomer(Customer cus) {
        customer = cus;
        txtCusCode.setText(cus.getCusId());
        txtCusName.setText(cus.getCusName());
        txtAddress.setText(cus.getCusAddress());
        txtPhone.setText(cus.getCusPhoneNo());
        cboCity.setSelectedItem(cus.getCusCity());
    }

    private void openFileChooser() {
        FileDialog fileDialog = new FileDialog(Global.parentFrame, "Select Image Files");
        fileDialog.setMultipleMode(false);
        fileDialog.setDirectory(Global.setting.getImgPath());
        fileDialog.setLocationRelativeTo(null);
        fileDialog.setVisible(true);
        if (fileDialog.getDirectory() != null) {
            String imgPath = fileDialog.getDirectory().concat("\\") + fileDialog.getFile();
            dialog.setImgPath(imgPath);
            dialog.setResizable(true);
            dialog.setObserver(this);
            dialog.setLocationRelativeTo(null);
            dialog.setVisible(true);
        }
    }

    private JPanel get3ColumnImagePanel(String size, String color, String price, String imgPath, String qty, String discount) {
        int width = 213;
        int height = 320;
        JPanel imgPanel = new JPanel();
        imgPanel.setSize(width, height);
        JLabel labelImg = new JLabel();
        ImageIcon icon = new ImageIcon(imgPath);
        labelImg.setSize(213, 282);
        Image img = icon.getImage().getScaledInstance(213, 282, Image.SCALE_SMOOTH);
        labelImg.setIcon(new ImageIcon(img));
        //Adding to JPanel
        imgPanel.add(labelImg);
        imgPanel.add(getFieldLabel("Size - " + size));
        imgPanel.add(getFieldLabel("Color - " + color));
        imgPanel.add(getFieldLabel("Qty - " + qty));
        imgPanel.add(getFieldLabel("Dis% - " + discount));
        imgPanel.add(getFieldLabel("Price - " + price));

        //labelImg.setAlignmentX(java.awt.Component.CENTER_ALIGNMENT);
        labelImg.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2, true));
        imgPanel.setBackground(Color.WHITE);
        imgPanel.setLayout(new BoxLayout(imgPanel, BoxLayout.Y_AXIS));

        imgPanel.addMouseListener(new MouseAdapter() {
            boolean clicked = false;

            @Override
            public void mouseClicked(MouseEvent e) {
                if (eventActive) {
                    if (clicked) {
                        clicked = false;
                        labelImg.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2, true));
                        listDelImg.remove(imgPath);
                        listDelPanel.remove(imgPanel);
                        btnDelete.setEnabled(false);
                        LOGGER.info("You Cancel :" + imgPath);

                    } else {
                        labelImg.setBorder(BorderFactory.createLineBorder(Color.RED, 2, true));
                        clicked = true;
                        listDelImg.add(imgPath);
                        listDelPanel.add(imgPanel);
                        btnDelete.setEnabled(true);
                    }
                }

            }
        });

        return imgPanel;
    }

    private JLabel getFieldLabel(String field) {
        JLabel label = new JLabel(field);
        label.setSize(210, 50);
        label.setAlignmentX(SwingConstants.CENTER);
        label.setFont(Global.font);
        return label;
    }

    private void calAmount() {

        double ttlAmt = 0.0;
        double ttlDis = 0.0;
        int ttlQty = 0;
        double amt;
        double disAmt = 0.0;
        for (OrderDetail od : listOrderDetail) {
            int qty = od.getItemQty();
            amt = od.getItemPrice() * qty;
            if (od.getItemDiscount() > 0) {
                disAmt = (amt * od.getItemDiscount() * 0.01);
                amt = amt - (amt * od.getItemDiscount() * 0.01);

            }
            ttlAmt += amt;
            ttlDis += disAmt;
            ttlQty += qty;
        }
        txtDiscountAmt.setText(String.valueOf(ttlDis));
        txtTotalAmt.setText(String.valueOf(ttlAmt));
        txtTotalQty.setText(String.valueOf(ttlQty));

    }

    private String getTotalAmt() {

        double ttlAmt = 0.0;
        double amt;
        for (OrderDetail od : listOrderDetail) {
            int qty = od.getItemQty();
            amt = od.getItemPrice() * qty;
            if (od.getItemDiscount() > 0) {
                amt = amt - (amt * od.getItemDiscount() * 0.01);
            }
            ttlAmt += amt;
        }

        return String.valueOf(ttlAmt);
    }

    public void saveOrder() {
        if (isValidEntry()) {
            try {
                Order save = orderService.save(order);
                if (save != null) {
                    clear();
                    JOptionPane.showMessageDialog(Global.parentFrame, "Saved");
                }
            } catch (HeadlessException e) {
                JOptionPane.showMessageDialog(Global.parentFrame, e.getMessage());

            }
        }
    }

    private boolean isValidEntry() {
        boolean status;
        if (txtCusCode.getText().isEmpty()) {
            JOptionPane.showMessageDialog(Global.parentFrame, "Customer can't be empty");
            status = false;
        } else if (listOrderDetail.isEmpty()) {
            JOptionPane.showMessageDialog(Global.parentFrame, "Order Item can't be empty");
            status = false;
        } else {
            order = new Order();
            Payment pay = (Payment) cboPayment.getSelectedItem();
            if (pay.getPayment().equals("Cash")) {
                order.setPaidAmount(Util1.getDouble(txtTotalAmt.getText()));
            }
            customer.setCusId(txtCusCode.getText());
            customer.setCusName(txtCusName.getText());
            customer.setCusCity((City) cboCity.getSelectedItem());
            customer.setCusPhoneNo(txtPhone.getText());
            customer.setCusAddress(txtAddress.getText());
            //order.setPaidAmount(Util1.getDouble(txtPaidAmt.getText()));
            order.setOrderId(txtOrderId.getText());
            order.setOrderTotalAmt(Util1.getDouble(txtTotalAmt.getText()));
            order.setPayment(pay);
            order.setDiscount(Util1.getDouble(txtDiscount.getText()));
            order.setDueDate(txtDueDate.getDate());
            order.setOrderDate(txtOrderDate.getDate());
            order.setOrderTotalQty(Util1.getInteger(txtTotalQty.getText()));
            order.setCustomer(customer);
            //order.setDeliveryFee(Util1.getDouble(txtDeliverFee.getText()));
            order.setListOrderDetail(listOrderDetail);
            status = true;
        }

        return status;
    }

    public void clear() {
        setTodayDate();
        labelStatus.setForeground(Color.green);
        labelStatus.setText("NEW");
        txtOrderId.setText("");
        txtCusCode.setText("");
        txtCusName.setText("");
        txtPhone.setText("");
        txtTotalAmt.setText("");
        txtTotalQty.setText("");
        txtAddress.setText("");
        txtDiscount.setText("");
        panelImage.removeAll();
        panelImage.revalidate();
        panelImage.repaint();
        listOrderDetail.clear();
        setEditable(true);
        applicationMainFrame.setEditableDelete(false);
        btnDelete.setEnabled(false);
        newState();
    }

    private void setOrder(Order order) {
        clear();
        if (order.getDeleted()) {
            labelStatus.setForeground(Color.red);                                            // Delete State
            labelStatus.setText("DELETED");
            setEditable(false);
            deleteState();
        } else {
            labelStatus.setText("EDIT");                                                      // Edit State
            eventActive = true;
            editState();
        }
        if (order.getDeliveredStatus() == null || !order.getDeliveredStatus()) {
            //lblDelivered.setText("Not Delivered");
            //lblDelivered.setForeground(Color.red);

        } else {
            //lblDelivered.setText("Deliverd");
            //lblDelivered.setForeground(Color.green);
        }
        double ttlPayAmt = 0.0;
        if (order.getListPaymentDetail() != null) {
            for (PaymentDetail payment : order.getListPaymentDetail()) {
                ttlPayAmt += payment.getPayAmt();
            }
            double ttlBalance = order.getOrderTotalAmt() - ttlPayAmt;
            //txtBalance.setText(String.valueOf(ttlBalance));

        }
        for (OrderDetail od : order.getListOrderDetail()) {
            JPanel panel = get3ColumnImagePanel(od.getItemSize(), od.getItemColor(),
                    od.getItemPrice().toString(), od.getItemImage(),
                    od.getItemQty().toString(), od.getItemDiscount().toString());
            panelImage.add(panel);
        }
        /* order.getListOrderDetail().stream().map((od)
        -> get3ColumnImagePanel(od.getItemSize(), od.getItemColor(),
        od.getItemPrice().toString(), od.getItemImage(),
        od.getItemQty().toString(), od.getItemDiscount().toString())).forEachOrdered((panel) -> {
        panelImage.add(panel);
        });*/
        txtOrderDate.setDate(order.getOrderDate());
        txtDueDate.setDate(order.getDueDate());
        txtOrderId.setText(order.getOrderId());
        txtCusCode.setText(order.getCustomer().getCusId());
        txtCusName.setText(order.getCustomer().getCusName());
        cboCity.setSelectedItem(order.getCustomer().getCusCity());
        cboPayment.setSelectedItem(order.getPayment());
        txtPhone.setText(order.getCustomer().getCusPhoneNo());
        txtTotalAmt.setText(order.getOrderTotalAmt().toString());
        txtTotalQty.setText(order.getOrderTotalQty().toString());
        txtAddress.setText(order.getCustomer().getCusAddress());
        txtDiscount.setText(order.getDiscount().toString());
        //txtPaidAmt.setText(order.getPaidAmount().toString());

        listOrderDetail = order.getListOrderDetail();
        customer = order.getCustomer();
        panelImage.revalidate();
        panelImage.repaint();
        LOGGER.info("List Order Detail :" + order.getListOrderDetail().size());
        LOGGER.info("Panel Component Count :" + panelImage.getComponentCount());

    }

    private void setEditable(boolean status) {
        txtOrderDate.setEnabled(status);
        txtDueDate.setEnabled(status);
        txtOrderId.setEnabled(status);
        txtCusCode.setEnabled(status);
        txtCusName.setEnabled(status);
        cboCity.setEnabled(status);
        cboPayment.setEnabled(status);
        txtPhone.setEnabled(status);
        txtTotalAmt.setEnabled(status);
        txtTotalQty.setEnabled(status);
        txtAddress.setEnabled(status);
        txtDiscount.setEnabled(status);
        panelImage.setEnabled(status);
        btnAddImage.setEnabled(status);
        btnDelete.setEnabled(status);
        eventActive = status;

        //txtPaidAmt.setText(order.getPaidAmount().toString());
    }

    private void deleteImage() {
        if (!listDelImg.isEmpty()) {
            listDelImg.forEach((img) -> {
                listOrderDetail.removeIf(od -> od.getItemImage().equals(img));
            });
            LOGGER.info("Item List :" + listOrderDetail.size());
        }
        if (!listDelPanel.isEmpty()) {
            listDelPanel.forEach((panel) -> {
                panelImage.remove(panel);
            });
            panelImage.revalidate();
            panelImage.repaint();
            LOGGER.info("Panel Removed ");
        }
        btnDelete.setEnabled(false);
        listDelImg.clear();
        listDelPanel.clear();
        calAmount();
    }

    public void deleteOrder() {
        if (labelStatus.getText().equals("DELETED")) {
            JOptionPane.showMessageDialog(Global.parentFrame, "This Order is already deleted");
        } else {
            int showConfirmDialog = JOptionPane.showConfirmDialog(Global.parentFrame, "Are you sure delete...");
            if (showConfirmDialog == JOptionPane.OK_OPTION) {
                orderService.delete(txtOrderId.getText());
            }
            clear();
        }

    }

    public void searchOrderDialog() {
        searchOrderDialog.setResizable(false);
        searchOrderDialog.setLocationRelativeTo(null);
        searchOrderDialog.setObserver(this);
        searchOrderDialog.assignDefaultValue();
        searchOrderDialog.setVisible(true);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane2 = new javax.swing.JScrollPane();
        panelImage = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        panelCustomer = new javax.swing.JPanel();
        labelCusCode = new javax.swing.JLabel();
        labelCusName = new javax.swing.JLabel();
        txtCusName = new javax.swing.JTextField();
        labelCity = new javax.swing.JLabel();
        cboCity = new javax.swing.JComboBox<>();
        labelPhoneNo = new javax.swing.JLabel();
        txtPhone = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        btnAddImage = new javax.swing.JButton();
        labelDate = new javax.swing.JLabel();
        labelTotalQty = new javax.swing.JLabel();
        txtTotalQty = new javax.swing.JTextField();
        labelTotalAmt = new javax.swing.JLabel();
        txtTotalAmt = new javax.swing.JTextField();
        txtCusCode = new javax.swing.JTextField();
        btnDelete = new javax.swing.JButton();
        labelStatus = new javax.swing.JLabel();
        labelOrderId = new javax.swing.JLabel();
        txtOrderId = new javax.swing.JTextField();
        labelDiscount = new javax.swing.JLabel();
        txtDiscount = new javax.swing.JTextField();
        labelDueDate = new javax.swing.JLabel();
        txtOrderDate = new com.toedter.calendar.JDateChooser();
        txtDueDate = new com.toedter.calendar.JDateChooser();
        jLabel1 = new javax.swing.JLabel();
        cboPayment = new javax.swing.JComboBox<>();
        jLabel2 = new javax.swing.JLabel();
        txtDiscountAmt = new javax.swing.JTextField();
        jScrollPane4 = new javax.swing.JScrollPane();
        txtAddress = new javax.swing.JTextPane();

        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                formComponentShown(evt);
            }
        });

        panelImage.setBackground(new java.awt.Color(255, 255, 255));
        panelImage.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        panelImage.setForeground(new java.awt.Color(240, 240, 240));
        panelImage.setName("panelImgae"); // NOI18N

        javax.swing.GroupLayout panelImageLayout = new javax.swing.GroupLayout(panelImage);
        panelImage.setLayout(panelImageLayout);
        panelImageLayout.setHorizontalGroup(
            panelImageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 771, Short.MAX_VALUE)
        );
        panelImageLayout.setVerticalGroup(
            panelImageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 674, Short.MAX_VALUE)
        );

        jScrollPane2.setViewportView(panelImage);

        panelCustomer.setBackground(new java.awt.Color(255, 255, 255));
        panelCustomer.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        labelCusCode.setFont(Global.font);
        labelCusCode.setText("Customer Code");

        labelCusName.setFont(Global.font);
        labelCusName.setText("Customer Name");

        txtCusName.setFont(Global.font);
        txtCusName.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtCusNameMouseClicked(evt);
            }
        });

        labelCity.setFont(Global.font);
        labelCity.setText("City");

        cboCity.setFont(Global.font);
        cboCity.setName("cboCity"); // NOI18N

        labelPhoneNo.setFont(Global.font);
        labelPhoneNo.setText("Phone No");

        txtPhone.setFont(Global.font);
        txtPhone.setName("txtPhone"); // NOI18N

        jLabel5.setFont(Global.font);
        jLabel5.setText("Address");

        btnAddImage.setFont(Global.font);
        btnAddImage.setText("Image");
        btnAddImage.setToolTipText("Click to get images");
        btnAddImage.setMaximumSize(new java.awt.Dimension(80, 29));
        btnAddImage.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddImageActionPerformed(evt);
            }
        });

        labelDate.setFont(Global.font);
        labelDate.setText("Order Date");

        labelTotalQty.setFont(Global.font);
        labelTotalQty.setText("Total Qty");

        txtTotalQty.setEditable(false);
        txtTotalQty.setFont(Global.font);
        txtTotalQty.setName("txtTotalQty"); // NOI18N
        txtTotalQty.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTotalQtyActionPerformed(evt);
            }
        });

        labelTotalAmt.setFont(Global.font);
        labelTotalAmt.setText("Total Amt");

        txtTotalAmt.setEditable(false);
        txtTotalAmt.setFont(Global.font);
        txtTotalAmt.setName("txtTotalAmt"); // NOI18N

        txtCusCode.setFont(Global.font);
        txtCusCode.setName("txtCusCode"); // NOI18N
        txtCusCode.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtCusCodeMouseClicked(evt);
            }
        });

        btnDelete.setFont(Global.font);
        btnDelete.setText("Delete");
        btnDelete.setEnabled(false);
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });

        labelStatus.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        labelStatus.setForeground(java.awt.Color.green);
        labelStatus.setText("NEW");

        labelOrderId.setFont(Global.font);
        labelOrderId.setText("Order Code");

        txtOrderId.setEditable(false);
        txtOrderId.setFont(Global.font);

        labelDiscount.setFont(Global.font);
        labelDiscount.setText("Discount %");

        txtDiscount.setFont(Global.font);
        txtDiscount.setName("txtDiscount"); // NOI18N

        labelDueDate.setFont(Global.font);
        labelDueDate.setText("Due Date");

        txtOrderDate.setDateFormatString("dd-MM-yyyy");
        txtOrderDate.setFont(Global.font);

        txtDueDate.setDateFormatString("dd-MM-yyyy");
        txtDueDate.setFont(Global.font);

        jLabel1.setFont(Global.font);
        jLabel1.setText("Payment");

        cboPayment.setFont(Global.font
        );

        jLabel2.setFont(Global.font);
        jLabel2.setText("Discount Amount");

        txtDiscountAmt.setFont(Global.font);

        jScrollPane4.setViewportView(txtAddress);

        javax.swing.GroupLayout panelCustomerLayout = new javax.swing.GroupLayout(panelCustomer);
        panelCustomer.setLayout(panelCustomerLayout);
        panelCustomerLayout.setHorizontalGroup(
            panelCustomerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelCustomerLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelCustomerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(labelStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 233, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(labelCusCode, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(labelCusName, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(labelCity, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(labelPhoneNo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(labelDate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(labelDueDate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(labelOrderId, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(labelDiscount, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(labelTotalQty, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(labelTotalAmt, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGroup(panelCustomerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelCustomerLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panelCustomerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtTotalAmt)
                            .addComponent(txtTotalQty)
                            .addComponent(cboPayment, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtDiscount)
                            .addComponent(txtCusCode, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(cboCity, javax.swing.GroupLayout.Alignment.TRAILING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtPhone, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(txtOrderId)
                            .addComponent(txtDueDate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtOrderDate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtCusName)
                            .addComponent(txtDiscountAmt)
                            .addComponent(jScrollPane4)))
                    .addGroup(panelCustomerLayout.createSequentialGroup()
                        .addGap(79, 79, 79)
                        .addComponent(btnAddImage, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnDelete, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(11, 11, 11)))
                .addContainerGap())
        );
        panelCustomerLayout.setVerticalGroup(
            panelCustomerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelCustomerLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelCustomerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtOrderDate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(labelDate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelCustomerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtDueDate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(labelDueDate, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelCustomerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtOrderId)
                    .addComponent(labelOrderId, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelCustomerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtCusCode, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(labelCusCode, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelCustomerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtCusName)
                    .addComponent(labelCusName, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelCustomerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(cboCity)
                    .addComponent(labelCity, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelCustomerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtPhone, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(labelPhoneNo, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelCustomerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, 65, Short.MAX_VALUE)
                    .addComponent(jScrollPane4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelCustomerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtDiscount)
                    .addComponent(labelDiscount, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelCustomerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtDiscountAmt)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelCustomerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(cboPayment)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelCustomerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelTotalQty, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtTotalQty))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelCustomerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelTotalAmt, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtTotalAmt))
                .addGap(18, 18, 18)
                .addGroup(panelCustomerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnDelete)
                    .addComponent(labelStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnAddImage, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        panelCustomerLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {cboCity, txtCusCode, txtCusName, txtDiscount, txtDueDate, txtOrderDate, txtOrderId, txtPhone, txtTotalAmt, txtTotalQty});

        panelCustomerLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jLabel1, labelCity, labelCusCode, labelCusName, labelDate, labelDiscount, labelDueDate, labelOrderId, labelPhoneNo, labelTotalAmt, labelTotalQty});

        jScrollPane3.setViewportView(panelCustomer);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 499, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 758, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 691, Short.MAX_VALUE)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void formComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_formComponentShown
        // TODO add your handling code here:
        LOGGER.info("OrderNote Component Shown");
        assignDefaultValue();
        initCombo();
        txtCusCode.requestFocus();

        //addEmptyPanel();
    }//GEN-LAST:event_formComponentShown

    private void btnAddImageActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddImageActionPerformed
        // TODO add your handling code here:

        openFileChooser();
    }//GEN-LAST:event_btnAddImageActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        // TODO add your handling code here:
        deleteImage();

    }//GEN-LAST:event_btnDeleteActionPerformed

    private void txtCusCodeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtCusCodeMouseClicked
        // TODO add your handling code here:
        if (evt.getClickCount() > 1) {
            customerListDialog.initTabel();
            customerListDialog.setLocationRelativeTo(null);
            customerListDialog.setResizable(false);
            customerListDialog.setObserver(this);
            customerListDialog.setVisible(true);
        }
    }//GEN-LAST:event_txtCusCodeMouseClicked

    private void txtTotalQtyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTotalQtyActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTotalQtyActionPerformed

    private void txtCusNameMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtCusNameMouseClicked
        // TODO add your handling code here:
        if (evt.getClickCount() > 1) {
            customerListDialog.initTabel();
            customerListDialog.setLocationRelativeTo(null);
            customerListDialog.setResizable(false);
            customerListDialog.setObserver(this);
            customerListDialog.setVisible(true);
        }
    }//GEN-LAST:event_txtCusNameMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAddImage;
    private javax.swing.JButton btnDelete;
    private javax.swing.JComboBox<String> cboCity;
    private javax.swing.JComboBox<String> cboPayment;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JLabel labelCity;
    private javax.swing.JLabel labelCusCode;
    private javax.swing.JLabel labelCusName;
    private javax.swing.JLabel labelDate;
    private javax.swing.JLabel labelDiscount;
    private javax.swing.JLabel labelDueDate;
    private javax.swing.JLabel labelOrderId;
    private javax.swing.JLabel labelPhoneNo;
    private javax.swing.JLabel labelStatus;
    private javax.swing.JLabel labelTotalAmt;
    private javax.swing.JLabel labelTotalQty;
    private javax.swing.JPanel panelCustomer;
    private javax.swing.JPanel panelImage;
    private javax.swing.JTextPane txtAddress;
    private javax.swing.JTextField txtCusCode;
    private javax.swing.JTextField txtCusName;
    private javax.swing.JTextField txtDiscount;
    private javax.swing.JTextField txtDiscountAmt;
    private com.toedter.calendar.JDateChooser txtDueDate;
    private com.toedter.calendar.JDateChooser txtOrderDate;
    private javax.swing.JTextField txtOrderId;
    private javax.swing.JTextField txtPhone;
    private javax.swing.JTextField txtTotalAmt;
    private javax.swing.JTextField txtTotalQty;
    // End of variables declaration//GEN-END:variables

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
        Object sourceObj = e.getSource();
        String ctrlName = "-";

        if (sourceObj instanceof JComboBox) {
            ctrlName = ((JComboBox) sourceObj).getName();
        } else if (sourceObj instanceof JFormattedTextField) {
            ctrlName = ((JFormattedTextField) sourceObj).getName();
        } else if (sourceObj instanceof JTextField) {
            ctrlName = ((JTextField) sourceObj).getName();
        }
        LOGGER.info("Control Name Key Released:" + ctrlName);
        switch (ctrlName) {
            case "txtCusCode":
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    customer = customerService.findById(txtCusCode.getText());
                    if (customer == null) {
                        JOptionPane.showMessageDialog(Global.parentFrame, "Register :" + txtCusCode.getText(), "Finding Customer", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        setCustomer(customer);
                    }
                }
                //openFileChooser();
                break;
            case "txtDiscount":
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_ENTER:
                        double ttlAmt = Double.valueOf(txtTotalAmt.getText());
                        double amt = ttlAmt - ((NumberUtil.NZero(txtDiscount.getText()) * 0.01)
                                * ttlAmt);
                        txtTotalAmt.setText(String.valueOf(amt));
                        break;
                    case KeyEvent.VK_BACK_SPACE:
                        txtTotalAmt.setText(getTotalAmt());
                        break;
                }
                break;
            case "txtPaidAmt":
                switch (e.getKeyCode()) {
                    /*case KeyEvent.VK_ENTER:
                    String ttlAmt = txtTotalAmt.getText();
                    double amt = NumberUtil.NZero(ttlAmt) - NumberUtil.NZero(txtPaidAmt.getText());
                    txtTotalAmt.setText(String.valueOf(amt));
                    break;
                    case KeyEvent.VK_BACK_SPACE:
                    txtTotalAmt.setText(getTotalAmt());
                    break;*/
                }
        }
    }

    @Override
    public void selected(Object source, Object selectObj
    ) {
        switch (source.toString()) {
            case "ImageEntryDialog":
                LOGGER.info("ImageEntryDialog is selected");
                OrderDetail od = (OrderDetail) selectObj;
                String size = od.getItemSize();
                String color = od.getItemColor();
                String price = od.getItemPrice().toString();
                String img = od.getItemImage();
                String qty = od.getItemQty().toString();
                String dis = String.valueOf(od.getItemDiscount());
                JPanel p = get3ColumnImagePanel(size, color, price, img, qty, dis);
                panelImage.add(p);
                panelImage.revalidate();
                panelImage.repaint();
                listOrderDetail.add(od);
                calAmount();
                LOGGER.info("Item List : " + listOrderDetail.size());
                break;
            case "SearchOrderDialog":
                setOrder((Order) selectObj);
                break;
            case "SearchCustomerListDialog":
                setCustomer((Customer) selectObj);
                //openFileChooser();
                break;
        }
    }

    public void newState() {
        applicationMainFrame.setEditableNew(true);
        applicationMainFrame.setEditableHistory(true);
        applicationMainFrame.setEditableDelete(false);
        applicationMainFrame.setEditableSave(true);
    }

    private void deleteState() {
        applicationMainFrame.setEditableNew(true);
        applicationMainFrame.setEditableHistory(true);
        applicationMainFrame.setEditableDelete(false);
        applicationMainFrame.setEditableSave(false);

    }

    private void editState() {
        applicationMainFrame.setEditableNew(true);
        applicationMainFrame.setEditableHistory(true);
        applicationMainFrame.setEditableDelete(true);
        applicationMainFrame.setEditableSave(true);
    }

}
