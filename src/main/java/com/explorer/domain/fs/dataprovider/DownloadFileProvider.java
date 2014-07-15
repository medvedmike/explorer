package com.explorer.domain.fs.dataprovider;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by Michael on 15.07.2014.
 */
public interface DownloadFileProvider {
    OutputStream getOutputStream() throws IOException;
    void copy(OutputStream stream) throws IOException;
    long getSize() throws IOException;
    String getName();
}
