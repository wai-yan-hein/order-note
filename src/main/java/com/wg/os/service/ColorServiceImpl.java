/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wg.os.service;

import com.mongodb.client.result.DeleteResult;
import com.wg.os.dal.ColorDal;
import com.wg.os.document.ItemColor;
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
public class ColorServiceImpl implements ColorService {

    @Autowired
    private ColorDal dal;

    @Override
    public List<ItemColor> findAll() {
        return dal.findAll();
    }

    @Override
    public ItemColor findById(String code) {
        return dal.findById(code);
    }

    @Override
    public ItemColor save(ItemColor student) {
        return dal.save(student);
    }

    @Override
    public ItemColor update(ItemColor student) {
        return dal.update(student);
    }

    @Override
    public DeleteResult remove(String id) {
        return dal.remove(id);
    }

}
