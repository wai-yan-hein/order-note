/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wg.os.dal;

import com.mongodb.client.result.DeleteResult;
import com.wg.os.document.Payment;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Lenovo
 */
@Repository
public class PaymentDalImpl implements PaymentDal {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public List<Payment> findAll() {
        return mongoTemplate.findAll(Payment.class);
    }

    @Override
    public Payment findById(String userId) {
        Query query = new Query();
        query.addCriteria(Criteria.where("cityId").is(userId));
        return mongoTemplate.findOne(query, Payment.class);
    }

    @Override
    public Payment save(Payment payment) {
        return mongoTemplate.save(payment);
    }

    @Override
    public Payment update(Payment payment) {
        return null;
    }

    @Override
    public List find() {
        return null;
    }

    @Override
    public DeleteResult remove(String name) {
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(name));
        return mongoTemplate.remove(query, Payment.class);

    }

}
