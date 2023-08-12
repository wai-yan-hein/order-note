/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wg.os.dal;

import com.mongodb.client.result.DeleteResult;
import com.wg.os.document.ItemColor;
import java.util.List;

/**
 *
 * @author Lenovo
 */
public interface ColorDal {

    List<ItemColor> findAll();

    ItemColor findById(String code);

    ItemColor save(ItemColor student);

    ItemColor update(ItemColor student);

    DeleteResult remove(String id);
}
