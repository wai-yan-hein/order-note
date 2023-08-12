/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wg.os.ui.common;

import com.wg.os.document.Payment;
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
public class PaymentTabelModel extends AbstractTableModel {

    private static final Logger LOGGER = LoggerFactory.getLogger(PaymentTabelModel.class);
    private List<Payment> listPayment = new ArrayList();
    private String[] columnNames = {"Payment"};

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
        return String.class;
    }

    @Override
    public Object getValueAt(int row, int column) {

        try {
            Payment payment = listPayment.get(row);

            switch (column) {
                case 0: //Name
                    return payment.getPayment();
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
        if (listPayment == null) {
            return 0;
        }
        return listPayment.size();
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

    public List<Payment> getlistCustomer() {
        return listPayment;
    }

    public void setlistCustomer(List<Payment> listPayment) {
        this.listPayment = listPayment;
        fireTableDataChanged();

    }

    public Payment getPayment(int row) {
        return listPayment.get(row);
    }

    public void deletePayment(int row) {
        if (!listPayment.isEmpty()) {
            listPayment.remove(row);
            fireTableRowsDeleted(0, listPayment.size());
        }

    }

    public void addPayment(Payment payment) {
        listPayment.add(payment);
        fireTableRowsInserted(listPayment.size() - 1, listPayment.size() - 1);
    }

    public void setPayment(int row, Payment payment) {
        if (listPayment == null) {
            if (!listPayment.isEmpty()) {
                listPayment.set(row, payment);
                fireTableRowsUpdated(row, row);
            }
        }
    }
}
