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
public class TotalAmount {
    private String name;
    private Double ttlAmt;

    public TotalAmount(String name, Double ttlAmt) {
        this.name = name;
        this.ttlAmt = ttlAmt;
    }
    
}
