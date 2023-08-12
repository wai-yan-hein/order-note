/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wg.os.service;

import com.wg.os.dal.SettingDalImpl;
import com.wg.os.document.Setting;
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
public class SettingServiceImpl implements SettingService {
    @Autowired
    private SettingDalImpl dal;

    @Override
    public Setting findById(String id) {
       return dal.findById(id);
    }

    @Override
    public Setting save(Setting itemAmt) {
        return  dal.save(itemAmt);
    }

    @Override
    public List<Setting> findAll() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

  

}
