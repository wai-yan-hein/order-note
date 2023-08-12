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
public class CurrentFont {

    public CurrentFont(String fontName, int fontStyle, int fontSize) {
        this.fontName = fontName;
        this.fontStyle = fontStyle;
        this.fontSize = fontSize;
    }

    public CurrentFont() {
    }

    private String fontName;
    private int fontStyle;
    private int fontSize;

}
