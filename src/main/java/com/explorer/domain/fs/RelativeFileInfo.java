package com.explorer.domain.fs;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by Michael on 14.07.2014.
 * Информация о файле полученном по относительному пути
 */
public class RelativeFileInfo implements FileInfo {

    private Path path;
    private Path absolutePath;
    private String relation;

    public RelativeFileInfo(Path path, String relationParent) {
        System.out.println("--new file: " + path.toString() + " parent: " + relationParent);
        absolutePath = path;
        relation = relationParent;
        this.path = Paths.get(relationParent, path.getFileName().toString());
        System.out.println("--path: " + this.path.toString());
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

    @Override
    public boolean isWritable() {
        return Files.isWritable(absolutePath);
    }
}
