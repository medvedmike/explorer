package com.explorer.domain.fs;

/**
 * Created by Michael on 14.07.2014.
 */
public class Breadcrumb {
    private String name;
    private String path;

    public Breadcrumb(String name, String path) {
        this.name = name;
        this.path = path;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
