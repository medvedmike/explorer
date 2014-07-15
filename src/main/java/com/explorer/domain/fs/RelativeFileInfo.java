package com.explorer.domain.fs;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by Michael on 14.07.2014.
 */
public class RelativeFileInfo implements FileInfo {

    private Path path;
    private Path absolutePath;
    private String relation;

    public RelativeFileInfo(Path path, String relationParent) {
        absolutePath = path;
        relation = relationParent;
        this.path = Paths.get(relationParent, path.getFileName().toString());//TODO like this
    }

    @Override
    public String getName() {
        return path.getFileName().toString();
    }

    @Override
    public String getPath() {
        return path.toString();
    }

    @Override
    public long getSize() throws IOException {
        return Files.size(absolutePath);
    }

    @Override
    public boolean isFile() {
        return Files.isRegularFile(absolutePath);
    }
}
