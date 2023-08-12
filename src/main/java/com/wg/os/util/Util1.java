/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wg.os.util;

import com.wg.os.common.Global;
import java.awt.Component;
import java.net.InetAddress;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author WSwe
 */
public class Util1 {

    private static final Logger LOGGER = LoggerFactory.getLogger(Util1.class);

    public static String getEngChar(int i) {
        String[] engChar = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J",
            "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T",
            "U", "V", "W", "X", "Y", "Z"};

        if ((i + 1) < 0 || (i + 1) > engChar.length) {
            return null;
        } else {
            return engChar[i + 1];
        }
    }

    public static Date toDate(Object objDate) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;

        try {
            date = formatter.parse(objDate.toString());
        } catch (Exception ex) {
        }

        return date;
    }

    public static String toDateStrMYSQL(String strDate) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String date = null;

        try {
            date = formatter.format(toDate(strDate));
        } catch (Exception ex) {
        }

        return date;
    }

    public static String toDateStrMYSQL(Date strDate) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String date = null;

        try {
            date = formatter.format(strDate);
        } catch (Exception ex) {
        }

        return date;
    }

    public static String toDateTimeStrMYSQL(String strDate) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date = null;

        try {
            date = formatter.format(toDate(strDate, "dd/MM/yyyy"));
        } catch (Exception ex) {
        }

        return date;
    }

    public static String getTodayDateTimeStrMySql() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date = null;

        try {
            date = formatter.format(new Date());
        } catch (Exception ex) {

        }

        return date;
    }

    public static String toDateStr(String strDate, String inFormat, String outFormat) {
        SimpleDateFormat formatter = new SimpleDateFormat(outFormat);
        String date = null;

        try {
            /*if (strDate.contains("-")) {
                date = strDate;
            } else {*/
            date = formatter.format(toDate(strDate, inFormat));
            //}
        } catch (Exception ex) {
        }

        return date;
    }

    public static String toDateStrMYSQLEnd(String strDate) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String date = null;

        try {
            date = formatter.format(toDate(strDate, "dd/MM/yyyy")) + " 23:59:59";
        } catch (Exception ex) {
        }

        return date;
    }

    public static Date toDate(Object objDate, String format) {
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        Date date = null;

        try {
            date = formatter.parse(objDate.toString());
        } catch (Exception ex) {
        }

        return date;
    }

    public static String getFileExtension(String content) {
        String extension = "";

        if (content.contains("jpeg")) {
            extension = "jpg";
        } else if (content.contains("gif")) {
            extension = "gif";
        } else if (content.contains("tif")) {
            extension = "tif";
        } else if (content.contains("png")) {
            extension = "png";
        } else if (content.contains("bmp")) {
            extension = "bmp";
        }

        return extension;
    }

    public static String toDateStr(Object date, String format) {
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        String strDate = null;

        try {
            strDate = formatter.format(date);
        } catch (Exception ex) {

        }

        return strDate;
    }

    public static Date getTodayDate() {
        Date todayDate = new Date();
        return todayDate;
    }

    public static String toDateStrMYSQL(String strDate, String format) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String date = null;

        try {
            date = formatter.format(toDate(strDate, format));
        } catch (Exception ex) {
        }

        return date;
    }

    public static String addDateTo(String date, int ttlDay) {
        String output = null;
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();

        try {
            c.setTime(toDate(date, "dd/MM/yyyy")); // Now use today date.
            c.add(Calendar.DATE, ttlDay);
            output = formatter.format(c.getTime());
        } catch (Exception ex) {
        }

        return output;
    }

    public static Date addDateTo(Date date, int ttlDay) {
        //SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        Date tmp = null;

        try {
            //c.setTime(toDate(date, "yyyy-MM-dd")); // Now use today date.
            c.setTime(date);
            c.add(Calendar.DATE, ttlDay);
            tmp = c.getTime();
        } catch (Exception ex) {
        }

        return tmp;
    }

    public static String isNull(String strValue, String value) {
        if (strValue == null) {
            return value;
        } else if (strValue.isEmpty() || strValue.equals("")) {
            return value;
        } else {
            return strValue;
        }
    }

    public static Date getLastDayOfMonth(String strDate, String format) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(toDate(strDate, format));

        calendar.add(Calendar.MONTH, 1);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.add(Calendar.DATE, -1);

        Date lastDayOfMonth = calendar.getTime();
        return lastDayOfMonth;
    }

    public static int getDatePart(Date d, String format) {
        int intValue = 0;

        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            String value = sdf.format(d);

            if (!value.isEmpty()) {
                intValue = Integer.parseInt(value);
            }
        } catch (Exception ex) {

        }

        return intValue;
    }

    public static int isNullZero(Integer value) {
        if (value == null) {
            return 0;
        } else {
            return value;
        }
    }

    public static double isNullZero(Double value) {
        if (value == null) {
            return 0.0;
        } else {
            return value;
        }
    }

    public static int isNullZero(String value) {
        if (value == null || value.isEmpty()) {
            return 0;
        } else {
            return Integer.parseInt(value);
        }
    }

    public static double nullZero(String value) {
        if (value == null) {
            return 0;
        }

        if (value.isEmpty()) {
            return 0;
        }

        return Double.parseDouble(value);
    }

    public static String getPeriod(String strDate, String format) {
        SimpleDateFormat formatter = new SimpleDateFormat("MMyyyy");
        String strPeriod = null;
        Date date = toDate(strDate, format);

        if (date != null) {
            strPeriod = formatter.format(date);
        }

        return strPeriod;
    }

    public static String getTodayDateStr(String format) {
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        return formatter.format(Calendar.getInstance().getTime());
    }

    public static String getComputerName() {
        String computerName = "";

        try {
            computerName = InetAddress.getLocalHost().getHostName();
        } catch (Exception e) {
        }

        return computerName;
    }

    public static String getVouNo(int machineId, String period, int serial,
            int vouTotalDigit, int machineTtlDigit) {
        String strMachineId = String.format("%0" + machineTtlDigit + "d", machineId);
        period = period.substring(0, 2) + "-" + period.substring(2, 6);
        String vouSerial = String.format("%0" + vouTotalDigit + "d", serial);
        return strMachineId + vouSerial + "-" + period;
    }

    public static Float NZeroFloat(Object number) {
        Float value = 0f;

        try {
            value = Float.parseFloat(number.toString());
        } catch (Exception ex) {
        }

        return value;
    }

    public static Double NZeroDouble(Object number) {
        Double value = 0.0;

        try {
            value = Double.parseDouble(number.toString());
        } catch (Exception ex) {

        }

        return value;
    }

    public static Date toDateTime(Object objDate, String format) {
        SimpleDateFormat formatter = new SimpleDateFormat(format + " HH:mm:ss");
        Date date = new Date();
        String strDate = objDate.toString() + " " + date.getHours() + ":"
                + date.getMinutes() + ":" + date.getSeconds();

        date = null;

        try {
            date = formatter.parse(strDate);
        } catch (Exception ex) {
        }

        return date;
    }

    public static boolean isSameDate(Date d1, Date d2) {
        if (d1 == null || d2 == null) {
            return false;
        }

        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        return sdf.format(d1).equals(sdf.format(d2));
    }

    public static Date getTodayDateTime() {
        return new Date();
    }

    public static Double getDouble(String number) {
        double value = 0.0;
        if (!number.isEmpty()) {
            value = Double.valueOf(number);
        }
        return value;
    }

    public static Integer getInteger(String number) {
        int value = 0;
        if (!number.isEmpty()) {
            value = Integer.parseInt(number);
        }
        return value;
    }

    public static boolean getNullTo(Boolean value) {
        if (value == null) {
            return false;
        } else {
            return value;
        }
    }

    public static String subDateTo(Date date, int day, String format) {
        String output = null;
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        Calendar c = Calendar.getInstance();

        try {
            c.setTime(date); // Now use today date.
            c.add(Calendar.DATE, day);
            output = formatter.format(c.getTime());
        } catch (Exception ex) {
            //System.out.println("DateUtil.addTodayDateTo : " + ex.toString());
        }

        return output;
    }

    public static Integer getNumber(String strText) {
        int tmpInt = 1;
        if (!strText.isEmpty()) {
            tmpInt = Integer.parseInt(strText);
        }
        return tmpInt;
    }

    public static boolean isNumber(String number) {
        boolean status = false;

        try {
            if (number != null && !number.isEmpty()) {
                double tmp = Double.parseDouble(number);
                status = true;
            } else {
                status = true;
            }
        } catch (Exception ex) {
            System.out.println("NumberUtil.isNumber : " + ex.getMessage());
        }
        return status;
    }

    public static String getString(String str1, String str2) {
        if (str1 == null) {
            return str2;
        } else if (str1.trim().isEmpty()) {
            return str2;
        } else {
            return str1;
        }
    }

    public static boolean isNull(String value) {
        return !(value.isEmpty());
    }

    public static String getYear() {
        int year = Calendar.getInstance().get(Calendar.YEAR);
        return String.valueOf(year);
    }

    public static String getMonth() {
        Date date = new Date();
        LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        int month = localDate.getMonthValue();
        String mon;
        // int month = Calendar.getInstance().get(Calendar.MONTH);
        if (month < 10) {
            mon = "0" + month;
        } else {
            mon = String.valueOf(month);
        }
        return mon;
    }

    public static String toDateFormat(Date date, String format) {
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        String strDate = null;

        try {
            strDate = formatter.format(date);
        } catch (Exception ex) {
            LOGGER.info("toDateStr : " + ex.getMessage());
        }

        return strDate;
    }

    public static String toDateFormat(String isoDate) {
        final String OLD_FORMAT = "2012-12-19T06:01:17.171Z";
        final String NEW_FORMAT = "yyyy-MM-dd HH:mm:ss";
        String newDateString;

        SimpleDateFormat sdf = new SimpleDateFormat(OLD_FORMAT);
        Date d = null;
        try {
            d = sdf.parse(isoDate);
        } catch (ParseException ex) {
            LOGGER.error("toDateFormat :" + ex.getMessage());
        }
        sdf.applyPattern(NEW_FORMAT);
        newDateString = sdf.format(d);
        return newDateString;
    }

    public static String toISODate(Date date) {
        SimpleDateFormat sdf;
        sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
        sdf.setTimeZone(TimeZone.getTimeZone("CET"));
        String isoDate = sdf.format(date);
        return isoDate;
    }

    public static void setLookAndFeel(Component com) {
        try {
            UIManager.setLookAndFeel(Global.setting.getTheme().getPath());
            SwingUtilities.updateComponentTreeUI(com);
        } catch (UnsupportedLookAndFeelException e) {
            LOGGER.error("Look and Feel :" + e.getMessage());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException ex) {
            LOGGER.error("Look And Feel :" + ex.getMessage());
        }
    }
}
