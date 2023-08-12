/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wg.os.service;

import com.wg.os.dal.CustomerDal;
import com.wg.os.dal.GenIdDal;
import com.wg.os.document.Order;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.wg.os.dal.OrderDal;
import com.wg.os.document.GenId;
import com.wg.os.util.Util1;

/**
 *
 * @author Lenovo
 */
@Service
@Transactional
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderDal dal;
    @Autowired
    private GenIdDal genIdDal;
    @Autowired
    private CustomerDal customerDal;
    private GenId genId;
    private int lastNo;

    @Override
    public List<Order> findAll() {
        return dal.findAll();
    }

    @Override
    public Order findById(String userId) {
        return dal.findById(userId);
    }

    @Override
    public Order save(Order order) {
        if (order.getOrderId().isEmpty()) {
            order.setOrderId(genOrderId());
            genId.setGenId(lastNo + 1);
            genIdDal.save(genId);
        }
        if (order.getDeleted() == null) {
            order.setDeleted(Boolean.FALSE);
        }
        customerDal.save(order.getCustomer());
        return dal.save(order);
    }

    @Override
    public Order delete(String id) {
        return dal.delete(id);
    }

    @Override
    public List<Order> search(Date startDate, Date endDate, String cusCode, String city, String payment) {
        return dal.search(startDate, endDate, cusCode, city, payment);
    }

    @Override
    public List<Order> search(String cusCode) {
        return dal.search(cusCode);
    }

    private String genOrderId() {
        int needToAdd;
        String orderCode;
        String month = Util1.getMonth();
        String year = Util1.getYear();
        genId = genIdDal.findById("ORDER");
        if (genId != null) {
            lastNo = genId.getGenId();
        } else {
            genId = new GenId();
            genId.setGenType("ORDER");
            genId.setGenId(1);
            lastNo = 1;
        }
        String orderId = String.valueOf(lastNo);
        needToAdd = 5 - orderId.length();
        for (int i = 0; i < needToAdd; i++) {
            orderId = "0" + orderId;
        }
        orderCode = orderId + "-" + month + "-" + year;
        return orderCode;

    }
}
