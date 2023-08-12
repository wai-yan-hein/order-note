/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wg.os.ui.common;

import com.wg.os.document.ExpNote;
import com.wg.os.util.Util1;
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
public class ExpenseNoteTabelModel extends AbstractTableModel {

    private static final Logger LOGGER = LoggerFactory.getLogger(ExpenseNoteTabelModel.class);
    private List<ExpNote> listExp = new ArrayList();
    private String[] columnNames = {"Date", "Expense", "Amount"};

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
                return String.class;
            case 2:
                return Double.class;
            default:
                return Object.class;
        }
    }

    @Override
    public Object getValueAt(int row, int column) {

        try {
            ExpNote exp = listExp.get(row);

            switch (column) {
                case 0: //Name
                    return Util1.toDateFormat(exp.getExpDate(), "dd-MM-yyyy");
                case 1:
                    return exp.getExp().getExpDesp();
                case 2:
                    return exp.getAmt();
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

    public List<ExpNote> getListExp() {
        return listExp;
    }

    public void setListExp(List<ExpNote> listExp) {
        this.listExp = listExp;
        fireTableDataChanged();
    }

    public ExpNote getExpense(int row) {
        return listExp.get(row);
    }

    public void deleteExpense(int row) {
        if (!listExp.isEmpty()) {
            listExp.remove(row);
            fireTableRowsDeleted(0, listExp.size());
        }

    }

    public void addExpense(ExpNote exp) {
        listExp.add(exp);
        fireTableRowsInserted(listExp.size() - 1, listExp.size() - 1);
    }

    public void setExpense(int row, ExpNote exp) {
        if (!listExp.isEmpty()) {
            listExp.set(row, exp);
            fireTableRowsUpdated(row, row);
        }
    }

    public void clear() {
        if (!listExp.isEmpty()) {
            listExp.clear();
            fireTableDataChanged();
        }
    }
}
