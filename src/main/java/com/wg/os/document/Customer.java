/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wg.os.document;

import java.util.Date;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 *
 * @author Lenovo
 */
@Getter
@Setter
@Document("customer")
public class Customer {

    @Id
    private String cusId;
    private String cusName;
    private String cusPhoneNo;
    private String cusAddress;
    private City cusCity;
    private Date cusRegDate;
    private Gender cusGender;
    private String cusEmail;
    private String cusSocialLink;
}
