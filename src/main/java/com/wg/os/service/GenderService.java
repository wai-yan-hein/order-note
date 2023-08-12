/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wg.os.service;

import com.mongodb.client.result.DeleteResult;
import com.wg.os.document.Gender;
import java.util.List;

/**
 *
 * @author Lenovo
 */
public interface GenderService {

    List find();

    List<Gender> findAll();

    Gender findById(String userId);

    Gender save(Gender student);

    Gender update(Gender student);

    DeleteResult remove(String id);

}
