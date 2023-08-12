/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wg.os.dal;

import com.wg.os.document.ItemAmt;
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
public class ItemAmtDalImpl implements ItemAmtDal {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public ItemAmt findById(String imgPath) {
        Query query = new Query();
        query.addCriteria(Criteria.where("imgPath").is(imgPath));
        return mongoTemplate.findOne(query, ItemAmt.class);
    }

    @Override
    public ItemAmt save(ItemAmt itemAmt) {
        return mongoTemplate.save(itemAmt);
    }

}
