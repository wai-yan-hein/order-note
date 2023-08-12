/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wg.os.ui.setup;

import com.mongodb.client.result.DeleteResult;
import com.wg.os.common.Global;
import com.wg.os.document.ItemColor;
import com.wg.os.service.ColorService;
import com.wg.os.ui.common.ColorTabelModel;
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
public class ColorSetup extends javax.swing.JPanel {

    private static final Logger LOGGER = LoggerFactory.getLogger(ColorSetup.class);
    private ItemColor color;
    private int selectRow = -1;

    @Autowired
    private ColorTabelModel colorTabelModel;
    @Autowired
    private ColorService colorService;

    /**
     * Creates new form ColorSetup
     */
    public ColorSetup() {
        initComponents();
    }

    public void initTabel() {
        Util1.setLookAndFeel(this);
        setCurrentFont();
        tblColor.setModel(colorTabelModel);
        colorTabelModel.setListColor(colorService.findAll());
        tblColor.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tblColor.getSelectionModel().addListSelectionListener((ListSelectionEvent e) -> {
            if (e.getValueIsAdjusting()) {
                if (tblColor.getSelectedRow() >= 0) {
                    selectRow = tblColor.convertRowIndexToModel(tblColor.getSelectedRow());
                }

            }
        });
        tblColor.getInputMap().put(KeyStroke.getKeyStroke("DELETE"), "Del-Action");
        tblColor.getActionMap().put("Del-Action", actionItemDelete);

    }
    private final Action actionItemDelete = new AbstractAction() {

        @Override
        public void actionPerformed(ActionEvent e) {
            int row = tblColor.getSelectedRow();

            if (row >= 0) {
                try {
                    int showConfirmDialog = JOptionPane.showConfirmDialog(Global.parentFrame, "Are you sure to Delete");
                    if (showConfirmDialog == JOptionPane.YES_OPTION) {
                        ItemColor color = colorTabelModel.getColor(row);
                        DeleteResult remove = colorService.remove(color.getId());
                        if (remove.getDeletedCount() == 1) {
                            colorTabelModel.deleteColor(row);
                        }
                    }
                } catch (HeadlessException ex) {
                    LOGGER.info("Remove City :" + ex.getMessage());
                }
            }
        }
    };

    private void save() {

        try {
            if (isValidEntry()) {
                colorTabelModel.addColor(color);
            }
        } catch (Exception e) {
            LOGGER.error("saveColor  :" + e.getMessage());
        }
    }

    private boolean isValidEntry() {
        boolean status = false;
        String colorName = txtColorName.getText();
        if (colorName.isEmpty()) {
            JOptionPane.showMessageDialog(new JFrame(), "Field Cannot be empty...", "Color Setup", JOptionPane.ERROR_MESSAGE);
            status = false;
        } else {
            color = new ItemColor();
            color.setColorName(colorName);
            ItemColor save = colorService.save(color);
            if (save != null) {
                clear();
                JOptionPane.showMessageDialog(Global.parentFrame, "Saved");
                status = true;
            }
        }
        return status;
    }

    private void clear() {
        txtColorName.setText(null);

    }

    private void setColor(ItemColor color) {
        txtColorName.setText(color.getColorName());
    }

    private void setCurrentFont() {
        java.awt.Component[] components = getComponents();
        for (java.awt.Component com : components) {
            com.setFont(Global.font);
        }
        tblColor.setFont(Global.font);
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
        tblColor = new javax.swing.JTable();
        txtColorName = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();

        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                formComponentShown(evt);
            }
        });

        tblColor.setFont(Global.font);
        jScrollPane1.setViewportView(tblColor);

        txtColorName.setFont(Global.font);
        txtColorName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtColorNameActionPerformed(evt);
            }
        });

        jLabel2.setFont(Global.font);
        jLabel2.setText("Color Name");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 17, Short.MAX_VALUE)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtColorName, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(txtColorName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 388, Short.MAX_VALUE))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void formComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_formComponentShown
        // TODO add your handling code here:
        LOGGER.info("ColorSetup Component Shown");

    }//GEN-LAST:event_formComponentShown

    private void txtColorNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtColorNameActionPerformed
        // TODO add your handling code here:
        save();
    }//GEN-LAST:event_txtColorNameActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblColor;
    private javax.swing.JTextField txtColorName;
    // End of variables declaration//GEN-END:variables
}
