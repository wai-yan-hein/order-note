/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wg.os.service;

import com.mongodb.client.result.DeleteResult;
import com.wg.os.dal.PaymentDal;
import com.wg.os.document.Payment;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Lenovo
 */
@Service
@Transactional
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    private PaymentDal dal;

    @Override
    public List<Payment> findAll() {
        return dal.findAll();
    }

    @Override
    public Payment findById(String userId) {
        return dal.findById(userId);
    }

    @Override
    public Payment save(Payment student) {
        return dal.save(student);
    }

    @Override
    public Payment update(Payment student) {
        return dal.update(student);
    }

    @Override
    public List find() {
        return dal.find();
    }

    @Override
    public DeleteResult remove(String name) {
        return dal.remove(name);
    }

}
