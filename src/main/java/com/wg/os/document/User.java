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
@Document("user")
public class User {
    @Id
    private String userId;
    private String userName;
    private String shortName;
    private String password;
    private Boolean isActive;
    private Date loginStart;
    private Date loginEnd;
}
