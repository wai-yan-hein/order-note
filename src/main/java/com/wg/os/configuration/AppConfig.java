package com.wg.os.configuration;

import java.util.Properties;
import org.apache.log4j.PropertyConfigurator;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan({
    "com.wg.os"})
//@Import({MessagingConfiguration.class, MessagingListnerConfiguration.class})
public class AppConfig {

    public AppConfig() {
        super();
        Properties props = new Properties();

        //Console log
        props.put("log4j.appender.stdout", "org.apache.log4j.ConsoleAppender");
        props.put("log4j.appender.stdout.Target", "System.out");
        props.put("log4j.rootLogger", "ERROR, stdout");
        props.put("log4j.appender.stdout.layout", "org.apache.log4j.PatternLayout");
        props.put("log4j.appender.stdout.layout.ConversionPattern", "%d{dd MMM yyyy HH:mm:ss,SSS} %-5p: %c - %m%n");
        props.put("log4j.logger.org.springframework", "ERROR");
        props.put("log4j.logger.com.cv.inventoryswing", "INFO");

        //File log
        /*props.put("log4j.rootLogger", "ERROR, FILE");
        props.put("log4j.appender.FILE", "org.apache.log4j.FileAppender");
        props.put("log4j.appender.FILE.File", "C:\\CoreValue\\logfile\\inventory-swing.log");
        props.put("log4j.appender.FILE.ImmediateFlush", true);
        props.put("og4j.appender.FILE.Threshold", "ERROR");
        props.put("log4j.appender.FILE.Append", true);
        props.put("og4j.appender.FILE.MaxFileSize", "5KB");
        props.put("log4j.appender.FILE.layout", "org.apache.log4j.PatternLayout");
        props.put("log4j.appender.FILE.layout.conversionPattern", "%d{dd MMM yyyy HH:mm:ss,SSS} %-5p: %c - %m%n");
        props.put("og4j.appender.FILE.Threshold", "ERROR");
        props.put("log4j.logger.org.springframework", "ERROR");
        props.put("log4j.logger.com.cv.inventoryfx", "ERROR");*/
        PropertyConfigurator.configure(props);
    }

}
