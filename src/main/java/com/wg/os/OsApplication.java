package com.wg.os;

import com.wg.os.common.Global;
import com.wg.os.document.Setting;
import com.wg.os.service.SettingService;
import com.wg.os.ui.ApplicationMainFrame;
import com.wg.os.ui.LoginDialog;
import java.io.IOException;
import java.net.ServerSocket;
import java.util.TimeZone;
import javax.annotation.PostConstruct;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class OsApplication implements CommandLineRunner {

    private static final Logger LOGGER = LoggerFactory.getLogger(OsApplication.class);
    @Autowired
    private SettingService settingService;

    @PostConstruct
    void started() {
        TimeZone.setDefault(TimeZone.getTimeZone("TimeZone"));
    }

    public static void main(String[] args) {
        /*try {
        UIManager.setLookAndFeel("com.jtattoo.plaf.acryl.AcrylLookAndFeel");
        } catch (UnsupportedLookAndFeelException e) {
        LOGGER.error("Look and Feel :" + e.getMessage());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException ex) {
        LOGGER.error("Look And Feel :" + ex.getMessage());
        }*/
        //PlasticLookAndFeel laf = new Plastic3DLookAndFeel();
        //PlasticLookAndFeel.setCurrentTheme(new SkyYellow());
        /*try {
        UIManager.setLookAndFeel("com.jtattoo.plaf.aero.AeroLookAndFeel");
        } catch (UnsupportedLookAndFeelException e) {
        LOGGER.info("Look and Feel :" + e.getMessage());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException ex) {
        java.util.logging.Logger.getLogger(OsApplication.class.getName()).log(Level.SEVERE, null, ex);
        }*/
 /* try {
        UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
        java.util.logging.Logger.getLogger(OsApplication.class.getName()).log(Level.SEVERE, null, ex);
        }*/
        try {
            Global.sock = new ServerSocket(10002);//Pharmacy
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(new JFrame(), "You cannot run two program at the same time in the same machine.",
                    "Duplicate Program running.", JOptionPane.ERROR_MESSAGE);
            System.exit(-1);
        }

        try {
            SpringApplicationBuilder builder = new SpringApplicationBuilder(OsApplication.class);
            builder.headless(false);
            builder.web(WebApplicationType.NONE);
            builder.bannerMode(Banner.Mode.OFF);
            ConfigurableApplicationContext context = builder.run(args);
            LoginDialog loginDialog = context.getBean(LoginDialog.class);
            loginDialog.addWindowListener(new java.awt.event.WindowAdapter() {
                @Override
                public void windowClosing(java.awt.event.WindowEvent e) {
                    context.close();
                    System.exit(0);
                }
            });
            loginDialog.initializeData();
            loginDialog.setLocationRelativeTo(null);
            loginDialog.setVisible(true);

            if (loginDialog.isLogin()) {

                ApplicationMainFrame appMain = context.getBean(ApplicationMainFrame.class);
                java.awt.EventQueue.invokeLater(() -> {
                    appMain.setExtendedState(JFrame.MAXIMIZED_BOTH);
                    appMain.assignDefalutValue();
                    appMain.defaultPanel();
                    appMain.setVisible(true);
                });
            } else {
                context.close();
                System.exit(0);
            }
        } catch (BeansException ex) {
            LOGGER.error("main : " + ex.getMessage());
        }

    }

    public void initializeData() {

    }

    @Override
    public void run(String... args) throws Exception {

    }
}
