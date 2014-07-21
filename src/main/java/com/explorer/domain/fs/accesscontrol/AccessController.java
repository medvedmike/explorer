package com.explorer.domain.fs.accesscontrol;

import com.explorer.domain.SharedPath;
import com.explorer.domain.fs.accesscontrol.exceptions.AccessDeniedException;
import com.explorer.domain.fs.accesscontrol.exceptions.UnauthorizedException;
import com.explorer.service.FileSystemService;
import com.explorer.service.SharedPathService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.function.Predicate;

/**
 * Created by Michael on 17.07.2014.
 */
public class AccessController {

    @Autowired
    private ApplicationContext context;

    @Autowired
    private FileSystemService fileSystemService;

    @Autowired
    private SharedPathService sharedPathService;

    private boolean isAdmin(Authentication authentication) {
         return authentication.getAuthorities().stream().anyMatch(new Predicate<GrantedAuthority>() {
            @Override
            public boolean test(GrantedAuthority o) {
                return o.toString().compareTo("ROLE_ADMIN") == 0;
            }
        });
    }


    public void checkGlobalDir(String path) {
        System.out.println("test global");
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null)
            throw new UnauthorizedException();
        if (!isAdmin(auth))
            throw new AccessDeniedException();
    }

    public void checkHomeDir(String path, String username) throws IOException {
        System.out.println("test home");
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null)
            throw new UnauthorizedException();

        Path userdir = Paths.get(fileSystemService.getWorkingDirectoryName(), username);
        Path p = Paths.get(userdir.toString(), path).toRealPath();
        if (!p.startsWith(userdir))
            throw new AccessDeniedException();
    }

    public void checkSharedDir(final String path, String username) throws IOException {
        System.out.println("test shared");
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null)
            throw new UnauthorizedException();

        if (!path.equals("")) {
            List<SharedPath> paths = sharedPathService.getPathsByTargetUsername(username);
            final Path p = Paths.get(path).toRealPath();
            if (!paths.stream().anyMatch(new Predicate<SharedPath>() {
                @Override
                public boolean test(SharedPath sharedPath) {
                    return p.startsWith(sharedPath.getPath());
                }
            })) {
                throw new AccessDeniedException();
            }
        }
    }
}
