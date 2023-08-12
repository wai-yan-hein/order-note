/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wg.os.ui.common;

import com.wg.os.document.Order;
import com.wg.os.document.OrderDetail;
import com.wg.os.document.PaymentDetail;
import com.wg.os.service.OrderService;
import com.wg.os.util.Util1;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.swing.JTable;
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
public class OrderCheckTabelModel extends AbstractTableModel {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderCheckTabelModel.class);
    private List<Order> listOrder = new ArrayList();
    private String[] columnNames = {"Date", "Order Id", "Customer", "Gender",
        "City", "Payment", "Purchase Price", "Order Price", "Discount%", "Paid Amt", "Profit"};
    JTable table;

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
                return String.class;//Date
            case 1:
                return String.class;//Order id
            case 2:
                return String.class;//Customer
            case 3:
                return String.class;//Gender
            case 4:
                return String.class;//City
            case 5:
                return String.class;//Payment
            case 6:
                return Double.class;//Normal Price
            case 7:
                return Double.class;//Order Price
            case 8:
                return Double.class;//Discount
            case 9:
                return Double.class;//Paid amt
            case 10:
                return Double.class;//profit
            default:
                return Object.class;
        }

    }

    @Override
    public Object getValueAt(int row, int column) {
        try {
            Order order = listOrder.get(row);
            List<OrderDetail> listDorder = order.getListOrderDetail();
            List<PaymentDetail> listPay = order.getListPaymentDetail();
            String payment = order.getPayment().getPayment();
            double ttlNPrice = 0.0;
            double ttlPayAmt = 0.0;

            for (OrderDetail od : listDorder) {
                ttlNPrice += Util1.isNullZero(od.getItemPurPrice());
            }
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
                    return order.getOrderId();
                case 2://Custermer Name
                    return order.getCustomer().getCusName();
                case 3:
                    return order.getCustomer().getCusGender();
                case 4:
                    return order.getCustomer().getCusCity();
                case 5:
                    return order.getPayment().getPayment();
                case 6:
                    return ttlNPrice;
                case 7:
                    return order.getOrderTotalAmt();
                case 8:
                    return order.getDiscount();
                case 9:
                    if (payment.equals("Cash")) {
                        return Util1.isNullZero(order.getPaidAmount());
                    } else {
                        return ttlPayAmt;
                    }
                case 10:
                    if (payment.equals("Cash")) {
                        double amt = order.getPaidAmount() - ttlNPrice;
                        return amt;
                    } else {
                        return ttlPayAmt - ttlNPrice;
                    }
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
        listOrder.removeIf(order -> order.getDeleted().equals(Boolean.TRUE));
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

    public void refresh() {
        fireTableDataChanged();
    }

    public void clear() {
        listOrder.clear();
        fireTableDataChanged();
    }

    public JTable getTable() {
        return table;
    }

    public void setTable(JTable table) {
        this.table = table;
    }

}
