/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wg.os.service;

import com.wg.os.dal.SystemPathDal;
import com.wg.os.document.SystemPath;
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
public class SystemPathServiceImpl implements SystemPathService {

    @Autowired
    private SystemPathDal dal;

    @Override
    public List<SystemPath> findAll() {
        return dal.findAll();
    }

    @Override
    public SystemPath findById(String userId) {
        return dal.findById(userId);
    }

    @Override
    public SystemPath save(SystemPath sysPath) {
        return dal.save(sysPath);
    }

    @Override
    public SystemPath update(SystemPath sysPath) {
        return dal.update(sysPath);
    }

    @Override
    public List find() {
        return dal.find();
    }

}
