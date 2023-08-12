/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wg.os.ui;

import com.wg.os.common.Global;
import com.wg.os.common.GlobalColor;
import com.wg.os.document.CurrentFont;
import com.wg.os.document.Payment;
import com.wg.os.document.Setting;
import com.wg.os.document.Theme;
import com.wg.os.document.User;
import com.wg.os.service.PaymentService;
import com.wg.os.service.SettingService;
import com.wg.os.service.UserService;
import com.wg.os.util.Util1;
import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.FocusAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Component;

/**
 *
 * @author winswe
 */
@Component
public class LoginDialog extends javax.swing.JDialog implements KeyListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoginDialog.class);
    private boolean login = false;
    private int loginAttempt = 0;
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

    @Autowired
    private TaskExecutor taskExecutor;
    @Autowired
    private UserService userService;
    @Autowired
    private SettingService settingService;
    @Autowired
    private PaymentService paymentService;

    /**
     * Creates new form LoginDialog
     */
    public LoginDialog() {
        super(new javax.swing.JFrame(), true);
        initComponents();
        txtLoginDate.setText(Util1.getTodayDateStr("dd/MM/yyyy"));
        ImageIcon size = new ImageIcon(getClass().getResource("/image/order_note.png"));
        setIconImage(size.getImage());
        initKeyListener();
        initFocusListener();
        // createFont();
        //txtLoginDate.setText(Util1.getTodayDateStr("dd/MM/yyyy"));
    }

    private void createFont() {
        Setting setting = settingService.findById("setting");
        if (setting != null) {
            Global.setting = setting;
            //setLookAndFeel(Global.setting.getTheme().getPath());
        } else {
            defaultSetting();
        }
        CurrentFont cf = Global.setting.getFont();
        Global.font = new Font(cf.getFontName(), cf.getFontStyle(), cf.getFontSize());
        setFont();
        settingService.save(Global.setting);

    }

    public void initializeData() {

        List<Payment> listPayment = paymentService.findAll();
        if (listPayment.isEmpty()) {
            Payment payment = new Payment();
            payment.setId("1");
            payment.setPayment("Cash");
            paymentService.save(payment);
            payment = new Payment();
            payment.setId("2");
            payment.setPayment("Credit");
            paymentService.save(payment);
        }
        createFont();
        Util1.setLookAndFeel(this);
    }

    private void defaultSetting() {
        Global.setting = new Setting();
        Global.setting.setId("setting");
        String username = System.getProperty("user.name");
        Global.setting.setImgPath("C:\\Users\\" + username + "\\Pictures");
        Global.setting.setFont(new CurrentFont("Pyidaungsu", 1, 14));
        Global.setting.setRetartPath("C:\\OrderNote\\OrderNote.jar");
        String dTheme = "com.jtattoo.plaf.smart.SmartLookAndFeel";
        Global.setting.setTheme(new Theme("Smart", dTheme));
    }

    /* private void setLookAndFeel(String theme) {
    try {
    UIManager.setLookAndFeel(theme);
    } catch (UnsupportedLookAndFeelException e) {
    LOGGER.error("Look and Feel :" + e.getMessage());
    } catch (ClassNotFoundException | InstantiationException | IllegalAccessException ex) {
    LOGGER.error("Look And Feel :" + ex.getMessage());
    }
    
    }*/
    private void setFont() {
        java.awt.Component[] loginComps = panelLogin.getComponents();
        for (java.awt.Component loginComp : loginComps) {
            loginComp.setFont(Global.font);
        }
    }

    //KeyListener implementation
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
            case "txtLoginName":
                if (e.getKeyCode() == KeyEvent.VK_ENTER || e.getKeyCode() == KeyEvent.VK_DOWN) {
                    txtPassword.requestFocus();
                } else if (e.getKeyCode() == KeyEvent.VK_UP) {
                    butClear.requestFocus();
                }
                break;
            case "txtPassword":
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_ENTER: //Login
                        login();
                        break;
                    case KeyEvent.VK_DOWN:
                        txtLoginDate.requestFocus();
                        break;
                    case KeyEvent.VK_UP:
                        txtLoginName.requestFocus();
                        break;
                }
                break;

            /*if (e.getKeyCode() == KeyEvent.VK_ENTER || e.getKeyCode() == KeyEvent.VK_DOWN) {

                 } else if (e.getKeyCode() == KeyEvent.VK_UP) {
                 if (!cboSession.isPopupVisible()) {
                 txtLoginDate.requestFocus();
                 }
                 }*/
            case "butLogin":
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_ENTER:
                        login();
                        break;
                    case KeyEvent.VK_DOWN:
                        butClear.requestFocus();
                        break;

                }
                break;
            case "butClear":
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_ENTER:
                        clear();
                        break;
                    case KeyEvent.VK_DOWN:
                        txtLoginName.requestFocus();
                        break;
                    case KeyEvent.VK_UP:
                        butLogin.requestFocus();
                        break;
                }
        }
    }
    //======End KeyListener implementation ======

    public boolean isLogin() {
        return login;
    }

    private void login() {
        String username = txtLoginName.getText();
        String pass = new String(txtPassword.getPassword());
        if (username.isEmpty() || pass.isEmpty()) {
            JOptionPane.showMessageDialog(Global.parentFrame, "Empty Entry", "Login", JOptionPane.ERROR_MESSAGE);
            txtLoginName.setBorder(BorderFactory.createLineBorder(Color.red));
            login = false;
        } else {
            User user =new User();
            user.setIsActive(true);
            if (user != null) {
                if (user.getIsActive()) {
                    Global.userName = user.getUserName();
                    login = true;
                    this.dispose();
                }
            } else {
                JOptionPane.showMessageDialog(Global.parentFrame, "The username or password is incorrect !", "Login", JOptionPane.ERROR_MESSAGE);
                txtLoginName.setBorder(BorderFactory.createLineBorder(Color.red));
                txtPassword.setBorder(BorderFactory.createLineBorder(Color.red));
                loginAttempt++;
                if (loginAttempt > 2) {
                    login = false;
                    this.dispose();
                }
            }

        }
    }

    private void clear() {
        txtLoginName.setText(null);
        txtPassword.setText(null);
        //FtxtLoginDate.setText(Util1.getTodayDateStr("dd/MM/yyyy"));
        txtLoginName.requestFocus();
    }

    private void initKeyListener() {
        txtLoginDate.addKeyListener(this);
        txtLoginName.addKeyListener(this);
        txtPassword.addKeyListener(this);
        butClear.addKeyListener(this);
        butLogin.addKeyListener(this);
    }

    private void initFocusListener() {
        txtLoginDate.addFocusListener(fa);
        txtLoginName.addFocusListener(fa);
        txtPassword.addFocusListener(fa);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelLogin = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txtLoginDate = new javax.swing.JTextField();
        txtPassword = new javax.swing.JPasswordField();
        txtLoginName = new javax.swing.JTextField();
        butLogin = new javax.swing.JButton();
        butClear = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Order Note");
        setBackground(new java.awt.Color(255, 255, 255));
        setResizable(false);

        jLabel1.setFont(Global.font);
        jLabel1.setText("Login Name ");

        jLabel2.setFont(Global.font);
        jLabel2.setText("Password");

        jLabel3.setFont(Global.font);
        jLabel3.setText("Login Date");

        txtLoginDate.setFont(Global.font);

        txtPassword.setFont(Global.font);
        txtPassword.setBorder(javax.swing.BorderFactory.createEtchedBorder(GlobalColor.tbBorderColor, null));
        txtPassword.setName("txtPassword"); // NOI18N
        txtPassword.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtPasswordMouseClicked(evt);
            }
        });

        txtLoginName.setFont(Global.font);
        txtLoginName.setBorder(javax.swing.BorderFactory.createEtchedBorder(GlobalColor.tbBorderColor, null));
        txtLoginName.setName("txtLoginName"); // NOI18N
        txtLoginName.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtLoginNameMouseClicked(evt);
            }
        });

        butLogin.setText("Login");
        butLogin.setName("butLogin"); // NOI18N
        butLogin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butLoginActionPerformed(evt);
            }
        });

        butClear.setText("Clear");
        butClear.setName("butClear"); // NOI18N

        javax.swing.GroupLayout panelLoginLayout = new javax.swing.GroupLayout(panelLogin);
        panelLogin.setLayout(panelLoginLayout);
        panelLoginLayout.setHorizontalGroup(
            panelLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelLoginLayout.createSequentialGroup()
                .addGroup(panelLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelLoginLayout.createSequentialGroup()
                        .addGap(0, 12, Short.MAX_VALUE)
                        .addGroup(panelLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(panelLoginLayout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addGap(18, 18, 18)
                                .addComponent(txtLoginDate, javax.swing.GroupLayout.PREFERRED_SIZE, 191, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(panelLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addGroup(panelLoginLayout.createSequentialGroup()
                                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(18, 18, 18)
                                    .addComponent(txtLoginName, javax.swing.GroupLayout.PREFERRED_SIZE, 191, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(panelLoginLayout.createSequentialGroup()
                                    .addComponent(jLabel2)
                                    .addGap(18, 18, 18)
                                    .addComponent(txtPassword)))))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelLoginLayout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(butLogin)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(butClear)))
                .addContainerGap())
        );

        panelLoginLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jLabel1, jLabel2, jLabel3});

        panelLoginLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {butClear, butLogin});

        panelLoginLayout.setVerticalGroup(
            panelLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelLoginLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtLoginName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtPassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addComponent(txtLoginDate, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(butLogin)
                    .addComponent(butClear))
                .addContainerGap(62, Short.MAX_VALUE))
        );

        panelLoginLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {txtLoginDate, txtLoginName, txtPassword});

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelLogin, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelLogin, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void butLoginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butLoginActionPerformed
        login();
    }//GEN-LAST:event_butLoginActionPerformed

    private void txtLoginNameMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtLoginNameMouseClicked
        // TODO add your handling code here:
        txtLoginName.setBorder(BorderFactory.createEtchedBorder());
    }//GEN-LAST:event_txtLoginNameMouseClicked

    private void txtPasswordMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtPasswordMouseClicked
        // TODO add your handling code here:
        txtPassword.setBorder(BorderFactory.createEtchedBorder());
    }//GEN-LAST:event_txtPasswordMouseClicked

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton butClear;
    private javax.swing.JButton butLogin;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel panelLogin;
    private javax.swing.JTextField txtLoginDate;
    private javax.swing.JTextField txtLoginName;
    private javax.swing.JPasswordField txtPassword;
    // End of variables declaration//GEN-END:variables
}
