package com.explorer.domain.fs;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Created by Michael on 14.07.2014.
 * Информация о файле с абсолютными путями
 */
public class AbsoluteFileInfo implements FileInfo {

    private Path path;

    public AbsoluteFileInfo(Path path) {
        this.path = path;
    }

    @Override
    public String getName() {
        return path.getParent() == null ? path.toString() : path.getFileName().toString();
    }

    @Override
    public String getPath() {
        return path.toString();
    }

    @Override
    public long getSize() throws IOException {
        return Files.size(path);
    }

    @Override
    public boolean isFile() {
        return Files.isRegularFile(path);
    }
}
