package com.explorer.domain.fs;

/**
 * Created by Michael on 14.07.2014.
 * Информация о дирректории
 */
public interface Directory {
    public String getPath(); //путь
    public String getName(); //имя
    public String getParent(); //родительский путь
    public FileInfo[] getChildren(); //список файлво в дирректории
    public Breadcrumb[] getBreadcrumbs(); //"ххлебные крошки"
    public boolean isRoot(); //является ли дирректорий начальным (домашний каталог/список расшаренных путей/список корней физической ФС)
    public boolean isWritable(); //возможность записи в файл
}
