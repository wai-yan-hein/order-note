/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wg.os.document;

import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author Lenovo
 */
@Getter
@Setter
public class OrderDetail {

    private String orderId;
    private Double itemPrice;
    private Integer itemQty;
    private Double itemDiscount;
    private String itemImage;
    private String itemSize;
    private String itemColor;
    private Double itemPurPrice;
}
