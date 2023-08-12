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
public class Theme {

    public Theme(String name, String path) {
        this.name = name;
        this.path = path;
    }
    private String name;
    private String path;

    @Override
    public String toString() {
        return name;
    }

    public Theme() {
    }


}
