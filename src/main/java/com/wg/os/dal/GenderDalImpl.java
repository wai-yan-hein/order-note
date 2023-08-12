/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wg.os.dal;

import com.mongodb.client.result.DeleteResult;
import com.wg.os.document.Gender;
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
public class GenderDalImpl implements GenderDal {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public List<Gender> findAll() {
        return mongoTemplate.findAll(Gender.class);
    }

    @Override
    public Gender findById(String userId) {
        return null;
    }

    @Override
    public Gender save(Gender user) {
        return mongoTemplate.save(user);
    }

    @Override
    public Gender update(Gender user) {
        return null;
    }

    @Override
    public List find() {
        return null;
    }

    @Override
    public DeleteResult remove(String id) {
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(id));
        return mongoTemplate.remove(query, Gender.class);

    }

}
