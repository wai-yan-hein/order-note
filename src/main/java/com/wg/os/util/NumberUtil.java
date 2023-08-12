/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wg.os.util;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.NumberFormatter;

/**
 *
 * @author WSwe
 */
public class NumberUtil {

    public static Long NZeroL(Object number) {
        try {
            if (number == null) {
                return new Long(0);
            } else {
                return Long.parseLong(number.toString());
            }
        } catch (Exception ex) {
            System.out.println("NumberUtil.NZero : " + ex.getMessage());
            return new Long(0);
        }
    }

    public static Double NZero(Object number) {
        try {
            if (number == null) {
                return new Double(0);
            } else {
                return Double.parseDouble(number.toString().replace(",", ""));
            }
        } catch (Exception ex) {
            System.out.println("NumberUtil.NZero : " + ex.getMessage());
            return new Double(0);
        }
    }

    public static boolean isNumber(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
