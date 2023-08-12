/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wg.os.document;

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
@Document("setting")
public class Setting {

    public Setting() {
    }

    @Id
    private String id;
    private CurrentFont font;
    private String retartPath;
    private String imgPath;
    private Theme theme;

}
