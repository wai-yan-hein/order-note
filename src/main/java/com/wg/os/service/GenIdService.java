/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wg.os.service;

import com.wg.os.document.GenId;
import java.util.List;

/**
 *
 * @author Lenovo
 */
public interface GenIdService {

    List<GenId> findAll();

    GenId findById(String code);

    GenId save(GenId genId);

    GenId update(GenId genId);

    GenId searchGenType(String name);

}
