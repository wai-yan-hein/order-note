/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wg.os.dal;

import com.mongodb.client.result.DeleteResult;
import com.wg.os.document.City;
import java.util.List;

/**
 *
 * @author Lenovo
 */
public interface CityDal {

    List find();

    List<City> findAll();

    City findById(String userId);

    City save(City student);

    City update(City student);

    DeleteResult remove(String id);
}
