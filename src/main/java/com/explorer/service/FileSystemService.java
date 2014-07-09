package com.explorer.service;

import com.explorer.domain.DirectoryInfo;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;

/**
 * Created by Michael on 04.07.2014.
 */
@Service
public class FileSystemService {

    public File[] getDirectoryContent(String path) {
        if (path == null)
            return File.listRoots();
        File dir = new File(path);
        if (!dir.exists() && dir.isFile()) //TODO custom exceptions
            throw new RuntimeException("directory is not exists on a server");
        return dir.listFiles();
    }

    public DirectoryInfo getDirectoryInfo(String path) {
        if (path == null)
            return new DirectoryInfo(null);
        else
            return new DirectoryInfo(new File(path));
    }

    public File getFile(String name) {
        File file = new File(name);
        if (!file.exists() || file.isDirectory())
            return null;
        return file;
    }

    public File getWorkingDirectory() {
        File directory = new File(System.getProperty("user.home") + File.separator + "explorer home");
        if (!directory.exists()) {
            directory.mkdirs();
            directory.mkdir();
        }
        return directory;
    }

}
