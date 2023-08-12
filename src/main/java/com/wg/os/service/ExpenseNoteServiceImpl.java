/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wg.os.service;

import com.mongodb.client.result.DeleteResult;
import com.wg.os.dal.ExpenseNoteDal;
import com.wg.os.document.ExpNote;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Lenovo
 */
@Service
@Transactional
public class ExpenseNoteServiceImpl implements ExpenseNoteService {

    @Autowired
    private ExpenseNoteDal dal;

    @Override
    public List<ExpNote> findAll() {
        return dal.findAll();
    }

    @Override
    public ExpNote save(ExpNote student) {
        return dal.save(student);
    }

    @Override
    public DeleteResult remove(String id) {
        return dal.remove(id);
    }

    @Override
    public List<ExpNote> search(Date stDate, Date endDate) {
        return dal.search(stDate, endDate);
    }

}
