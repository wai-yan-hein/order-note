/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wg.os.dal;

import com.wg.os.document.Order;
import com.wg.os.util.Util1;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Lenovo
 */
@Repository
public class OrderDalImpl implements OrderDal {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public List<Order> findAll() {
        return mongoTemplate.findAll(Order.class);
    }

    @Override
    public Order findById(String userId) {
        Query query = new Query();
        query.addCriteria(Criteria.where("cityId").is(userId));
        return mongoTemplate.findOne(query, Order.class);
    }

    @Override
    public Order save(Order ot) {
        return mongoTemplate.save(ot);
    }

    @Override
    public Order delete(String id) {
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(id));
        return mongoTemplate.findAndModify(query, Update.update("deleted", true), Order.class);
    }

    @Override
    public List<Order> search(Date startDate, Date endDate, String cusCode, String cusCity, String payment) {
        Query query = new Query();
        if (!cusCode.equals("-") && !cusCity.equals("-") && !payment.equals("-")) {
            query.addCriteria(
                    Criteria.where("customer.cusCity").is(cusCity)
                            .and("customer._id").is(cusCode)
                            .and("payment.payment")
                            .andOperator(
                                    Criteria.where("orderDate").lte(endDate),
                                    Criteria.where("orderDate").gte(startDate)
                            )
            );
        } else {
            if (!cusCode.equals("-")) {
                query.addCriteria(
                        Criteria.where("customer._id").is(cusCode)
                                .andOperator(
                                        Criteria.where("orderDate").lte(endDate),
                                        Criteria.where("orderDate").gte(startDate)
                                )
                );
            } else if (!cusCity.equals("-")) {
                query.addCriteria(
                        Criteria.where("customer.cusCity._id").is(cusCity)
                                .andOperator(
                                        Criteria.where("orderDate").lte(endDate),
                                        Criteria.where("orderDate").gte(startDate)
                                )
                );
            } else if (!payment.equals("-")) {
                query.addCriteria(
                        Criteria.where("payment._id").is(payment)
                                .andOperator(
                                        Criteria.where("orderDate").lte(endDate),
                                        Criteria.where("orderDate").gte(startDate)
                                )
                );
            } else {
                query.addCriteria(Criteria.where("orderDate")
                        .gt(startDate)
                        .lt(endDate));
            }
        }

        return mongoTemplate.find(query, Order.class);
    }

    @Override
    public List<Order> search(String cusCode) {
        Query query = new Query();
        query.addCriteria(Criteria.where("customer._id").is(cusCode));
        List<Order> list = mongoTemplate.find(query, Order.class);

        return list;
    }

}
