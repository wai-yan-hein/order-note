/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wg.os.service;

import com.wg.os.document.Customer;
import java.util.List;

/**
 *
 * @author Lenovo
 */
public interface CustomerService {

    List<Customer> findAll();

    Customer findById(String code);

    Customer save(Customer customer);

    Customer update(Customer student);

}
