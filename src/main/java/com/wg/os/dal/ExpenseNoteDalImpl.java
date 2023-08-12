/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wg.os.dal;

import com.mongodb.client.result.DeleteResult;
import com.wg.os.document.ExpNote;
import com.wg.os.util.Util1;
import java.util.Date;
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
public class ExpenseNoteDalImpl implements ExpenseNoteDal {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public List<ExpNote> findAll() {
        return mongoTemplate.findAll(ExpNote.class);
    }

    @Override
    public ExpNote save(ExpNote exp) {
        return mongoTemplate.save(exp);
    }

    @Override
    public DeleteResult remove(String id) {
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(id));
        return mongoTemplate.remove(query, ExpNote.class);
    }

    @Override
    public List<ExpNote> search(Date stDate, Date endDate) {
        Query query = new Query();
        query.addCriteria(Criteria.where("expDate").gte(stDate).lte(endDate));
        return mongoTemplate.find(query, ExpNote.class);

    }

}
