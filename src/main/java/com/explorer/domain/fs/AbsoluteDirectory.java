package com.explorer.domain.fs;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Stream;

/**
 * Created by Michael on 14.07.2014.
 */
public class AbsoluteDirectory implements Directory {

    protected Path path;
    protected boolean root;
    protected List<FileInfo> children;
    protected List<Breadcrumb> breadcrumbs;

    protected AbsoluteDirectory() {}

    public AbsoluteDirectory(Path file) throws IOException {
        root = file == null;
        if (!root) {
            path = file;
            Stream<Path> list = Files.list(file);
            children = new ArrayList<>();
            list.forEach(new Consumer<Path>() {
                @Override
                public void accept(Path path) {
                    children.add(new AbsoluteFileInfo(path));
                }
            });

            int max = path.getNameCount();
            breadcrumbs = new ArrayList<>(max);
            Path p = path;
            String name;
            do {
                Path next = p.getParent();
                String pth = p.toString();
                name = next == null ? pth : p.getFileName().toString();
                breadcrumbs.add(new Breadcrumb(name, pth));
                p = next;
            } while (p != null);
            Collections.reverse(breadcrumbs);

        } else {
            path = null;
            Stream<File> list = Arrays.stream(File.listRoots());
            children = new ArrayList<>();
            list.forEach(new Consumer<File>() {
                @Override
                public void accept(File file) {
                    children.add(new AbsoluteFileInfo(file.toPath()));
                }
            });
        }
    }

    @Override
    public String getPath() {
        return path == null ? null : path.toString();
    }

    @Override
    public String getName() {
        return path.getParent() == null ? path.toString() : path.getFileName().toString();
    }

    @Override
    public String getParent() {
        if (path == null) return null;
        Path parent = path.getParent();
        return parent == null ? null : parent.toString();
    }

    @Override
    public FileInfo[] getChildren() {
        FileInfo[] res = new FileInfo[children.size()];
        return children.toArray(res);
    }

    @Override
    public Breadcrumb[] getBreadcrumbs() {
        if (breadcrumbs == null) return null;
        Breadcrumb[] res = new Breadcrumb[breadcrumbs.size()];
        return breadcrumbs.toArray(res);
    }

    @Override
    public boolean isRoot() {
        return root;
    }

    @Override
    public boolean isWritable() {
        return path != null && Files.isWritable(path);
    }
}
