/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wg.os.ui.setting;

import com.wg.os.common.Global;
import com.wg.os.document.Theme;
import com.wg.os.service.SettingService;
import com.wg.os.util.BindingUtil;
import com.wg.os.util.Util1;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author Lenovo
 */
@Component
public class LookAndFeel extends javax.swing.JPanel {

    private static final Logger LOGGER = LoggerFactory.getLogger(LookAndFeel.class);
    String aero = "com.jtattoo.plaf.aero.AeroLookAndFeel";
    String acry = "com.jtattoo.plaf.acryl.AcrylLookAndFeel";
    String fast = "com.jtattoo.plaf.fast.FastLookAndFeel";
    String aluminium = "com.jtattoo.plaf.aluminium.AluminiumLookAndFeel";
    String bernstein = "com.jtattoo.plaf.bernstein.BernsteinLookAndFeel";
    String graphite = "com.jtattoo.plaf.graphite.GraphiteLookAndFeel";
    String hifi = "com.jtattoo.plaf.hifi.HiFiLookAndFeel";
    String luna = "com.jtattoo.plaf.luna.LunaLookAndFeel";
    String mcwin = "com.jtattoo.plaf.mcwin.McWinLookAndFeel";
    String mint = "com.jtattoo.plaf.mint.MintLookAndFeel";
    String noire = "com.jtattoo.plaf.noire.NoireLookAndFeel";
    String smart = "com.jtattoo.plaf.smart.SmartLookAndFeel";
    String texture = "com.jtattoo.plaf.texture.TextureLookAndFeel";
    @Autowired
    private SettingService settingService;

    /**
     * Creates new form LookAndFeel
     */
    public LookAndFeel() {
        initComponents();

    }

    public void initCombo() {
        Util1.setLookAndFeel(this);
        List<Theme> listTheme = new ArrayList<>();
        listTheme.add(new Theme("Aero", aero));
        listTheme.add(new Theme("Acry", acry));
        listTheme.add(new Theme("Fast", fast));
        listTheme.add(new Theme("Aluminiumn", aluminium));
        listTheme.add(new Theme("Bernstein", bernstein));
        listTheme.add(new Theme("Graphite", graphite));
        listTheme.add(new Theme("HiFi", hifi));
        listTheme.add(new Theme("Luna", luna));
        listTheme.add(new Theme("McWin", mcwin));
        listTheme.add(new Theme("Mint", mint));
        listTheme.add(new Theme("Noire", noire));
        listTheme.add(new Theme("Smart", smart));
        listTheme.add(new Theme("Texture", texture));
        BindingUtil.BindComboFilter(cboTheme, listTheme, null, true, false);
        cboTheme.setSelectedItem(Global.setting.getTheme());
        cboTheme.addActionListener((ActionEvent e) -> {
            Theme theme = (Theme) cboTheme.getSelectedItem();
            if (theme != null) {
                setLookAndFeel(theme.getPath());
                btnApply.setEnabled(true);
                Global.setting.setTheme(theme);
            }
        });

    }

    private void setLookAndFeel(String theme) {
        try {
            UIManager.setLookAndFeel(theme);
            SwingUtilities.updateComponentTreeUI(this);
        } catch (UnsupportedLookAndFeelException e) {
            LOGGER.error("Look and Feel :" + e.getMessage());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException ex) {
            LOGGER.error("Look And Feel :" + ex.getMessage());
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

        jPanel1 = new javax.swing.JPanel();
        cboTheme = new javax.swing.JComboBox<>();
        btnApply = new javax.swing.JButton();

        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                formComponentShown(evt);
            }
        });

        jPanel1.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                jPanel1ComponentShown(evt);
            }
        });

        cboTheme.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cboThemeItemStateChanged(evt);
            }
        });

        btnApply.setText("Apply");
        btnApply.setEnabled(false);
        btnApply.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnApplyActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(cboTheme, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnApply)
                .addContainerGap(24, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cboTheme, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnApply))
                .addContainerGap(262, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void cboThemeItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cboThemeItemStateChanged
        // TODO add your handling code here:


    }//GEN-LAST:event_cboThemeItemStateChanged

    private void btnApplyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnApplyActionPerformed
        // TODO add your handling code here:
        settingService.save(Global.setting);
        btnApply.setEnabled(false);
    }//GEN-LAST:event_btnApplyActionPerformed

    private void jPanel1ComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_jPanel1ComponentShown
        // TODO add your handling code here:
    }//GEN-LAST:event_jPanel1ComponentShown

    private void formComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_formComponentShown
        // TODO add your handling code here:
    }//GEN-LAST:event_formComponentShown


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnApply;
    private javax.swing.JComboBox<String> cboTheme;
    private javax.swing.JPanel jPanel1;
    // End of variables declaration//GEN-END:variables
}
