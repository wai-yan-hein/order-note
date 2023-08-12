/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wg.os.dal;

import com.mongodb.client.result.DeleteResult;
import com.wg.os.document.ItemColor;
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
public class ColorDalImpl implements ColorDal {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public List<ItemColor> findAll() {
        return mongoTemplate.findAll(ItemColor.class);
    }

    @Override
    public ItemColor findById(String code) {
        Query query = new Query();
        query.addCriteria(Criteria.where("colorId").is(code));
        return mongoTemplate.findOne(query, ItemColor.class);
    }

    @Override
    public ItemColor save(ItemColor color) {
        return mongoTemplate.save(color);
    }

    @Override
    public ItemColor update(ItemColor color) {
        return null;
    }

    @Override
    public DeleteResult remove(String id) {
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(id));
        return mongoTemplate.remove(query, ItemColor.class);
    }

}
