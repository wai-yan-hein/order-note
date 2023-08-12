/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wg.os.dal;

import com.wg.os.document.OrderDetail;
import java.util.Date;
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
public class OrderDetailDalImpl implements OrderDetailDal {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public List<OrderDetail> findAll() {
        return mongoTemplate.findAll(OrderDetail.class);
    }

    @Override
    public OrderDetail findById(String userId) {
        Query query = new Query();
        query.addCriteria(Criteria.where("cityId").is(userId));
        return mongoTemplate.findOne(query, OrderDetail.class);
    }

    @Override
    public OrderDetail save(OrderDetail ot) {
        return mongoTemplate.save(ot);
    }

    @Override
    public OrderDetail update(OrderDetail ot) {
        return null;
    }

    @Override
    public List<OrderDetail> search(String cusId, Date date) {
        Query query = new Query();
        if (!cusId.equals("-")) {
            query.addCriteria(Criteria.where("cusId").is(cusId));
        }
        if (date != null) {
            query.addCriteria(Criteria.where("orderDate").is(date));
        }
        return mongoTemplate.find(query, OrderDetail.class);
    }

}
