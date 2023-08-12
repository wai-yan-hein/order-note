/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wg.os.dal;

import com.wg.os.document.GenId;
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
public class GenIdDalImpl implements GenIdDal {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public List<GenId> findAll() {
        return mongoTemplate.findAll(GenId.class);
    }

    @Override
    public GenId findById(String code) {
        Query query = new Query();
        query.addCriteria(Criteria.where("genType").is(code));
        return mongoTemplate.findOne(query, GenId.class);
    }

    @Override
    public GenId save(GenId customer) {
        return mongoTemplate.save(customer);
    }

    @Override
    public GenId update(GenId customer) {
        Query query = new Query();
        query.addCriteria(Criteria.where("cusId").is(customer.getGenId()));
        Update update = new Update();
        //update.set("name", student.);
        //update.set("description", department.getDescription());
        return mongoTemplate.findAndModify(query, update, GenId.class);
    }

    @Override
    public GenId searchGenType(String name) {
        Query query = new Query();
        query.addCriteria(Criteria.where("genType").is(name));
        return mongoTemplate.findOne(query, GenId.class);
    }

}
