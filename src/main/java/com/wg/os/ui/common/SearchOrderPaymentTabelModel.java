/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wg.os.ui.common;

import com.wg.os.document.Order;
import com.wg.os.document.PaymentDetail;
import com.wg.os.service.OrderService;
import com.wg.os.util.Util1;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author winswe
 */
@Component
public class SearchOrderPaymentTabelModel extends AbstractTableModel {

    private static final Logger LOGGER = LoggerFactory.getLogger(SearchOrderPaymentTabelModel.class);
    private List<Order> listOrder = new ArrayList();
    private String[] columnNames = {"Date", "Order Id", "Customer", "Payment",
        "Order Total", "Pay Amt", "Balance", "Remark"};
    @Autowired
    private OrderService orderService;

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }

    @Override
    public boolean isCellEditable(int row, int column) {
        return column == 7;
    }

    @Override
    public Class getColumnClass(int column) {
        switch (column) {
            case 0:
                return String.class;
            case 1:
                return String.class;
            case 2:
                return String.class;
            case 3:
                return String.class;
            case 4:
                return Double.class;
            case 5:
                return Double.class;
            case 6:
                return Double.class;
            case 7:
                return String.class;

            default:
                return Object.class;
        }

    }

    @Override
    public Object getValueAt(int row, int column) {
        try {
            Order order = listOrder.get(row);
            List<PaymentDetail> listPay = order.getListPaymentDetail();
            double ttlPayAmt = 0.0;
            if (listPay != null) {
                for (PaymentDetail pd : listPay) {
                    ttlPayAmt += pd.getPayAmt();
                }
            }

            switch (column) {
                case 0: //Date
                    String date = Util1.toDateFormat(order.getOrderDate(), "dd-MM-yyyy");
                    return date;
                case 1: //Order Id
                    if (order.getDeleted()) {
                        return order.getOrderId().concat("***");
                    } else {
                        return order.getOrderId();

                    }
                case 2://Custermer Name
                    return order.getCustomer().getCusName();
                case 3:
                    return order.getPayment().getPayment();
                case 4:
                    return order.getOrderTotalAmt();
                case 5:
                    if (order.getPayment().getPayment().equals("Cash")) {
                        return order.getPaidAmount();
                    } else {
                        return ttlPayAmt;
                    }
                case 6:
                    if (order.getPayment().getPayment().equals("Cash")) {
                        return order.getOrderTotalAmt() - order.getPaidAmount();
                    } else {
                        return order.getOrderTotalAmt() - ttlPayAmt;
                    }
                case 7:
                    return order.getRemark();
                default:
                    return new Object();
            }
        } catch (Exception ex) {
            LOGGER.error("getValueAt : " + ex.getStackTrace()[0].getLineNumber() + " - " + ex.getMessage());
        }

        return null;
    }

    @Override
    public void setValueAt(Object value, int row, int column) {
        Order order = getOrder(row);
        if (column == 7) {
            order.setRemark(value.toString());
            orderService.save(order);
            setOrder(row, order);
        }
    }

    @Override
    public int getRowCount() {
        if (listOrder == null) {
            return 0;
        }
        return listOrder.size();
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

    public List<Order> getlistOrder() {
        return listOrder;
    }

    public void setlistOrder(List<Order> listOrder) {

        this.listOrder = listOrder;
        fireTableDataChanged();

    }

    public Order getOrder(int row) {
        return listOrder.get(row);
    }

    public void deleteOrder(int row) {
        if (!listOrder.isEmpty()) {
            listOrder.remove(row);
            fireTableRowsDeleted(0, listOrder.size());
        }
    }

    public void addOrder(Order order) {
        listOrder.add(order);
        fireTableRowsInserted(listOrder.size() - 1, listOrder.size() - 1);
    }

    public void setOrder(int row, Order order) {
        if (!listOrder.isEmpty()) {
            listOrder.set(row, order);
            fireTableRowsUpdated(row, row);
        }
    }

    public void clear() {
        listOrder.clear();
        fireTableDataChanged();
    }
}
