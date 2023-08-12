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
@Document("gender")
public class Gender {

    private String id;
    private String cusGender;

    public Gender() {

    }

    public Gender(String cusGender) {
        this.cusGender = cusGender;
    }

    @Override
    public String toString() {
        return cusGender; //To change body of generated methods, choose Tools | Templates.
    }

}
