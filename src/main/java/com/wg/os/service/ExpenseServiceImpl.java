/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wg.os.service;

import com.mongodb.client.result.DeleteResult;
import com.wg.os.dal.CityDal;
import com.wg.os.dal.ExpenseDal;
import com.wg.os.document.City;
import com.wg.os.document.Expense;
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
public class ExpenseServiceImpl implements ExpenseService {

    @Autowired
    private ExpenseDal dal;

    @Override
    public List<Expense> findAll() {
        return dal.findAll();
    }

    @Override
    public Expense save(Expense student) {
        return dal.save(student);
    }

    @Override
    public DeleteResult remove(String id) {
        return dal.remove(id);
    }

}
