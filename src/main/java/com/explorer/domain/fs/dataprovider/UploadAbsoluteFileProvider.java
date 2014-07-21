package com.explorer.domain.fs.dataprovider;

import com.explorer.service.exceptions.DirectoryNotFoundException;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by Michael on 15.07.2014.
 */
public class UploadAbsoluteFileProvider implements UploadFileProvider {

    private Path path;

    public UploadAbsoluteFileProvider(String directory, String fileName) throws IOException {
        path = Paths.get(directory).toRealPath();
        if (!Files.exists(path.getParent()) || !Files.isDirectory(path.getParent()))
            throw new DirectoryNotFoundException();
        path = Paths.get(path.toString(), fileName);
    }

    @Override
    public InputStream getInputStream() throws IOException {
        return Files.newInputStream(path);
    }

    @Override
    public void write(InputStream stream) throws IOException {
        Files.copy(stream, path);
    }

}
