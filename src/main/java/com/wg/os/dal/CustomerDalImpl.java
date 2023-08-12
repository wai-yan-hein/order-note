/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wg.os.dal;

import com.wg.os.document.Customer;
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
public class CustomerDalImpl implements CustomerDal {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public List<Customer> findAll() {
        return mongoTemplate.findAll(Customer.class);
    }

    @Override
    public Customer findById(String code) {
        Query query = new Query();
        query.addCriteria(Criteria.where("cusId").is(code));
        return mongoTemplate.findOne(query, Customer.class);
    }

    @Override
    public Customer save(Customer customer) {
        return mongoTemplate.save(customer);
    }

    @Override
    public Customer update(Customer customer) {
        Query query = new Query();
        query.addCriteria(Criteria.where("cusId").is(customer.getCusId()));
        Update update = new Update();
        //update.set("name", student.);
        //update.set("description", department.getDescription());
        return mongoTemplate.findAndModify(query, update, Customer.class);
    }

}
