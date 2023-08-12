/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wg.os.dal;

import com.wg.os.document.Setting;
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
public class SettingDalImpl implements SettingDal {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public Setting findById(String id) {
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(id));
        return mongoTemplate.findOne(query, Setting.class);
 
    }

    @Override
    public Setting save(Setting itemAmt) {
        return mongoTemplate.save(itemAmt);
    }

    @Override
    public List<Setting> findAll() {
        return mongoTemplate.findAll(Setting.class);
    }

}
