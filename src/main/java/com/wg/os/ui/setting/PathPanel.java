/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wg.os.ui.setting;

import com.wg.os.common.Global;
import com.wg.os.service.SettingService;
import com.wg.os.util.Util1;
import java.awt.FileDialog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author Lenovo
 */
@Component
public class PathPanel extends javax.swing.JPanel {

    @Autowired
    private SettingService settingService;

    /**
     * Creates new form PathPanel
     */
    public PathPanel() {
        initComponents();
    }

    public void assignDefalutValue() {
        Util1.setLookAndFeel(this);
        txtImagePath.setText(Global.setting.getImgPath());
        txtRestartPath.setText(Global.setting.getRetartPath());
    }

    private void openDialog(String type) {
        FileDialog dialog = new FileDialog(Global.parentFrame);
        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);
        String dir = dialog.getDirectory();
        String fileName = dialog.getFile();
        if (dir != null) {
            switch (type) {
                case "ImagePath":
                    txtImagePath.setText(dir);
                    Global.setting.setImgPath(dir);
                    break;
                case "RestartPath":
                    txtRestartPath.setText(dir.concat("\\" + fileName));
                    Global.setting.setRetartPath(dir.concat("\\" + fileName));
                    break;
                default:
                    break;
            }
            settingService.save(Global.setting);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txtImagePath = new javax.swing.JTextField();
        txtRestartPath = new javax.swing.JTextField();
        btnImage = new javax.swing.JButton();
        btnRestart = new javax.swing.JButton();

        jLabel1.setText("Image Path");

        jLabel2.setText("Restart Path");

        txtRestartPath.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtRestartPathActionPerformed(evt);
            }
        });

        btnImage.setText("...");
        btnImage.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnImageActionPerformed(evt);
            }
        });

        btnRestart.setText("...");
        btnRestart.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRestartActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtImagePath, javax.swing.GroupLayout.DEFAULT_SIZE, 229, Short.MAX_VALUE)
                    .addComponent(txtRestartPath))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnImage)
                    .addComponent(btnRestart))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtImagePath, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnImage))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtRestartPath, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnRestart))
                .addContainerGap(200, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void txtRestartPathActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtRestartPathActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtRestartPathActionPerformed

    private void btnImageActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnImageActionPerformed
        // TODO add your handling code here:
        openDialog("ImagePath");
    }//GEN-LAST:event_btnImageActionPerformed

    private void btnRestartActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRestartActionPerformed
        // TODO add your handling code here:
        openDialog("RestartPath");

    }//GEN-LAST:event_btnRestartActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnImage;
    private javax.swing.JButton btnRestart;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JTextField txtImagePath;
    private javax.swing.JTextField txtRestartPath;
    // End of variables declaration//GEN-END:variables
}
