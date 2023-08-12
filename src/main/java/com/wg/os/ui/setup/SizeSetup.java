/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wg.os.ui.setup;

import com.mongodb.client.result.DeleteResult;
import com.wg.os.common.Global;
import com.wg.os.document.ItemSize;
import com.wg.os.service.SizeService;
import com.wg.os.ui.common.SizeTabelModel;
import com.wg.os.util.Util1;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JFrame;
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
public class SizeSetup extends javax.swing.JPanel {

    private static final Logger LOGGER = LoggerFactory.getLogger(SizeSetup.class);
    private ItemSize size;
    private int selectRow = -1;

    @Autowired
    private SizeTabelModel sizeTabelModel;
    @Autowired
    private SizeService sizeService;

    /**
     * Creates new form SizeSetup
     */
    public SizeSetup() {
        initComponents();
    }

    public void initTabel() {
        Util1.setLookAndFeel(this);
        setCurrentFont();
        tblSize.setModel(sizeTabelModel);
        sizeTabelModel.setListSize(sizeService.findAll());
        tblSize.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tblSize.getSelectionModel().addListSelectionListener((ListSelectionEvent e) -> {
            if (e.getValueIsAdjusting()) {
                if (tblSize.getSelectedRow() >= 0) {
                    selectRow = tblSize.convertRowIndexToModel(tblSize.getSelectedRow());
                }
            }
        });
        tblSize.getInputMap().put(KeyStroke.getKeyStroke("DELETE"), "Del-Action");
        tblSize.getActionMap().put("Del-Action", actionItemDelete);

    }
    private final Action actionItemDelete = new AbstractAction() {

        @Override
        public void actionPerformed(ActionEvent e) {
            int row = tblSize.getSelectedRow();

            if (row >= 0) {
                try {
                    int showConfirmDialog = JOptionPane.showConfirmDialog(new JFrame(), "Are you sure to Delete");
                    if (showConfirmDialog == JOptionPane.YES_OPTION) {
                        ItemSize size = sizeTabelModel.getSize(row);
                        DeleteResult remove = sizeService.remove(size.getId());
                        if (remove.getDeletedCount() == 1) {
                            sizeTabelModel.deleteSize(row);
                        }
                    }

                } catch (HeadlessException ex) {
                    LOGGER.info("Remove Size :" + ex.getMessage());
                }
            }
        }
    };

    private void save() {

        try {
            if (isValidEntry()) {
                sizeTabelModel.addSize(size);
            }
        } catch (Exception e) {
            LOGGER.error("saveSize  :" + e.getMessage());
        }
    }

    private boolean isValidEntry() {
        boolean status = false;
        String SizeName = txtSizeName.getText();
        if (SizeName.isEmpty()) {
            JOptionPane.showMessageDialog(new JFrame(), "Field Cannot be empty...", "Size Setup", JOptionPane.ERROR_MESSAGE);
            status = false;
        } else {
            size = new ItemSize();
            size.setItemSize(SizeName);
            ItemSize item = sizeService.save(size);
            if (item != null) {
                JOptionPane.showMessageDialog(Global.parentFrame, "Saved");
                clear();
                status = true;
            }
        }
        return status;
    }

    private void clear() {
        txtSizeName.setText("");

    }

    private void setSize(ItemSize size) {
        txtSizeName.setText(size.getItemSize());
    }

    private void setCurrentFont() {
        java.awt.Component[] components = getComponents();
        for (java.awt.Component com : components) {
            com.setFont(Global.font);
        }
        tblSize.setFont(Global.font);
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
        tblSize = new javax.swing.JTable();
        txtSizeName = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();

        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                formComponentShown(evt);
            }
        });

        tblSize.setFont(Global.font);
        jScrollPane1.setViewportView(tblSize);

        txtSizeName.setFont(Global.font);
        txtSizeName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtSizeNameActionPerformed(evt);
            }
        });

        jLabel2.setFont(Global.font);
        jLabel2.setText("Size");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtSizeName, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(40, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtSizeName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 388, Short.MAX_VALUE))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void formComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_formComponentShown
        // TODO add your handling code here:
        LOGGER.info("SizeSetup Component Shown");

    }//GEN-LAST:event_formComponentShown

    private void txtSizeNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSizeNameActionPerformed
        // TODO add your handling code here:
        save();
    }//GEN-LAST:event_txtSizeNameActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblSize;
    private javax.swing.JTextField txtSizeName;
    // End of variables declaration//GEN-END:variables
}