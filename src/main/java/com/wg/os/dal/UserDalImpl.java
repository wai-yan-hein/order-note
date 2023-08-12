/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wg.os.dal;

import com.mongodb.client.result.DeleteResult;
import com.wg.os.document.City;
import com.wg.os.document.User;
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
public class UserDalImpl implements UserDal {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public User search(String user, String pass) {
        Query query = new Query();
        query.addCriteria(Criteria.where("shortName").is(user).and("password").is(pass));
        return mongoTemplate.findOne(query, User.class);

    }

    @Override
    public User save(User user) {
        return mongoTemplate.save(user);
    }

    @Override
    public DeleteResult remove(String name) {
        Query query = new Query();
        query.addCriteria(Criteria.where("userId").is(name));
        return mongoTemplate.remove(query, User.class);
    }

    @Override
    public List<User> findAll() {
        return mongoTemplate.findAll(User.class);
    }

}
