/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wg.os.dal;

import com.wg.os.document.Setting;
import java.util.List;

/**
 *
 * @author Lenovo
 */
public interface SettingDal {

    Setting findById(String id);

    Setting save(Setting itemAmt);

    List<Setting> findAll();
}
