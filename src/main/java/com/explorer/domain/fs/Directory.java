package com.explorer.domain.fs;

/**
 * Created by Michael on 14.07.2014.
 */
public interface Directory {
    public String getPath();
    public String getName();
    public String getParent();
    public FileInfo[] getChildren();
    public Breadcrumb[] getBreadcrumbs();
    public boolean isRoot();
    public boolean isWritable();
}
