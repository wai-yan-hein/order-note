/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wg.os.dal;

import com.mongodb.bulk.DeleteRequest;
import com.mongodb.client.result.DeleteResult;
import com.wg.os.document.ItemSize;
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
public class SizeDalImpl implements SizeDal {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public List<ItemSize> findAll() {
        return mongoTemplate.findAll(ItemSize.class);
    }

    @Override
    public ItemSize findById(String userId) {
        Query query = new Query();
        query.addCriteria(Criteria.where("cityId").is(userId));
        return mongoTemplate.findOne(query, ItemSize.class);
    }

    @Override
    public ItemSize save(ItemSize size) {
        return mongoTemplate.save(size);
    }

    @Override
    public ItemSize update(ItemSize size) {
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
        return mongoTemplate.remove(query, ItemSize.class);
    }

}
