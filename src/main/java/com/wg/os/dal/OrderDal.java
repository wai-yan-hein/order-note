/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wg.os.dal;

import com.wg.os.document.Order;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Lenovo
 */
public interface OrderDal {

    List<Order> findAll();

    Order findById(String userId);

    Order save(Order ot);

    Order delete(String id);

    List<Order> search(Date startDate, Date endDate, String cusCode, String cusCity,String paymentF);

    List<Order> search(String cusCode);

}
