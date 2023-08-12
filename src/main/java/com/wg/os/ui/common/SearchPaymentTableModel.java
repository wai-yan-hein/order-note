/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wg.os.ui.common;

import com.wg.os.document.PaymentDetail;
import com.wg.os.util.Util1;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 *
 * @author winswe
 */
@Component
public class SearchPaymentTableModel extends AbstractTableModel {

    private static final Logger LOGGER = LoggerFactory.getLogger(SearchPaymentTableModel.class);
    private List<PaymentDetail> listPaymentDetail = new ArrayList<>();
    private String[] columnNames = {"Payment Date", "Payment Amount"};

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }

    @Override
    public boolean isCellEditable(int row, int column) {
        return false;
    }

    @Override
    public Class getColumnClass(int column) {
        switch (column) {
            case 0:
                return String.class;
            case 1:
                return Double.class;
            default:
                return Object.class;
        }
    }

    @Override
    public Object getValueAt(int row, int column) {

        try {
            PaymentDetail payment = listPaymentDetail.get(row);

            switch (column) {
                case 0: //Name

                    return Util1.toDateFormat(payment.getPayDate(), "yyyy-MM-dd");
                case 1:
                    return payment.getPayAmt();
                default:
                    return null;
            }
        } catch (Exception ex) {
            LOGGER.error("getValueAt : " + ex.getStackTrace()[0].getLineNumber() + " - " + ex.getMessage());
        }

        return null;
    }

    @Override
    public void setValueAt(Object value, int row, int column) {

    }

    @Override
    public int getRowCount() {
        if (listPaymentDetail == null) {
            return 0;
        }
        return listPaymentDetail.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    public String[] getColumnNames() {
        return columnNames;
    }

    public void setColumnNames(String[] columnNames) {
        this.columnNames = columnNames;
    }

    public List<PaymentDetail> getListPaymentDetail() {
        return listPaymentDetail;
    }

    public void setListPaymentDetail(List<PaymentDetail> listPaymentDetail) {
        this.listPaymentDetail = listPaymentDetail;
        fireTableDataChanged();
    }

    public PaymentDetail getPaymentDetail(int row) {
        return listPaymentDetail.get(row);
    }

    public void deletePaymentDetail(int row) {
        if (!listPaymentDetail.isEmpty()) {
            listPaymentDetail.remove(row);
            fireTableRowsDeleted(0, listPaymentDetail.size());
        }

    }

    public void addPaymentDetail(PaymentDetail payment) {
        if (listPaymentDetail == null) {
            listPaymentDetail = new ArrayList<>();
        }
        listPaymentDetail.add(payment);
        fireTableRowsInserted(listPaymentDetail.size() - 1, listPaymentDetail.size() - 1);
    }

    public void setPaymentDetail(int row, PaymentDetail payment) {
        if (listPaymentDetail == null) {
            if (!listPaymentDetail.isEmpty()) {
                listPaymentDetail.set(row, payment);
                fireTableRowsUpdated(row, row);
            }
        }
    }

    public void clear() {
        if (!listPaymentDetail.isEmpty()) {
            listPaymentDetail = new ArrayList<>();
            fireTableDataChanged();
        }
    }
}
