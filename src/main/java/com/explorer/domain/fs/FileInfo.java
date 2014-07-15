package com.explorer.domain.fs;

import java.io.IOException;

/**
 * Created by Michael on 14.07.2014.
 */
public interface FileInfo {
    public String getName();
    public String getPath();
    public long getSize() throws IOException;
    public boolean isFile();
}
