/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wg.os.service;

import com.wg.os.document.OrderDetail;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.wg.os.dal.OrderDetailDal;

/**
 *
 * @author Lenovo
 */
@Service
@Transactional
public class OrderDetailServiceImpl implements OrderDetailService {

    @Autowired
    private OrderDetailDal dal;

    @Override
    public List<OrderDetail> findAll() {
        return dal.findAll();
    }

    @Override
    public OrderDetail findById(String userId) {
        return dal.findById(userId);
    }

    @Override
    public OrderDetail save(OrderDetail student) {
        return dal.save(student);
    }

    @Override
    public OrderDetail update(OrderDetail student) {
        return dal.update(student);
    }

    @Override
    public List<OrderDetail> search(String cusId, Date date) {
        return dal.search(cusId, date);
    }

}
