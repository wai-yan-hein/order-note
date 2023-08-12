/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wg.os.dal;

import com.wg.os.document.OrderDetail;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Lenovo
 */
public interface OrderDetailDal {

    List<OrderDetail> findAll();

    OrderDetail findById(String userId);

    OrderDetail save(OrderDetail ot);

    OrderDetail update(OrderDetail ot);

    List<OrderDetail> search(String cusId, Date date);

}
