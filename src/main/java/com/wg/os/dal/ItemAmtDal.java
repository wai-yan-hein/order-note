/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wg.os.dal;

import com.wg.os.document.ItemAmt;

/**
 *
 * @author Lenovo
 */
public interface ItemAmtDal {

    ItemAmt findById(String imgPath);

    ItemAmt save(ItemAmt itemAmt);
}
