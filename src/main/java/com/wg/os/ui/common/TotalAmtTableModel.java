/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wg.os.ui.common;

import com.wg.os.document.TotalAmount;
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
public class TotalAmtTableModel extends AbstractTableModel {

    private static final Logger LOGGER = LoggerFactory.getLogger(TotalAmtTableModel.class);
    private List<TotalAmount> listAmt = new ArrayList();
    private String[] columnNames = {"Name", "Total Amount"};

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
            TotalAmount ttlAmt = listAmt.get(row);

            switch (column) {
                case 0: //Name
                    return ttlAmt.getName();
                case 1:
                    return ttlAmt.getTtlAmt();
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

    public List<TotalAmount> getListAmt() {
        return listAmt;
    }

    public void setListAmt(List<TotalAmount> listAmt) {
        this.listAmt = listAmt;
        fireTableDataChanged();
    }

    public void addTotalAmt(TotalAmount ttlAmt) {
        listAmt.add(ttlAmt);
        fireTableRowsInserted(listAmt.size() - 1, listAmt.size() - 1);
    }

    public void clear() {
        listAmt.clear();
        fireTableDataChanged();
    }

}
