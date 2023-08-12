/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wg.os.service;

import com.wg.os.dal.ItemAmtDal;
import com.wg.os.document.ItemAmt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Lenovo
 */
@Service
@Transactional
public class ItemAmtServiceImpl implements ItemAmtService {
    @Autowired
    private ItemAmtDal dal;

    @Override
    public ItemAmt findById(String imgPath) {
       return dal.findById(imgPath);
    }

    @Override
    public ItemAmt save(ItemAmt itemAmt) {
        return  dal.save(itemAmt);
    }

  

}
