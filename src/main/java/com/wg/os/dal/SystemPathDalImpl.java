/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wg.os.dal;

import com.wg.os.document.SystemPath;
import com.wg.os.service.SystemPathService;
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
public class SystemPathDalImpl implements SystemPathDal {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public List<SystemPath> findAll() {
        return mongoTemplate.findAll(SystemPath.class);
    }

    @Override
    public SystemPath findById(String userId) {
        Query query = new Query();
        query.addCriteria(Criteria.where("cityId").is(userId));
        return mongoTemplate.findOne(query, SystemPath.class);
    }

    @Override
    public SystemPath save(SystemPath sysPath) {
        return mongoTemplate.save(sysPath);
    }

    @Override
    public SystemPath update(SystemPath sysPath) {
        return null;
    }

    @Override
    public List find() {
        return null;
    }

}
