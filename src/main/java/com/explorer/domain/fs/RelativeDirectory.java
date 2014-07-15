package com.explorer.domain.fs;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Stream;

/**
 * Created by Michael on 14.07.2014.
 */
public class RelativeDirectory implements Directory {

    private Path absolutePath;
    private Path path;
    private Path relate;
    private boolean root;
    private List<FileInfo> children;

    public static int subIndex(Path path, Path relate) {
        Path name = relate.getFileName();
        for (int i = 0; i < path.getNameCount(); i++) {
            if (path.getName(i).compareTo(name) == 0)
                return i + 1;
        }
        return path.getNameCount() - 1;
    }

    public RelativeDirectory(Path file, final Path relate) throws IOException {
        absolutePath = file;
        root = file.compareTo(relate) == 0;
        if (!root)
            path = file.subpath(subIndex(file, relate), file.getNameCount());
        this.relate = relate;
        Stream<Path> list = Files.list(file);
        children = new ArrayList<>();
        list.forEach(new Consumer<Path>() {
            @Override
            public void accept(Path path) {
                RelativeDirectory.this.children.add(new RelativeFileInfo(path, RelativeDirectory.this.path == null ?
                        "" : RelativeDirectory.this.path.toString())); //TODO only concat names
            }
        });
    }

    @Override
    public String getPath() {
        return path == null ? null : path.toString();
    }

    @Override
    public String getName() {
        return path.getFileName().toString();
    }

    @Override
    public String getParent() {
        if (/*path.startsWith(relate) && path.compareTo(relate) > 0*/path != null) {
            Path parent = path.getParent();
            if (parent != null)
                return parent.toString();
        }
        return null;//TODO maybe exception
    }

    @Override
    public FileInfo[] getChildren() {
        FileInfo[] res = new FileInfo[children.size()];
        return children.toArray(res);
    }

    @Override
    public Breadcrumb[] getBreadcrumbs() {
        return new Breadcrumb[0];
    }

    @Override
    public boolean isRoot() {
        return root;
    }

    @Override
    public boolean isWritable() {
        return Files.isWritable(absolutePath);
    }

    public String getAbsolutePath() {
        return absolutePath.toString();
    }
}
