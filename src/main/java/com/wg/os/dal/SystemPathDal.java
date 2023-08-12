/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wg.os.dal;

import com.wg.os.document.SystemPath;
import java.util.List;

/**
 *
 * @author Lenovo
 */
public interface SystemPathDal {

    List find();

    List<SystemPath> findAll();

    SystemPath findById(String userId);

    SystemPath save(SystemPath sysPath);

    SystemPath update(SystemPath sysPath);
}
