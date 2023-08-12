/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wg.os.document;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 *
 * @author Lenovo
 */
@Getter
@Setter
@Document("size")
public class ItemSize {
    private String id;
    private String itemSize;

    @Override
    public String toString() {
        return itemSize; //To change body of generated methods, choose Tools | Templates.
    }

}
