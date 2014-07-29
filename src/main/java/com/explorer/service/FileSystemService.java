package com.explorer.service;

import com.explorer.annotations.*;
import com.explorer.domain.SharedPath;
import com.explorer.domain.fs.AbsoluteDirectory;
import com.explorer.domain.fs.Directory;
import com.explorer.domain.fs.RelativeDirectory;
import com.explorer.domain.fs.SharedDirectory;
import com.explorer.service.exceptions.DirectoryAlreadyExistsException;
import com.explorer.service.exceptions.DirectoryNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/**
 * Created by Michael on 04.07.2014.
 * Сервис отвечающий за взаимодействие с файловой системой
 */
@Service
public class FileSystemService {

    private static Path workingHome;

    @Autowired
    private SharedPathService sharedPathService;

    @Autowired
    private PermissionManager permissionManager;

    @GlobalAccessPointcut
    public Directory getDirectoryGlobal(String path) throws IOException {
        return getDirectoryAbsolute(path);
    }

    @HomeAccessPointcut
    public Directory getDirectoryHome(String path, String username) throws IOException {
        Path userdir = Paths.get(getWorkingDirectoryName(), username);
        Path p = Paths.get(userdir.toString(), path).toRealPath();
        return new RelativeDirectory(p, userdir);
    }

    private Directory getDirectoryAbsolute(String path) throws IOException {
        if (path.equals("")) {
            return new AbsoluteDirectory(null);
        }
        Path p = Paths.get(path).toRealPath();
        if (!Files.exists(p) || !Files.isDirectory(p))
            throw new DirectoryNotFoundException();
        return new AbsoluteDirectory(p);
    }

    @SharedAccessPointcut
    public Directory getSharedDirectory(String path, String username) throws IOException {
        List<SharedPath> paths = sharedPathService.getPathsByTargetUsername(username);
        if (path.equals("")) {
            return new SharedDirectory(paths);
        } else {
            final Path p = Paths.get(path).toRealPath();
            SharedPath current = getSharedPathForPath(p, paths);
            return new SharedDirectory(Paths.get(current.getPath()), path);
        }
    }

    private SharedPath getSharedPathForPath(Path path, List<SharedPath> paths) {
        return paths.stream().filter(sharedPath -> path.startsWith(sharedPath.getPath())).findFirst().get();
    }

    private void mkdir(Path path) throws IOException {
        if (Files.notExists(path.getParent()))
            throw new DirectoryNotFoundException();
        if (Files.exists(path))
            throw new DirectoryAlreadyExistsException();
        Files.createDirectory(path);
        try {
            Files.setPosixFilePermissions(path, permissionManager.getDefaultDir());
        } catch (UnsupportedOperationException ex) {
            File file = path.toFile();
            file.setReadable(true, false);
            file.setWritable(true, true);
            file.setExecutable(true, true);
        }
    }

    @GlobalAccessPointcut
    public void mkdirGlobal(String path, String name) throws IOException {
        mkdir(Paths.get(path, name));
    }

    @HomeAccessPointcut
    public void mkdirHome(String path, String username, String name) throws IOException {
        mkdir(Paths.get(getWorkingDirectoryName(), username, path, name));
    }

    @SharedAccessPointcut
    public void mkdirShared(String path, String username, String name) throws IOException {
        mkdir(Paths.get(path, name));
    }

    private boolean delete(File directory) {
        if (directory.isDirectory()) {
            File[] files = directory.listFiles();
            if (files == null || files.length == 0) {
                return directory.delete();
            } else {
                for (File f : files) {
                    if (!delete(f)) {
                        return false;
                    }
                }
                return directory.list().length == 0 && directory.delete();
            }
        } else {
            return directory.delete();
        }
    }

    private boolean deleteAbsolute(Path path) throws IOException {
        sharedPathService.deleteByPathValue(path.toRealPath().toString());
        return delete(path.toFile());
    }

    @GlobalAccessPointcut
    @GlobalDeleteControl
    public boolean deleteGlobal(String path) throws IOException {
        return deleteAbsolute(Paths.get(path));
    }

    @SharedAccessPointcut
    @SharedDeleteControl
    public boolean deleteShared(String path, String username) throws IOException {
        return deleteAbsolute(Paths.get(path));
    }

    @HomeAccessPointcut
    @HomeDeleteControl
    public boolean deleteHome(String path, String username) throws IOException {
        return deleteAbsolute(Paths.get(getWorkingDirectoryName(), username, path));
    }

    public Path getWorkingDirectoryPath() {
        if (workingHome == null)
            workingHome = Paths.get(System.getProperty("user.home"), "explorer home"); //TODO configurable
        return workingHome;
    }

    public String getWorkingDirectoryName() {
        return getWorkingDirectoryPath().toString();
    }

    public File getWorkingDirectory() {
        File directory = new File(getWorkingDirectoryName());
        if (!directory.exists()) {
            directory.mkdirs();
            directory.mkdir();
        }
        return directory;
    }

    public File createUserDirectory(String username) {
        File working = getWorkingDirectory();
        File user = new File(working.getPath() + File.separator + username);
        user.mkdir();
        return user;
    }

    public Path buildHomePath(String path, String username) throws IOException {
        Path userdir = Paths.get(getWorkingDirectoryName(), username);
        return Paths.get(userdir.toString(), path).toRealPath();
    }

    public Path getUserPath(String username) {
        return Paths.get(getWorkingDirectoryName(), username);
    }

}
