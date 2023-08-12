/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wg.os.ui.common;

import com.wg.os.document.ItemColor;
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
public class ColorTabelModel extends AbstractTableModel {

    private static final Logger LOGGER = LoggerFactory.getLogger(ColorTabelModel.class);
    private List<ItemColor> listColor = new ArrayList();
    private String[] columnNames = {"Color Name"};

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
        if (listColor == null) {
            return null;
        }

        if (listColor.isEmpty()) {
            return null;
        }

        try {
            ItemColor color = listColor.get(row);

            switch (column) {

                case 0: //Name
                    return color.getColorName();
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
        if (listColor == null) {
            return 0;
        }
        return listColor.size();
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

    public List<ItemColor> getListColor() {
        return listColor;
    }

    public void setListColor(List<ItemColor> listColor) {
        this.listColor = listColor;
        fireTableDataChanged();

    }

    public ItemColor getColor(int row) {
        return listColor.get(row);
    }

    public void deleteColor(int row) {
        if (!listColor.isEmpty()) {
            listColor.remove(row);
            fireTableRowsDeleted(0, listColor.size());
        }
    }

    public void addColor(ItemColor color) {
        listColor.add(color);
        fireTableRowsInserted(listColor.size() - 1, listColor.size() - 1);
    }

    public void setColor(int row, ItemColor color) {
        if (listColor == null) {
            if (!listColor.isEmpty()) {
                listColor.set(row, color);
                fireTableRowsUpdated(row, row);
            }
        }
    }
}
