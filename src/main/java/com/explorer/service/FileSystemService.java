package com.explorer.service;

import com.explorer.domain.SharedPath;
import com.explorer.domain.fs.AbsoluteDirectory;
import com.explorer.domain.fs.Directory;
import com.explorer.domain.fs.RelativeDirectory;
import com.explorer.domain.fs.SharedDirectory;
import com.explorer.domain.fs.accesscontrol.exceptions.AccessDeniedException;
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
import java.util.function.Predicate;

/**
 * Created by Michael on 04.07.2014.
 * Сервис отвечающий за взаимодействие с файловой системой
 */
@Service
public class FileSystemService {

    private static Path workingHome;

    @Autowired
    private SharedPathService sharedPathService;

    public Directory getDirectoryGlobal(String path) throws IOException {
        return getDirectoryAbsolute(path);
    }

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

    public Directory getSharedDirectory(String path, String username) throws IOException {
        List<SharedPath> paths = sharedPathService.getPathsByTargetUsername(username);
        if (path.equals("")) {
            return new SharedDirectory(paths);
        } else {
            final Path p = Paths.get(path).toRealPath();
            SharedPath current = paths.stream().filter(new Predicate<SharedPath>() {
                @Override
                public boolean test(SharedPath sharedPath) {
                    return p.startsWith(sharedPath.getPath());
                }
            }).findFirst().get();
            return new SharedDirectory(Paths.get(current.getPath()), path);
        }
    }

    private void mkdir(Path path) throws IOException {
        if (Files.notExists(path.getParent()))
            throw new DirectoryNotFoundException();
        if (Files.exists(path))
            throw new DirectoryAlreadyExistsException();
        Files.createDirectory(path);
    }

    public void mkdirGlobal(String path, String name) throws IOException {
        mkdir(Paths.get(path, name));
    }

    public void mkdirHome(String path, String name, String username) throws IOException {
        mkdir(Paths.get(getWorkingDirectoryName(), username, path, name));
    }

    public void mkdirShared(String path, String name, String username) throws IOException {
        mkdir(Paths.get(path, name));
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
        Path p = Paths.get(userdir.toString(), path).toRealPath();
        if (!p.startsWith(userdir))
            throw new AccessDeniedException();
        return p;
    }

}
