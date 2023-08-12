/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wg.os.ui.common;

import com.wg.os.document.Gender;
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
public class GenderTabelModel extends AbstractTableModel {

    private static final Logger LOGGER = LoggerFactory.getLogger(GenderTabelModel.class);
    private List<Gender> listGender = new ArrayList();
    private String[] columnNames = {"Gender"};

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
            Gender gender = listGender.get(row);

            switch (column) {
                case 0: //Name
                    return gender.getCusGender();
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
        if (listGender == null) {
            return 0;
        }
        return listGender.size();
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

    public List<Gender> getListGender() {
        return listGender;
    }

    public void setListGender(List<Gender> listGender) {
        this.listGender = listGender;
        fireTableDataChanged();
    }

   

    public Gender getGender(int row) {
        return listGender.get(row);
    }

    public void deleteGender(int row) {
        if (!listGender.isEmpty()) {
            listGender.remove(row);
            fireTableRowsDeleted(0, listGender.size());
        }

    }

    public void addGender(Gender gender) {
        listGender.add(gender);
        fireTableRowsInserted(listGender.size() - 1, listGender.size() - 1);
    }

    public void setGender(int row, Gender gender) {
        if (listGender == null) {
            if (!listGender.isEmpty()) {
                listGender.set(row, gender);
                fireTableRowsUpdated(row, row);
            }
        }
    }
}
