/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wg.os.service;

import com.wg.os.dal.CustomerDal;
import com.wg.os.dal.GenIdDal;
import com.wg.os.document.Customer;
import com.wg.os.document.GenId;
import com.wg.os.util.Util1;
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
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerDal dal;
    @Autowired
    private GenIdDal genIdDal;

    @Override
    public List<Customer> findAll() {
        return dal.findAll();
    }

    @Override
    public Customer findById(String cusCode) {
        return dal.findById(cusCode);
    }

    @Override
    public Customer save(Customer customer) {
        int genId;
        if (customer.getCusId() == null) {
            GenId gen = genIdDal.findById("CUSTOMER");
            if (gen != null) {
                genId = gen.getGenId();
                gen.setGenId(genId + 1);
            } else {
                gen = new GenId();
                gen.setGenType("CUSTOMER");
                gen.setGenId(2);
                genId = 1;
            }
            genIdDal.save(gen);
            customer.setCusId(String.valueOf(genId).concat(Util1.getYear()));

        }
        return dal.save(customer);
    }

    @Override
    public Customer update(Customer customer) {
        return dal.update(customer);
    }

}
