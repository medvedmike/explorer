package com.explorer.domain.fs;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Stream;

/**
 * Created by Michael on 14.07.2014.
 * Информация о относительной дирректории(относительно папки пользователя)
 */
public class RelativeDirectory implements Directory {

    private Path absolutePath;
    private Path path;
    private Path relate;
    private boolean root;
    private List<FileInfo> children;
    protected List<PathPart> breadcrumbs;

    public RelativeDirectory(Path file, final Path relate) throws IOException {
        System.out.println("absolutePath: " + file.toString() + " --relate: " + relate.toString());
        absolutePath = file;
        root = file.compareTo(relate) == 0;
        if (!root) {
            path = file.subpath(relate.getNameCount(), file.getNameCount());
            System.out.println("path: " + path.toString());
        }
        this.relate = relate;
        Stream<Path> list = Files.list(file);
        children = new ArrayList<>();
        list.forEach(path1 -> RelativeDirectory.this.children.add(new RelativeFileInfo(path1, RelativeDirectory.this.path == null ?
                "" : RelativeDirectory.this.path.toString())));

        if (path != null) {
            int max = path.getNameCount();
            breadcrumbs = new ArrayList<>(max);
            Path p = path;
            String name;
            do {
                Path next = p.getParent();
                String pth = p.toString();
                name = next == null ? pth : p.getFileName().toString();
                breadcrumbs.add(new PathPart(name, pth));
                p = next;
            } while (p != null);
            breadcrumbs.add(new PathPart(relate.getFileName().toString(), ""));
            Collections.reverse(breadcrumbs);
        }
    }

    @Override
    public String getPath() {
        return path == null ? null : path.toString();
    }

    @Override
    public String getName() {
        return path == null ? relate.getFileName().toString() : path.getFileName().toString();
    }

    @Override
    public String getParent() {
        if (path != null) {
            Path parent = path.getParent();
            if (parent != null)
                return parent.toString();
        }
        return null;
    }

    @Override
    public FileInfo[] getChildren() {
        FileInfo[] res = new FileInfo[children.size()];
        return children.toArray(res);
    }

    @Override
    public PathPart[] getBreadcrumbs() {
        if (breadcrumbs == null) return null;
        PathPart[] res = new PathPart[breadcrumbs.size()];
        return breadcrumbs.toArray(res);
    }

    @Override
    public boolean isRoot() {
        return false;
    }

    @Override
    public boolean isWritable() {
        return Files.isWritable(absolutePath);
    }

    public String getAbsolutePath() {
        return absolutePath.toString();
    }
}
