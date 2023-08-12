/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wg.os.ui.common;

import com.wg.os.document.Customer;
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
public class CustomerTabelModel extends AbstractTableModel {

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomerTabelModel.class);
    private List<Customer> listCustomer = new ArrayList();
    private String[] columnNames = {"Code", "Customer Name", "City"};

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
            Customer cus = listCustomer.get(row);

            switch (column) {
                case 0: //Name
                    return cus.getCusId();
                case 1:
                    return cus.getCusName();
                case 2:
                    return cus.getCusCity();
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
        if (listCustomer == null) {
            return 0;
        }
        return listCustomer.size();
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

    public List<Customer> getlistCustomer() {
        return listCustomer;
    }

    public void setlistCustomer(List<Customer> listCustomer) {
        this.listCustomer = listCustomer;
        fireTableDataChanged();

    }

    public Customer getCustomer(int row) {
        return listCustomer.get(row);
    }

    public void deleteCustomer(int row) {
        if (!listCustomer.isEmpty()) {
            listCustomer.remove(row);
            fireTableRowsDeleted(0, listCustomer.size());
        }

    }

    public void addCustomer(Customer cus) {
        listCustomer.add(cus);
        fireTableRowsInserted(listCustomer.size() - 1, listCustomer.size() - 1);
    }

    public void setCustomer(int row, Customer cus) {
        if (listCustomer == null) {
            if (!listCustomer.isEmpty()) {
                listCustomer.set(row, cus);
                fireTableRowsUpdated(row, row);
            }
        }
    }
}
