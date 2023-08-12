/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wg.os.service;

import com.mongodb.client.result.DeleteResult;
import com.wg.os.dal.GenderDal;
import com.wg.os.document.Gender;
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
public class GenderServiceImpl implements GenderService {

    @Autowired
    private GenderDal dal;

    @Override
    public List<Gender> findAll() {
        return dal.findAll();
    }

    @Override
    public Gender findById(String userId) {
        return dal.findById(userId);
    }

    @Override
    public Gender save(Gender student) {
        return dal.save(student);
    }

    @Override
    public Gender update(Gender student) {
        return dal.update(student);
    }

    @Override
    public List find() {
        return dal.find();
    }

    @Override
    public DeleteResult remove(String id) {
        return dal.remove(id);
    }

}
