package com.explorer.domain;

import java.io.File;

/**
 * Created by Michael on 04.07.2014.
 */
public class DirectoryInfo {

    private String name;
    private String path;
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
        path = null;
    }

    private void createDirectory(File file) {
        root = false;
        name = file.getParentFile() == null ? file.toString() : file.getName();
        parent = file.getParent();
        path = file.getPath();
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

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
