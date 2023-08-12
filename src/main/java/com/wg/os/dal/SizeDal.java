/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wg.os.dal;

import com.mongodb.client.result.DeleteResult;
import com.wg.os.document.ItemSize;
import java.util.List;

/**
 *
 * @author Lenovo
 */
public interface SizeDal {

    List find();

    List<ItemSize> findAll();

    ItemSize findById(String userId);

    ItemSize save(ItemSize size);

    ItemSize update(ItemSize size);

    DeleteResult remove(String id);
}
