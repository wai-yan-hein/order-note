/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wg.os.ui.common;

import com.wg.os.document.Expense;
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
public class ExpenseTabelModel extends AbstractTableModel {

    private static final Logger LOGGER = LoggerFactory.getLogger(ExpenseTabelModel.class);
    private List<Expense> listExp = new ArrayList();
    private String[] columnNames = {"Expense"};

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
            Expense exp = listExp.get(row);

            switch (column) {
                case 0: //Name
                    return exp.getExpDesp();
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
        if (listExp == null) {
            return 0;
        }
        return listExp.size();
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

    public List<Expense> getListExp() {
        return listExp;
    }

    public void setListExp(List<Expense> listExp) {
        this.listExp = listExp;
        fireTableDataChanged();
    }

    public Expense getExpense(int row) {
        return listExp.get(row);
    }

    public void deleteExpense(int row) {
        if (!listExp.isEmpty()) {
            listExp.remove(row);
            fireTableRowsDeleted(0, listExp.size());
        }

    }

    public void addExpense(Expense exp) {
        listExp.add(exp);
        fireTableRowsInserted(listExp.size() - 1, listExp.size() - 1);
    }

    public void setExpense(int row, Expense exp) {
        if (listExp == null) {
            if (!listExp.isEmpty()) {
                listExp.set(row, exp);
                fireTableRowsUpdated(row, row);
            }
        }
    }
}
