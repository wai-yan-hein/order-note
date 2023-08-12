/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wg.os.ui.common;

import com.wg.os.document.City;
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
public class CityTabelModel extends AbstractTableModel {

    private static final Logger LOGGER = LoggerFactory.getLogger(CityTabelModel.class);
    private List<City> listAmt = new ArrayList();
    private String[] columnNames = {"City"};

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
            City user = listAmt.get(row);

            switch (column) {
                case 0: //Name
                    return user.getCityName();
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
        if (listAmt == null) {
            return 0;
        }
        return listAmt.size();
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

    public List<City> getlistCustomer() {
        return listAmt;
    }

    public void setlistCustomer(List<City> listAmt) {
        this.listAmt = listAmt;
        fireTableDataChanged();

    }

    public City getCity(int row) {
        return listAmt.get(row);
    }

    public void deleteCity(int row) {
        if (!listAmt.isEmpty()) {
            listAmt.remove(row);
            fireTableRowsDeleted(0, listAmt.size());
        }

    }

    public void addCity(City user) {
        listAmt.add(user);
        fireTableRowsInserted(listAmt.size() - 1, listAmt.size() - 1);
    }

    public void setCity(int row, City user) {
        if (!listAmt.isEmpty()) {
            listAmt.set(row, user);
            fireTableRowsUpdated(row, row);
        }
    }

}
