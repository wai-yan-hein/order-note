/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wg.os.util;

import java.io.File;
import javax.swing.filechooser.FileFilter;

/**
 *
 * @author Lenovo
 */
public class FileTypeFilter extends FileFilter {

    private final String extension;
    private final String description;

    public FileTypeFilter(String extension, String description) {
        this.extension = extension;
        this.description = description;
    }

    @Override
    public boolean accept(File f) {
        if (f.isDirectory()) {
            return true;
        }
        return f.getName().endsWith(extension);
    }

    @Override
    public String getDescription() {
        return description + String.format("(*%s)", extension);
    }

}
