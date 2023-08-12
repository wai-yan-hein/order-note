/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wg.os.ui.setup;

import com.mongodb.client.result.DeleteResult;
import com.wg.os.common.Global;
import com.wg.os.document.GenId;
import com.wg.os.document.User;
import com.wg.os.service.GenIdService;
import com.wg.os.service.UserService;
import com.wg.os.ui.common.UserTabelModel;
import com.wg.os.util.Util1;
import java.awt.Color;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.util.List;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;
import javax.swing.ListSelectionModel;
import javax.swing.border.Border;
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
public class AccountSetup extends javax.swing.JPanel {

    private static final Logger LOGGER = LoggerFactory.getLogger(AccountSetup.class);
    private User user;
    private int selectRow = -1;
    private GenId genId;
    private int lastId;

    @Autowired
    private UserTabelModel userTabelModel;
    @Autowired
    private UserService userService;
    @Autowired
    private GenIdService genIdService;

    /**
     * Creates new form CitySetup
     */
    public AccountSetup() {
        initComponents();
    }

    public void initTabel() {
        Util1.setLookAndFeel(this);
        setCurrentFont();
        tblUser.setModel(userTabelModel);
        List<User> listUser = userService.findAll();
        if (!listUser.isEmpty()) {
            userTabelModel.setListUser(listUser);
        }
        tblUser.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tblUser.getSelectionModel().addListSelectionListener((ListSelectionEvent e) -> {
            if (e.getValueIsAdjusting()) {
                if (tblUser.getSelectedRow() >= 0) {
                    selectRow = tblUser.convertRowIndexToModel(tblUser.getSelectedRow());
                }

            }
        });
        tblUser.getInputMap().put(KeyStroke.getKeyStroke("DELETE"), "Del-Action");
        tblUser.getActionMap().put("Del-Action", actionItemDelete);

    }
    private final Action actionItemDelete = new AbstractAction() {

        @Override
        public void actionPerformed(ActionEvent e) {
            int row = tblUser.getSelectedRow();

            if (row >= 0) {
                try {
                    int showConfirmDialog = JOptionPane.showConfirmDialog(Global.parentFrame, "Are you sure to Delete");
                    if (showConfirmDialog == JOptionPane.YES_OPTION) {
                        User user = userTabelModel.getUser(row);
                        DeleteResult remove = userService.remove(user.getUserId());
                        if (remove.getDeletedCount() == 1) {
                            userTabelModel.deleteUser(row);
                        }
                    }
                } catch (HeadlessException ex) {
                    LOGGER.info("Remove User :" + ex.getMessage());
                }
            }
        }
    };

    private void save() {

        try {
            if (isValidEntry()) {
                userTabelModel.addUser(user);
            }
        } catch (HeadlessException e) {
            LOGGER.error("saveCity  :" + e.getMessage());
        }
    }

    private boolean isValidEntry() {
        boolean status = false;
        String username = txtUsername.getText();
        String password = new String(txtPassword.getPassword());
        String repass = new String(txtRePassword.getPassword());
        String shortuser = txtShortName.getText();
        if (username.isEmpty() || shortuser.isEmpty()) {
            JOptionPane.showMessageDialog(new JFrame(), "Field Cannot be empty...", "User Setup", JOptionPane.ERROR_MESSAGE);
            txtUsername.setBorder(BorderFactory.createLineBorder(Color.red));
            txtShortName.setBorder(BorderFactory.createLineBorder(Color.red));
            status = false;
        } else {
            if (repass.equals(password)) {
                user = new User();
                user.setUserId(genUserId());
                user.setUserName(username);
                user.setShortName(shortuser);
                user.setPassword(password);
                user.setIsActive((cbActive.isSelected()));
                User save = userService.save(user);
                if (save != null) {
                    clear();
                    genId.setGenId(lastId + 1);
                    genIdService.save(genId);
                    JOptionPane.showMessageDialog(Global.parentFrame, "Save");
                    status = true;
                }
            } else {
                JOptionPane.showMessageDialog(Global.parentFrame, "Password not match", "Error", JOptionPane.ERROR_MESSAGE);
                txtPassword.setBorder(BorderFactory.createLineBorder(Color.red));
                txtRePassword.setBorder(BorderFactory.createLineBorder(Color.red));

            }
        }
        return status;
    }

    private void clear() {
        txtUsername.setText("");
        txtPassword.setText("");
        txtRePassword.setText("");
        cbActive.setText("");
        tblUser.getSelectionModel().clearSelection();

    }

    private String genUserId() {
        genId = genIdService.findById("USER");
        lastId = genId.getGenId();
        String lastValue = genId.getGenId().toString();
        for (int i = 0; i < 3; i++) {
            lastValue = "0" + lastValue;
        }
        return lastValue;
    }

    private void setCurrentFont() {
        java.awt.Component[] setupComps = getComponents();
        for (java.awt.Component setupComp : setupComps) {
            setupComp.setFont(Global.font);
        }
        tblUser.setFont(Global.font);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        tblUser = new javax.swing.JTable();
        txtUsername = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        btnSave = new javax.swing.JButton();
        btnClear = new javax.swing.JButton();
        txtPassword = new javax.swing.JPasswordField();
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txtRePassword = new javax.swing.JPasswordField();
        cbActive = new javax.swing.JCheckBox();
        jLabel4 = new javax.swing.JLabel();
        txtShortName = new javax.swing.JTextField();

        setBackground(new java.awt.Color(255, 255, 255));
        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                formComponentShown(evt);
            }
        });

        tblUser.setFont(Global.font);
        tblUser.setRowHeight(20);
        jScrollPane1.setViewportView(tblUser);

        txtUsername.setFont(Global.font);
        txtUsername.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        txtUsername.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtUsernameMouseClicked(evt);
            }
        });
        txtUsername.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtUsernameActionPerformed(evt);
            }
        });

        jLabel2.setFont(Global.font);
        jLabel2.setText("User Name");

        btnSave.setFont(Global.font);
        btnSave.setText("Save");
        btnSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveActionPerformed(evt);
            }
        });

        btnClear.setFont(Global.font);
        btnClear.setText("Clear");

        txtPassword.setFont(Global.font);
        txtPassword.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        txtPassword.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtPasswordMouseClicked(evt);
            }
        });

        jLabel1.setFont(Global.font);
        jLabel1.setText("Password");

        jLabel3.setFont(Global.font);
        jLabel3.setText("Re-Password");

        txtRePassword.setFont(Global.font);
        txtRePassword.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        txtRePassword.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtRePasswordMouseClicked(evt);
            }
        });
        txtRePassword.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtRePasswordActionPerformed(evt);
            }
        });

        cbActive.setFont(Global.font);
        cbActive.setText("Active");

        jLabel4.setFont(Global.font);
        jLabel4.setText("Short Name");

        txtShortName.setFont(Global.font);
        txtShortName.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtShortNameMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addComponent(jLabel1)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtUsername)
                    .addComponent(txtPassword)
                    .addComponent(txtRePassword)
                    .addComponent(cbActive)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnSave, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnClear, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(txtShortName))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {btnClear, btnSave});

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jLabel1, jLabel2, jLabel3, jLabel4});

        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtUsername, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(txtShortName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtPassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel1))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addComponent(txtRePassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(cbActive)
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnSave)
                            .addComponent(btnClear))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 388, Short.MAX_VALUE))
                .addContainerGap())
        );

        layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jLabel1, jLabel2, jLabel3, jLabel4});

    }// </editor-fold>//GEN-END:initComponents

    private void formComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_formComponentShown
        // TODO add your handling code here:
        LOGGER.info("CitySetup Component Shown");

    }//GEN-LAST:event_formComponentShown

    private void txtUsernameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtUsernameActionPerformed
        // TODO add your handling code here:
        save();
    }//GEN-LAST:event_txtUsernameActionPerformed

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
        // TODO add your handling code here:
        save();
    }//GEN-LAST:event_btnSaveActionPerformed

    private void txtRePasswordActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtRePasswordActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtRePasswordActionPerformed

    private void txtUsernameMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtUsernameMouseClicked
        // TODO add your handling code here:
        txtUsername.setBorder(BorderFactory.createEtchedBorder());

    }//GEN-LAST:event_txtUsernameMouseClicked

    private void txtPasswordMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtPasswordMouseClicked
        // TODO add your handling code here:
        txtPassword.setBorder(BorderFactory.createEtchedBorder());
    }//GEN-LAST:event_txtPasswordMouseClicked

    private void txtRePasswordMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtRePasswordMouseClicked
        // TODO add your handling code here:
        txtRePassword.setBorder(BorderFactory.createEtchedBorder());
    }//GEN-LAST:event_txtRePasswordMouseClicked

    private void txtShortNameMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtShortNameMouseClicked
        // TODO add your handling code here:
        txtShortName.setBorder(BorderFactory.createEtchedBorder());

    }//GEN-LAST:event_txtShortNameMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnClear;
    private javax.swing.JButton btnSave;
    private javax.swing.JCheckBox cbActive;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblUser;
    private javax.swing.JPasswordField txtPassword;
    private javax.swing.JPasswordField txtRePassword;
    private javax.swing.JTextField txtShortName;
    private javax.swing.JTextField txtUsername;
    // End of variables declaration//GEN-END:variables
}
