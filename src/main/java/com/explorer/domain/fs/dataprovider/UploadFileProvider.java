package com.explorer.domain.fs.dataprovider;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Michael on 15.07.2014.
 */
public interface UploadFileProvider {
    InputStream getInputStream() throws IOException;
    void write(InputStream stream) throws IOException;
}
