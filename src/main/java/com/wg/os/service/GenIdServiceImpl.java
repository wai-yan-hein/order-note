/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wg.os.service;

import com.wg.os.dal.GenIdDal;
import com.wg.os.document.GenId;
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
public class GenIdServiceImpl implements GenIdService {

    @Autowired
    private GenIdDal dal;

    @Override
    public List<GenId> findAll() {
        return dal.findAll();
    }

    @Override
    public GenId findById(String code) {
        return dal.findById(code);
    }

    @Override
    public GenId save(GenId genId) {
        return dal.save(genId);
    }

    @Override
    public GenId update(GenId genId) {
        return dal.update(genId);
    }

    @Override
    public GenId searchGenType(String name) {
        return dal.searchGenType(name);
    }

}
