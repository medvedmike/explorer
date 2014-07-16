package com.explorer.domain.fs;

import com.explorer.domain.SharedPath;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 * Created by Michael on 16.07.2014.
 */
public class SharedDirectory extends AbsoluteDirectory {

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
                    e.printStackTrace();//TODO придумать что-нибудь
                }
            }
        });
    }

    public SharedDirectory(String path) throws IOException {
        super(Paths.get(path));
    }
}
