/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wg.os.document;

import java.util.Date;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 *
 * @author Lenovo
 */
@Getter
@Setter
@Document("expense_note")
public class ExpNote {
    private String id;
    private Date expDate;
    private Expense exp;
    private Double amt;
}
