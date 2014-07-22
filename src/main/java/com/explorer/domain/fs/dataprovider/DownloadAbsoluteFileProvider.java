package com.explorer.domain.fs.dataprovider;

import com.explorer.domain.fs.dataprovider.exceptions.FileNotFoundOnServerException;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by Michael on 15.07.2014.
 */
public class DownloadAbsoluteFileProvider implements DownloadFileProvider {

    private Path path;

    public DownloadAbsoluteFileProvider(String fileName) throws IOException {
        path = Paths.get(fileName).toRealPath();
        if (!Files.exists(path) || Files.isDirectory(path)) //проверка на то что файл существует и действительно является файлом
            throw new FileNotFoundOnServerException();
    }

    @Override
    public void copy(OutputStream stream) throws IOException {
        Files.copy(path, stream);
    }

    @Override
    public long getSize() throws IOException {
        return Files.size(path);
    }

    @Override
    public String getName() {
        return path.getFileName().toString();
    }
}
