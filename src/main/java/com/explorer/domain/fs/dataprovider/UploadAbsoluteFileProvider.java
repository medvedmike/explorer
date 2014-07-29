package com.explorer.domain.fs.dataprovider;

import com.explorer.service.exceptions.DirectoryNotFoundException;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.PosixFilePermission;
import java.util.Set;

/**
 * Created by Michael on 15.07.2014.
 * Запись файла на диск по абсолютному пути
 */
public class UploadAbsoluteFileProvider implements UploadFileProvider {

    private Path path;

    public UploadAbsoluteFileProvider(String directory, String fileName) throws IOException {
        path = Paths.get(directory).toRealPath();
        if (!Files.exists(path.getParent()) || !Files.isDirectory(path.getParent())) //проверка существования
            throw new DirectoryNotFoundException();
        path = Paths.get(path.toString(), fileName);
        if (Files.exists(path)) //загружаем только если файла с таким мменем не существует
            throw new FileAlreadyExistsException(path.toString());
    }

    @Override
    public void write(InputStream stream, Set<PosixFilePermission> permissions) throws IOException {
        write(stream);
        try {
            Files.setPosixFilePermissions(path, permissions);
        } catch (UnsupportedOperationException ex) {
            File file = path.toFile();
            file.setReadable(true, false);
            file.setWritable(true, true);
            file.setExecutable(false);
        }
    }

    @Override
    public void write(InputStream stream) throws IOException {
        Files.copy(stream, path);
    }

}
