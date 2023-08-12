/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wg.os.service;

import com.mongodb.client.result.DeleteResult;
import com.wg.os.dal.CityDal;
import com.wg.os.document.City;
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
public class CityServiceImpl implements CityService {

    @Autowired
    private CityDal dal;

    @Override
    public List<City> findAll() {
        return dal.findAll();
    }

    @Override
    public City findById(String userId) {
        return dal.findById(userId);
    }

    @Override
    public City save(City student) {
        return dal.save(student);
    }

    @Override
    public City update(City student) {
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
