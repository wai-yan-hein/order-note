/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wg.os.ui.setup;

import com.mongodb.client.result.DeleteResult;
import com.wg.os.common.Global;
import com.wg.os.document.City;
import com.wg.os.service.CityService;
import com.wg.os.ui.common.CityTabelModel;
import com.wg.os.ui.common.SelectionObserver;
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
public class CitySetup extends javax.swing.JPanel {

    private static final Logger LOGGER = LoggerFactory.getLogger(CitySetup.class);
    private City city;
    private int selectRow = -1;
    private SelectionObserver observer;

    public void setObserver(SelectionObserver observer) {
        this.observer = observer;
    }

    @Autowired
    private CityTabelModel cityTabelModel;
    @Autowired
    private CityService cityService;

    /**
     * Creates new form CitySetup
     */
    public CitySetup() {
        initComponents();
    }

    public void initTabel() {
        Util1.setLookAndFeel(this);
        setCurrentFont();
        tblCity.setModel(cityTabelModel);
        cityTabelModel.setlistCustomer(cityService.findAll());
        tblCity.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tblCity.getSelectionModel().addListSelectionListener((ListSelectionEvent e) -> {
            if (e.getValueIsAdjusting()) {
                if (tblCity.getSelectedRow() >= 0) {
                    selectRow = tblCity.convertRowIndexToModel(tblCity.getSelectedRow());
                }

            }
        });
        tblCity.getInputMap().put(KeyStroke.getKeyStroke("DELETE"), "Del-Action");
        tblCity.getActionMap().put("Del-Action", actionItemDelete);

    }
    private final Action actionItemDelete = new AbstractAction() {

        @Override
        public void actionPerformed(ActionEvent e) {
            int row = tblCity.getSelectedRow();

            if (row >= 0) {
                try {
                    int showConfirmDialog = JOptionPane.showConfirmDialog(Global.parentFrame, "Are you sure to Delete");
                    if (showConfirmDialog == JOptionPane.YES_OPTION) {
                        City city = cityTabelModel.getCity(row);
                        DeleteResult remove = cityService.remove(city.getId());
                        if (remove.getDeletedCount() == 1) {
                            cityTabelModel.deleteCity(row);
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
                cityTabelModel.addCity(city);
                if (observer != null) {
                    observer.selected("CitySetup", city);
                }
            }
        } catch (HeadlessException e) {
            LOGGER.error("saveCity  :" + e.getMessage());
        }
    }

    private boolean isValidEntry() {
        boolean status = false;
        String cityName = txtCityName.getText();
        if (cityName.isEmpty()) {
            JOptionPane.showMessageDialog(new JFrame(), "Field Cannot be empty...", "City Setup", JOptionPane.ERROR_MESSAGE);
            status = false;
        } else {
            city = new City();
            city.setCityName(cityName);
            City save = cityService.save(city);
            if (save != null) {
                clear();
                JOptionPane.showMessageDialog(Global.parentFrame, "Save");
                status = true;
            }
        }
        return status;
    }

    private void clear() {
        txtCityName.setText("");
        tblCity.getSelectionModel().clearSelection();

    }

    private void setCurrentFont() {
        java.awt.Component[] components = getComponents();
        for (java.awt.Component com : components) {
            com.setFont(Global.font);
        }
        tblCity.setFont(Global.font);
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
        tblCity = new javax.swing.JTable();
        txtCityName = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();

        setBackground(new java.awt.Color(255, 255, 255));
        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                formComponentShown(evt);
            }
        });

        tblCity.setFont(Global.font);
        tblCity.setRowHeight(20);
        jScrollPane1.setViewportView(tblCity);

        txtCityName.setFont(Global.font);
        txtCityName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCityNameActionPerformed(evt);
            }
        });

        jLabel2.setFont(Global.font);
        jLabel2.setText("City Name");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtCityName, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(22, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtCityName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 388, Short.MAX_VALUE))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void formComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_formComponentShown
        // TODO add your handling code here:
        LOGGER.info("CitySetup Component Shown");

    }//GEN-LAST:event_formComponentShown

    private void txtCityNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCityNameActionPerformed
        // TODO add your handling code here:
        save();
    }//GEN-LAST:event_txtCityNameActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblCity;
    private javax.swing.JTextField txtCityName;
    // End of variables declaration//GEN-END:variables
}
