package com.explorer.service.accesscontrol;

import com.explorer.domain.SharedPath;
import com.explorer.service.FileSystemService;
import com.explorer.service.SharedPathService;
import com.explorer.service.accesscontrol.exceptions.AccessDeniedException;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.function.Predicate;

/**
 * Created by Michael on 26.07.2014.
 */
@Component
@Aspect
public class DeleteControl {

    @Autowired
    private SharedPathService sharedPathService;

    @Autowired
    private FileSystemService fileSystem;

    public boolean checkGlobalRoot(Path path) {
        return path.getFileName() == null;
    }

    public boolean checkHomeRoot(String path, String username) throws IOException {
        return fileSystem.buildHomePath(path, username).getParent().compareTo(fileSystem.getWorkingDirectoryPath()) == 0;
    }

    public boolean checkHomeRoot(Path path, String username) throws IOException {
        return path.compareTo(fileSystem.getWorkingDirectoryPath()) == 0;
    }

    public boolean checkSharedRoot(Path path, String username) {
        List<SharedPath> paths = sharedPathService.getPathsByTargetUsername(username);
        return isSharedRoot(path, paths);
    }

    @Before(value = "@annotation(com.explorer.annotations.GlobalDeleteControl) && args(path, ..))", argNames = "path")
    public void onDeleteGlobal(String path) {
        if (checkGlobalRoot(Paths.get(path).toAbsolutePath())) {
            throw new AccessDeniedException();
        }
    }

    @Before(value = "@annotation(com.explorer.annotations.HomeDeleteControl) && args(path, username, ..))", argNames = "path, username")
    public void onDeleteHome(String path, String username) throws IOException {
        Path p = Paths.get(path).toAbsolutePath();
        if (checkGlobalRoot(p) || checkHomeRoot(path, username)) {
            throw new AccessDeniedException();
        }
    }

    private boolean isSharedRoot(Path path, List<SharedPath> paths) {
        String p = path.toAbsolutePath().toString();
        return paths.stream().anyMatch(sharedPath -> sharedPath.getPath().equals(p));
    }

    @Before(value = "@annotation(com.explorer.annotations.SharedDeleteControl) && args(path, username, ..))", argNames = "path, username")
    public void onDeleteShared(String path, String username) throws IOException {
        Path p = Paths.get(path).toAbsolutePath();
        if (checkGlobalRoot(p) || checkHomeRoot(p, username) || checkSharedRoot(p, username)) {
            throw new AccessDeniedException();
        }
    }
}
