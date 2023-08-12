/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wg.os.service;

import com.mongodb.client.result.DeleteResult;
import com.wg.os.document.Payment;
import java.util.List;

/**
 *
 * @author Lenovo
 */
public interface PaymentService {

    List find();

    List<Payment> findAll();

    Payment findById(String userId);

    Payment save(Payment student);

    Payment update(Payment student);

    DeleteResult remove(String name);

}
