/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wg.os.ui.setup;

import com.wg.os.common.Global;
import com.wg.os.document.City;
import com.wg.os.document.Customer;
import com.wg.os.document.Gender;
import com.wg.os.service.CityService;
import com.wg.os.service.CustomerService;
import com.wg.os.service.GenderService;
import com.wg.os.ui.ApplicationMainFrame;
import com.wg.os.ui.common.SearchCustomerListDialog;
import com.wg.os.ui.common.SelectionObserver;
import com.wg.os.util.BindingUtil;
import com.wg.os.util.Util1;
import java.awt.Dialog;
import java.awt.HeadlessException;
import java.awt.event.FocusAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author Lenovo
 */
@Component
public class CustomerSetup extends javax.swing.JPanel implements KeyListener, SelectionObserver {

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomerSetup.class);
    private Customer customer;
    private List<City> listCity;
    private List<Gender> listGender;

    private final FocusAdapter fa = new FocusAdapter() {
        @Override
        public void focusGained(java.awt.event.FocusEvent evt) {
            Object sourceObj = evt.getSource();
            if (sourceObj instanceof JComboBox) {
                JComboBox jcb = (JComboBox) sourceObj;
                LOGGER.info("FocusAdapter Control Name : " + jcb.getName());
            } else if (sourceObj instanceof JFormattedTextField) {
                JFormattedTextField jftf = (JFormattedTextField) sourceObj;
                jftf.selectAll();
                LOGGER.info("FocusAdapter Control Name : " + jftf.getName());
            } else if (sourceObj instanceof JTextField) {
                JTextField jtf = (JTextField) sourceObj;
                jtf.selectAll();
                LOGGER.info("FocusAdapter Control Name : " + jtf.getName());
            }
        }

        @Override
        public void focusLost(java.awt.event.FocusEvent evt) {

        }
    };
    @Autowired
    private CityService cityService;
    @Autowired
    private CustomerService customerService;
    @Autowired
    private GenderService genderService;
    @Autowired
    private SearchCustomerListDialog customerListDialog;
    @Autowired
    private GenderSetup genderSetup;
    @Autowired
    private CitySetup citySetup;
    @Autowired
    private ApplicationMainFrame applicationMainFrame;

    /**
     * Creates new form CustomerSetup
     */
    public CustomerSetup() {
        initComponents();
        txtCusName.requestFocus();
        iniitFocusListener();
        initKeyListener();
        assignDefaultValue();
    }

    public void setControl() {
        applicationMainFrame.setEditableDelete(false);
        applicationMainFrame.setEditableHistory(false);
        applicationMainFrame.setEditableNew(true);
        applicationMainFrame.setEditableSave(true);

    }

    private void iniitFocusListener() {
        txtCusName.addFocusListener(fa);
        txtCusPhoneNo.addFocusListener(fa);
        txtCusCode.addFocusListener(fa);
        txtPaneAddress.addFocusListener(fa);
        cboCity.addFocusListener(fa);
    }

    private void initKeyListener() {
        txtCusName.addKeyListener(this);
        txtEmail.addKeyListener(this);
        txtSocialLink.addKeyListener(this);
        txtCusPhoneNo.addKeyListener(this);
        cboCity.addKeyListener(this);
        cboGender.addKeyListener(this);

    }

    private void initCombo() {
        setControl();
        listCity = cityService.findAll();
        listGender = genderService.findAll();
        BindingUtil.BindCombo(cboCity, listCity, null, true);
        BindingUtil.BindComboFilter(cboGender, listGender, null, true, false);

    }

    private void setCustomer(Customer cus) {
        labelStatus.setText("EDIT");
        txtCusCode.setText(cus.getCusId());
        txtCusName.setText(cus.getCusName());
        txtEmail.setText(cus.getCusEmail());
        txtSocialLink.setText(cus.getCusSocialLink());
        txtCusPhoneNo.setText(cus.getCusPhoneNo());
        txtPaneAddress.setText(cus.getCusAddress());
        cboCity.setSelectedItem(cus.getCusCity());
        cboGender.setSelectedItem(cus.getCusGender());

    }

    private void assignDefaultValue() {
        txtTodayDate.setDate(Util1.getTodayDate());

    }

    public void saveCusomer() {
        if (isValidEntry()) {
            try {
                Customer cus = customerService.save(customer);
                if (cus != null) {
                    clear();
                    if (labelStatus.getText().equals("NEW")) {
                        JOptionPane.showMessageDialog(Global.parentFrame, "Customer Code is " + cus.getCusId(), "Customer Setup", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(Global.parentFrame, "Successfully Changed...");
                    }
                }
            } catch (HeadlessException e) {
                LOGGER.error("Save Customer  :" + e.getMessage());
            }
        }

    }

    private boolean isValidEntry() {
        boolean status;
        if (txtCusName.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Invalid Entry", "Customer Setup", JOptionPane.ERROR_MESSAGE
            );
            status = false;
        } else {
            customer = new Customer();
            Gender gender = (Gender) cboGender.getSelectedItem();
            customer.setCusRegDate(txtTodayDate.getDate());
            customer.setCusName(txtCusName.getText());
            customer.setCusPhoneNo(txtCusPhoneNo.getText());
            customer.setCusCity((City) cboCity.getSelectedItem());
            customer.setCusAddress(txtPaneAddress.getText());
            customer.setCusEmail(txtEmail.getText());
            customer.setCusGender(gender);
            customer.setCusSocialLink(txtSocialLink.getText());
            status = true;
        }
        return status;
    }

    public void clear() {
        txtCusName.setText("");
        txtCusPhoneNo.setText("");
        txtPaneAddress.setText("");
        txtEmail.setText("");
        txtSocialLink.setText("");
        txtCusCode.setText("");
        labelStatus.setText("NEW");
    }

    private void openDialog(String name) {
        JDialog dialog = new JDialog(Global.parentFrame, Dialog.ModalityType.DOCUMENT_MODAL);
        ImageIcon size;
        switch (name) {
            case "Gender Setup":
                size = new ImageIcon(getClass().getResource("/image/gender.png"));
                dialog.setIconImage(size.getImage());
                dialog.setTitle(name);
                genderSetup.initTabel();
                genderSetup.setObserver(this);
                dialog.add(genderSetup);
                break;
            case "City Setup":
                size = new ImageIcon(getClass().getResource("/image/city_icon.png"));
                dialog.setIconImage(size.getImage());
                dialog.setTitle(name);
                citySetup.initTabel();
                citySetup.setObserver(this);
                dialog.add(citySetup);
                break;
            default:
                break;
        }
        dialog.setSize(490, 420);
        dialog.setResizable(false);
        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);
    }

    private void setCurrentFont() {
        java.awt.Component[] cusComps = panelCustomer.getComponents();
        for (java.awt.Component cusComp : cusComps) {
            cusComp.setFont(Global.font);
        }
        txtPaneAddress.setFont(Global.font);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelCustomer = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        txtCusCode = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        txtCusName = new javax.swing.JTextField();
        txtCusPhoneNo = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        cboCity = new javax.swing.JComboBox<>();
        jLabel4 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtPaneAddress = new javax.swing.JTextPane();
        txtTodayDate = new com.toedter.calendar.JDateChooser();
        jLabel7 = new javax.swing.JLabel();
        txtEmail = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        cboGender = new javax.swing.JComboBox<>();
        jLabel9 = new javax.swing.JLabel();
        txtSocialLink = new javax.swing.JTextField();
        labelStatus = new javax.swing.JLabel();
        btnAddCity = new javax.swing.JButton();
        btnGender = new javax.swing.JButton();

        setBackground(new java.awt.Color(255, 255, 255));
        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                formComponentShown(evt);
            }
        });

        panelCustomer.setBackground(new java.awt.Color(255, 255, 255));

        jLabel5.setFont(Global.font);
        jLabel5.setText("Cus Code");

        txtCusCode.setEditable(false);
        txtCusCode.setFont(Global.font);
        txtCusCode.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtCusCodeMouseClicked(evt);
            }
        });
        txtCusCode.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCusCodeActionPerformed(evt);
            }
        });

        jLabel6.setFont(Global.font);
        jLabel6.setText("Date");

        jLabel1.setFont(Global.font);
        jLabel1.setText("Name");

        txtCusName.setFont(Global.font);
        txtCusName.setName("txtCusName"); // NOI18N

        txtCusPhoneNo.setFont(Global.font);
        txtCusPhoneNo.setName("txtCusPhoneNo"); // NOI18N

        jLabel2.setFont(Global.font);
        jLabel2.setText("Phone No");

        jLabel3.setFont(Global.font);
        jLabel3.setText("City");

        cboCity.setFont(Global.font);
        cboCity.setName("cboCity"); // NOI18N
        cboCity.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboCityActionPerformed(evt);
            }
        });

        jLabel4.setFont(Global.font);
        jLabel4.setText("Address");

        txtPaneAddress.setFont(Global.font);
        txtPaneAddress.setName("txtPaneAddress"); // NOI18N
        jScrollPane1.setViewportView(txtPaneAddress);

        txtTodayDate.setDateFormatString("dd-MM-yyyy");

        jLabel7.setFont(Global.font);
        jLabel7.setText("Email");

        txtEmail.setFont(Global.font);
        txtEmail.setName("txtEmail"); // NOI18N

        jLabel8.setFont(Global.font);
        jLabel8.setText("Gender");

        cboGender.setFont(Global.font);
        cboGender.setName("cboGender"); // NOI18N

        jLabel9.setFont(Global.font);
        jLabel9.setText("Social Link");

        txtSocialLink.setFont(Global.font);
        txtSocialLink.setName("txtSocialLink"); // NOI18N
        txtSocialLink.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtSocialLinkActionPerformed(evt);
            }
        });

        labelStatus.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        labelStatus.setForeground(java.awt.Color.green);
        labelStatus.setText("NEW");

        btnAddCity.setFont(Global.font);
        btnAddCity.setText("...");
        btnAddCity.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddCityActionPerformed(evt);
            }
        });

        btnGender.setFont(Global.font);
        btnGender.setText("...");
        btnGender.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGenderActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelCustomerLayout = new javax.swing.GroupLayout(panelCustomer);
        panelCustomer.setLayout(panelCustomerLayout);
        panelCustomerLayout.setHorizontalGroup(
            panelCustomerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelCustomerLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelCustomerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelCustomerLayout.createSequentialGroup()
                        .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, 145, Short.MAX_VALUE)
                        .addGap(371, 371, 371))
                    .addGroup(panelCustomerLayout.createSequentialGroup()
                        .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, 145, Short.MAX_VALUE)
                        .addGap(371, 371, 371))
                    .addGroup(panelCustomerLayout.createSequentialGroup()
                        .addComponent(labelStatus, javax.swing.GroupLayout.DEFAULT_SIZE, 141, Short.MAX_VALUE)
                        .addGap(375, 375, 375))
                    .addGroup(panelCustomerLayout.createSequentialGroup()
                        .addGroup(panelCustomerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, 145, Short.MAX_VALUE)
                            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 145, Short.MAX_VALUE)
                            .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, 145, Short.MAX_VALUE)
                            .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, 145, Short.MAX_VALUE)
                            .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, 145, Short.MAX_VALUE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 145, Short.MAX_VALUE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(28, 28, 28)
                        .addGroup(panelCustomerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(panelCustomerLayout.createSequentialGroup()
                                .addGroup(panelCustomerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(cboGender, 0, 295, Short.MAX_VALUE)
                                    .addComponent(cboCity, 0, 295, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(panelCustomerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(btnAddCity, javax.swing.GroupLayout.PREFERRED_SIZE, 40, Short.MAX_VALUE)
                                    .addComponent(btnGender, javax.swing.GroupLayout.PREFERRED_SIZE, 40, Short.MAX_VALUE))
                                .addGap(1, 1, 1))
                            .addComponent(txtSocialLink, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 343, Short.MAX_VALUE)
                            .addComponent(txtEmail, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 343, Short.MAX_VALUE)
                            .addComponent(txtCusName, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 343, Short.MAX_VALUE)
                            .addComponent(txtCusCode, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 343, Short.MAX_VALUE)
                            .addComponent(txtTodayDate, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 343, Short.MAX_VALUE)
                            .addComponent(txtCusPhoneNo, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 343, Short.MAX_VALUE)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 343, Short.MAX_VALUE))))
                .addGap(24, 24, 24))
        );
        panelCustomerLayout.setVerticalGroup(
            panelCustomerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelCustomerLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelCustomerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtTodayDate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelCustomerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtCusCode)
                    .addComponent(jLabel5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelCustomerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtCusName)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelCustomerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtEmail)
                    .addComponent(jLabel7))
                .addGap(17, 17, 17)
                .addGroup(panelCustomerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtSocialLink)
                    .addComponent(jLabel9))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelCustomerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel8, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(cboGender, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnGender, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelCustomerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cboCity, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3)
                    .addComponent(btnAddCity, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelCustomerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addComponent(txtCusPhoneNo, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(panelCustomerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addGap(52, 52, 52)
                .addComponent(labelStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        panelCustomerLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {btnAddCity, btnGender, cboCity, cboGender, txtCusCode, txtCusName, txtCusPhoneNo, txtEmail, txtSocialLink, txtTodayDate});

        panelCustomerLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jLabel1, jLabel2, jLabel3, jLabel5, jLabel6, jLabel7, jLabel8, jLabel9});

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelCustomer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelCustomer, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void cboCityActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboCityActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cboCityActionPerformed

    private void txtCusCodeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCusCodeActionPerformed
        // TODO add your handling code here:0
    }//GEN-LAST:event_txtCusCodeActionPerformed

    private void formComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_formComponentShown
        // TODO add your handling code here:
        LOGGER.info("CustomerSetup Component Shown");
        Util1.setLookAndFeel(this);
        setCurrentFont();
        initCombo();

    }//GEN-LAST:event_formComponentShown

    private void txtCusCodeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtCusCodeMouseClicked
        // TODO add your handling code here:
        if (evt.getClickCount() > 1) {
            customerListDialog.setObserver(this);
            customerListDialog.initTabel();
            customerListDialog.setLocationRelativeTo(null);
            customerListDialog.setVisible(true);
        }
    }//GEN-LAST:event_txtCusCodeMouseClicked

    private void btnAddCityActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddCityActionPerformed
        // TODO add your handling code here:
        openDialog("City Setup");
    }//GEN-LAST:event_btnAddCityActionPerformed

    private void txtSocialLinkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSocialLinkActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSocialLinkActionPerformed

    private void btnGenderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGenderActionPerformed
        // TODO add your handling code here:
        openDialog("Gender Setup");

    }//GEN-LAST:event_btnGenderActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAddCity;
    private javax.swing.JButton btnGender;
    private javax.swing.JComboBox<String> cboCity;
    private javax.swing.JComboBox<String> cboGender;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel labelStatus;
    private javax.swing.JPanel panelCustomer;
    private javax.swing.JTextField txtCusCode;
    private javax.swing.JTextField txtCusName;
    private javax.swing.JTextField txtCusPhoneNo;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JTextPane txtPaneAddress;
    private javax.swing.JTextField txtSocialLink;
    private com.toedter.calendar.JDateChooser txtTodayDate;
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
        switch (ctrlName) {
            case "txtCusName":
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    txtEmail.requestFocus();
                }
                break;
            case "txtEmail":
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    txtSocialLink.requestFocus();
                }
                break;
            case "txtSocialLink":
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    cboGender.requestFocus();
                }
                break;
            case "txtCusPhoneNo":
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    txtPaneAddress.requestFocus();
                }
                break;

        }
    }

    @Override
    public void selected(Object source, Object selectObj) {

        switch (source.toString()) {
            case "SearchCustomerListDialog":
                setCustomer((Customer) selectObj);
                break;
            case "GenderSetup":
                listGender.add((Gender) selectObj);
                BindingUtil.BindCombo(cboGender, listGender, null, true);
                break;
            case "CitySetup":
                listCity.add((City) selectObj);
                BindingUtil.BindCombo(cboCity, listCity, null, true);
                break;
        }
    }

}
