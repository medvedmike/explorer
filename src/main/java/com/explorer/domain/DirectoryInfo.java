package com.explorer.domain;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Iterator;

/**
 * Created by Michael on 04.07.2014.
 */
public class DirectoryInfo {

    public FileInfo[] getBreadcrumbs() {
        return breadcrumbs;
    }

    public class FileInfo {
        private String name;
        private String path;

        public FileInfo(String name, String path) {
            this.name = name;
            this.path = path;
        }

        public String getName() {
            return name;
        }

        public String getPath() {
            return path;
        }
    }

    private String name;
    private String path;
    private boolean root;
    private String parent;
    private boolean canWrite;
    private FileInfo[] breadcrumbs;

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
        canWrite = false;
        breadcrumbs = null;
    }

    private void createDirectory(File file) {
        root = false;
        name = file.getParentFile() == null ? file.toString() : file.getName();
        parent = file.getParent();
        path = file.getPath();
        Path p = file.toPath();
        canWrite = Files.isWritable(p);
        breadcrumbs = new FileInfo[p.getNameCount()];
        if (breadcrumbs.length > 0)
            p = p.getParent();
        for (int i = breadcrumbs.length - 1; i > -1; i--) {
            String pth = p.toString();
            String nm = i == 0 ? pth : p.getFileName().toString();
            breadcrumbs[i] = new FileInfo(nm, pth);
            p = p.getParent();
        }
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

    public boolean isCanWrite() {
        return canWrite;
    }
}
