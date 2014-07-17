package com.explorer.service;

import com.explorer.domain.SharedPath;
import com.explorer.domain.fs.AbsoluteDirectory;
import com.explorer.domain.fs.Directory;
import com.explorer.domain.fs.RelativeDirectory;
import com.explorer.domain.fs.SharedDirectory;
import com.explorer.domain.fs.accesscontrol.exceptions.AccessDeniedException;
import com.explorer.service.exceptions.DirectoryNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

/**
 * Created by Michael on 04.07.2014.
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
            SharedPath current = paths.stream().findFirst().filter(new Predicate<SharedPath>() {
                @Override
                public boolean test(SharedPath sharedPath) {
                    return p.startsWith(Paths.get(sharedPath.getPath()));
                }
            }).get();
            return new SharedDirectory(Paths.get(current.getPath()), path); //мы уверены что путь есть?
        }
    }
//    private List<DirectoryInfo.BreadCrumb> getBreadcrumbs(Path path, String start) {
//        int max = path.getNameCount();
//        List<DirectoryInfo.BreadCrumb> breadcrumbs = new ArrayList<>(max);
//
//        System.out.println(path.subpath(2, path.getNameCount() - 1));
//
//        Path p = path;
//        String name;
//        do {
//            Path next = p.getParent();
//            String pth = p.toString();
//            name = next == null ? pth : p.getFileName().toString();
//            breadcrumbs.add(new DirectoryInfo.BreadCrumb(name, pth));
//            p = next;
//        } while (p != null && !name.equals(start));
//        Collections.reverse(breadcrumbs);
//        return  breadcrumbs;
//    }

    public Path getWorkingDirectoryPath() {
        if (workingHome == null)
            workingHome = Paths.get(System.getProperty("user.home"), "explorer home"); //TODO configurable
        return workingHome;
    }

    public String getWorkingDirectoryName() {
        return getWorkingDirectoryPath().toString();
    }

    public File getWorkingDirectory() {
        File directory = new File(getWorkingDirectoryName().toString());
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
        Path userdir = Paths.get(getWorkingDirectoryName().toString(), username);
        Path p = Paths.get(userdir.toString(), path).toRealPath();
        if (!p.startsWith(userdir))
            throw new AccessDeniedException();
        return p;
    }

}
