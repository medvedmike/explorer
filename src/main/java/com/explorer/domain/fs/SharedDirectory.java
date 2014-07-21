package com.explorer.domain.fs;

import com.explorer.domain.SharedPath;
import com.explorer.domain.fs.dataprovider.exceptions.InternalServerErrorException;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;

/**
 * Created by Michael on 16.07.2014.
 */
public class SharedDirectory extends AbsoluteDirectory {

    private Path parentPath;

    public SharedDirectory(List<SharedPath> sharedPaths) {
        path = null;
        root = true;
        children = new ArrayList<>();
        sharedPaths.forEach(new Consumer<SharedPath>() {
            @Override
            public void accept(SharedPath sharedPath) {
            try {
                children.add(new AbsoluteFileInfo(Paths.get(sharedPath.getPath()).toRealPath()));
            } catch (IOException e) {
                throw new InternalServerErrorException(e.getMessage());
            }
            }
        });
    }

    public SharedDirectory(Path parentPath, String path) throws IOException {
        super(Paths.get(path));
        this.parentPath = parentPath;

        String start = parentPath.getParent() != null ? parentPath.getFileName().toString() : parentPath.toString();

        if (this.path != null) {
            int max = this.path.getNameCount();
            breadcrumbs = new ArrayList<>(max);
            Path p = this.path;
            String name;
            do {
                Path next = p.getParent();
                String pth = p.toString();
                name = next == null ? pth : p.getFileName().toString();
                breadcrumbs.add(new Breadcrumb(name, pth));
                p = next;
            } while (p != null && !name.equals(start));
            breadcrumbs.add(new Breadcrumb("shared", ""));
            Collections.reverse(breadcrumbs);
        }
    }

    @Override
    public String getParent() {
        if (path == null) return null;
        if (path.compareTo(parentPath) <= 0)
            return "";
        return super.getParent();
    }
}
