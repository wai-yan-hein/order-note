/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wg.os.ui.common;

import com.wg.os.document.ItemSize;
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
public class SizeTabelModel extends AbstractTableModel {

    private static final Logger LOGGER = LoggerFactory.getLogger(SizeTabelModel.class);
    private List<ItemSize> listSize = new ArrayList();
    private String[] columnNames = {"Size Name"};

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
        if (listSize == null) {
            return null;
        }

        if (listSize.isEmpty()) {
            return null;
        }

        try {
            ItemSize size = listSize.get(row);

            switch (column) {

                case 0: //Name
                    return size.getItemSize();
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
        if (listSize == null) {
            return 0;
        }
        return listSize.size();
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

    public List<ItemSize> getListSize() {
        return listSize;
    }

    public void setListSize(List<ItemSize> listSize) {
        this.listSize = listSize;
        fireTableDataChanged();

    }

    public ItemSize getSize(int row) {
        return listSize.get(row);
    }

    public void deleteSize(int row) {
        if (!listSize.isEmpty()) {
            listSize.remove(row);
            fireTableRowsDeleted(row, listSize.size());
        }

    }

    public void addSize(ItemSize Size) {
        listSize.add(Size);
        fireTableRowsInserted(listSize.size() - 1, listSize.size() - 1);
    }

    public void setSize(int row, ItemSize Size) {
        if (listSize == null) {
            if (!listSize.isEmpty()) {
                listSize.set(row, Size);
                fireTableRowsUpdated(row, row);
            }
        }
    }

}
