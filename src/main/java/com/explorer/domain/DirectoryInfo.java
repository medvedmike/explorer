package com.explorer.domain;

import java.io.File;

/**
 * Created by Michael on 04.07.2014.
 */
public class DirectoryInfo {

    private String name;
    private boolean root;
    private String parent;

    public DirectoryInfo(File file) {
        if (file == null) {
            createRoot();
        } else {
            createDirectory(file);
        }
    }

    private void createRoot() {
        root = true;
        name = "root";
        parent = null;
    }

    private void createDirectory(File file) {
        root = false;
        name = file.getParentFile() == null ? file.toString() : file.getName();
        parent = file.getParent();
    }

    public String getName() {
        return name;
    }

    public boolean isRoot() {
        return root;
    }

    public String getParent() {
        return parent;
    }
}
