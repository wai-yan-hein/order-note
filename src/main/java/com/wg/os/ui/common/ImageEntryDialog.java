/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wg.os.ui.common;

import com.wg.os.common.Global;
import com.wg.os.document.ItemAmt;
import com.wg.os.document.ItemColor;
import com.wg.os.document.ItemSize;
import com.wg.os.document.OrderDetail;
import org.springframework.stereotype.Component;
import com.wg.os.service.ColorService;
import com.wg.os.service.ItemAmtService;
import com.wg.os.service.SizeService;
import com.wg.os.util.BindingUtil;
import com.wg.os.util.Util1;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

/**
 *
 * @author Lenovo
 */
@Component
public class ImageEntryDialog extends javax.swing.JDialog implements KeyListener {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(ImageEntryDialog.class);
    private SelectionObserver observer;
    private String imgPath;
    private ItemAmt itemAmt;
    private OrderDetail od;
    
    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }
    
    public void setObserver(SelectionObserver observer) {
        this.observer = observer;
    }
    
    JLabel labelImg = new JLabel();

    /**
     * Creates new form ImageEntryDialog
     *
     * @param parent
     * @param modal
     * @param imgPath
     */
    @Autowired
    private ColorService colorService;
    @Autowired
    private SizeService sizeService;
    @Autowired
    private ItemAmtService itemAmtService;
    
    public ImageEntryDialog() {
        super(new javax.swing.JFrame(), true);
        initComponents();
        initKeyListener();
        
    }
    
    private void getItemAmt() {
        itemAmt = itemAmtService.findById(imgPath);
        if (itemAmt == null) {
            txtPurPrice.setText("0.0");
            txtOrderPrice.setText("0.0");
        } else {
            txtPurPrice.setText(itemAmt.getPurPrice().toString());
            txtOrderPrice.setText(itemAmt.getOrderPrice().toString());
        }
    }
    
    private void assignDefaultValue() {
        setCurrentFont();
        txtQty.setText("1");
        txtDiscount.setText("0.0");
        getItemAmt();
    }
    
    private void initKeyListener() {
        txtOrderPrice.addKeyListener(this);
        txtQty.addKeyListener(this);
        txtDiscount.addKeyListener(this);
        txtPurPrice.addKeyListener(this);
        
    }
    
    private void initCombo() {
        BindingUtil.BindComboFilter(cboColor, colorService.findAll(), null, true, false);
        BindingUtil.BindComboFilter(cboSize, sizeService.findAll(), null, true, false);
        
    }
    
    private void setImageForm(String imagePath) {
        LOGGER.info("Set Image :" + imagePath);
        ImageIcon imgIcon = new ImageIcon(imagePath);
        labelImg.setSize(320, 420);
        Image img = imgIcon.getImage().getScaledInstance(320, 420, Image.SCALE_SMOOTH);
        labelImg.setIcon(new ImageIcon(img));
        labelImg.setHorizontalAlignment(JLabel.CENTER);
        panelImage.add(labelImg);
        panelImage.setLayout(new FlowLayout(FlowLayout.CENTER));
        panelImage.revalidate();
        panelImage.repaint();
        
    }
    
    private void clear() {
        txtOrderPrice.setText("");
        
    }
    
    private void saveItemAmt() {
        Double nAmt = Util1.getDouble(txtPurPrice.getText());
        Double oAmt = Util1.getDouble(txtOrderPrice.getText());
        if (itemAmt == null) {
            itemAmt = new ItemAmt();
            itemAmt.setImgPath(imgPath);
        }
        if (nAmt > 0) {
            itemAmt.setPurPrice(nAmt);
            if (oAmt > 0) {
                itemAmt.setOrderPrice(oAmt);
            }
            itemAmtService.save(itemAmt);
        }
        
    }
    
    private void sendDataToOrderNote() {
        if (isValidEntry()) {
            observer.selected("ImageEntryDialog", od);
            this.dispose();
        }
    }
    
    private boolean isValidEntry() {
        boolean status = false;
        if (observer != null) {
            String oPrice = txtOrderPrice.getText();
            String pPrice = txtPurPrice.getText();
            if (Util1.getDouble(pPrice) < 0 || Util1.getDouble(oPrice) < 0 || Util1.getDouble(txtQty.getText()) < 0) {
                JOptionPane.showMessageDialog(Global.parentFrame, "Invalid Entry", "Message", JOptionPane.ERROR_MESSAGE);
                status = false;
            } else {
                od = new OrderDetail();
                ItemColor color = (ItemColor) cboColor.getSelectedItem();
                ItemSize size = (ItemSize) cboSize.getSelectedItem();
                od.setItemColor(color.getColorName());
                od.setItemSize(size.getItemSize());
                od.setItemImage(imgPath);
                od.setItemPurPrice(Util1.getDouble(txtPurPrice.getText()));
                od.setItemPrice(Util1.getDouble(txtOrderPrice.getText()));
                od.setItemQty(Util1.getNumber(txtQty.getText()));
                od.setItemDiscount(Util1.getDouble(txtDiscount.getText()));
                saveItemAmt();
                clear();
                status = true;
            }
        }
        return status;
    }
    
    private void setCurrentFont() {
        java.awt.Component[] components = mainPanel.getComponents();
        for (java.awt.Component com : components) {
            com.setFont(Global.font);
        }
    }

    /**
     * \
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        mainPanel = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        cboSize = new javax.swing.JComboBox<>();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txtOrderPrice = new javax.swing.JTextField();
        panelImage = new javax.swing.JPanel();
        cboColor = new javax.swing.JComboBox();
        txtQty = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txtDiscount = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        txtPurPrice = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                formComponentShown(evt);
            }
        });

        jLabel1.setFont(Global.font);
        jLabel1.setText("Size");

        cboSize.setFont(Global.font);

        jLabel2.setFont(Global.font);
        jLabel2.setText("Color");

        jLabel3.setFont(Global.font);
        jLabel3.setText("Order Price");

        txtOrderPrice.setFont(Global.font);
        txtOrderPrice.setName("txtOrderPrice"); // NOI18N
        txtOrderPrice.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtOrderPriceMouseClicked(evt);
            }
        });
        txtOrderPrice.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtOrderPriceActionPerformed(evt);
            }
        });

        panelImage.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        javax.swing.GroupLayout panelImageLayout = new javax.swing.GroupLayout(panelImage);
        panelImage.setLayout(panelImageLayout);
        panelImageLayout.setHorizontalGroup(
            panelImageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 320, Short.MAX_VALUE)
        );
        panelImageLayout.setVerticalGroup(
            panelImageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 420, Short.MAX_VALUE)
        );

        cboColor.setFont(Global.font);
        cboColor.setName("cboColor"); // NOI18N

        txtQty.setFont(Global.font);
        txtQty.setName("txtQty"); // NOI18N
        txtQty.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtQtyMouseClicked(evt);
            }
        });

        jLabel4.setFont(Global.font);
        jLabel4.setText("Qty");

        jLabel5.setFont(Global.font);
        jLabel5.setText("Discount %");

        txtDiscount.setFont(Global.font);
        txtDiscount.setName("txtDiscount"); // NOI18N
        txtDiscount.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtDiscountMouseClicked(evt);
            }
        });

        jLabel6.setFont(Global.font);
        jLabel6.setText("Purchase Price");

        txtPurPrice.setFont(Global.font);
        txtPurPrice.setName("txtPurPrice"); // NOI18N
        txtPurPrice.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtPurPriceMouseClicked(evt);
            }
        });

        jButton1.setText("OK");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout mainPanelLayout = new javax.swing.GroupLayout(mainPanel);
        mainPanel.setLayout(mainPanelLayout);
        mainPanelLayout.setHorizontalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panelImage, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(59, 59, 59)
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(mainPanelLayout.createSequentialGroup()
                        .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, 149, Short.MAX_VALUE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(31, 31, 31)
                        .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtOrderPrice)
                            .addComponent(cboColor, 0, 215, Short.MAX_VALUE)
                            .addComponent(txtQty)
                            .addComponent(cboSize, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtDiscount)
                            .addComponent(txtPurPrice)))
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        mainPanelLayout.setVerticalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(mainPanelLayout.createSequentialGroup()
                        .addGap(42, 42, 42)
                        .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(cboSize, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(cboColor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(15, 15, 15)
                        .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtQty, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4))
                        .addGap(18, 18, 18)
                        .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(txtDiscount, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtPurPrice)
                            .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtOrderPrice, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton1))
                    .addComponent(panelImage, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        mainPanelLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jLabel1, jLabel2, jLabel3, jLabel4, jLabel5});

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(mainPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(mainPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_formComponentShown
        // TODO add your handling code here:
        LOGGER.info("ImageEntryDialog Component Shown");
        Util1.setLookAndFeel(this);
        setImageForm(imgPath);
        assignDefaultValue();
        initCombo();
        cboSize.requestFocus();
        

    }//GEN-LAST:event_formComponentShown

    private void txtOrderPriceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtOrderPriceActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtOrderPriceActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        sendDataToOrderNote();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void txtQtyMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtQtyMouseClicked
        // TODO add your handling code here
        txtQty.setText("");
    }//GEN-LAST:event_txtQtyMouseClicked

    private void txtDiscountMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtDiscountMouseClicked
        // TODO add your handling code here:
        txtDiscount.setText("");
    }//GEN-LAST:event_txtDiscountMouseClicked

    private void txtPurPriceMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtPurPriceMouseClicked
        // TODO add your handling code here:
        txtPurPrice.setText("");
    }//GEN-LAST:event_txtPurPriceMouseClicked

    private void txtOrderPriceMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtOrderPriceMouseClicked
        // TODO add your handling code here:
        txtOrderPrice.setText("");
    }//GEN-LAST:event_txtOrderPriceMouseClicked

    /**
     * @param args the command line arguments
     */

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox cboColor;
    private javax.swing.JComboBox<String> cboSize;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel mainPanel;
    private javax.swing.JPanel panelImage;
    private javax.swing.JTextField txtDiscount;
    private javax.swing.JTextField txtOrderPrice;
    private javax.swing.JTextField txtPurPrice;
    private javax.swing.JTextField txtQty;
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
        if (sourceObj instanceof JTextField) {
            ctrlName = ((JTextField) sourceObj).getName();
        }
        switch (ctrlName) {
            case "txtOrderPrice":
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    sendDataToOrderNote();
                }
                break;
            case "txtQty":
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    txtDiscount.requestFocus();
                    txtDiscount.setText("");
                }
                break;
            case "txtDiscount":
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    txtPurPrice.requestFocus();
                    txtPurPrice.setText("");
                }
                break;
            case "txtPurPrice":
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    txtOrderPrice.requestFocus();
                    txtOrderPrice.setText("");
                }
                break;
            
        }
    }
}
