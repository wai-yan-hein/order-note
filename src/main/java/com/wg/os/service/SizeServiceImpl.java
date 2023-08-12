/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wg.os.service;

import com.mongodb.client.result.DeleteResult;
import com.wg.os.dal.SizeDal;
import com.wg.os.document.City;
import com.wg.os.document.ItemSize;
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
public class SizeServiceImpl implements SizeService {

    @Autowired
    private SizeDal dal;

    @Override
    public List<ItemSize> findAll() {
        return dal.findAll();
    }

    @Override
    public ItemSize findById(String userId) {
        return dal.findById(userId);
    }

    @Override
    public ItemSize save(ItemSize size) {
        return dal.save(size);
    }

    @Override
    public ItemSize update(ItemSize size) {
        return dal.update(size);
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
