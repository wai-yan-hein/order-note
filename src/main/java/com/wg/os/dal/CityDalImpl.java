/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wg.os.dal;

import com.mongodb.client.result.DeleteResult;
import com.wg.os.document.City;
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
public class CityDalImpl implements CityDal {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public List<City> findAll() {
        return mongoTemplate.findAll(City.class);
    }

    @Override
    public City findById(String userId) {
        Query query = new Query();
        query.addCriteria(Criteria.where("cityId").is(userId));
        return mongoTemplate.findOne(query, City.class);
    }

    @Override
    public City save(City user) {
        return mongoTemplate.save(user);
    }

    @Override
    public City update(City user) {
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
        return mongoTemplate.remove(query, City.class);

    }

}
