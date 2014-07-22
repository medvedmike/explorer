package com.explorer.domain.fs;

import java.io.IOException;

/**
 * Created by Michael on 14.07.2014.
 */
public interface FileInfo {
    public String getName(); //имя файла
    public String getPath(); //путь к файлу
    public long getSize() throws IOException; //размер
    public boolean isFile(); //является ли файлом
}
