package com.explorer.service;

import com.explorer.annotations.GlobalAccessPointcut;
import com.explorer.annotations.HomeAccessPointcut;
import com.explorer.annotations.SharedAccessPointcut;
import com.explorer.domain.fs.dataprovider.DownloadAbsoluteFileProvider;
import com.explorer.domain.fs.dataprovider.DownloadFileProvider;
import com.explorer.domain.fs.dataprovider.UploadAbsoluteFileProvider;
import com.explorer.domain.fs.dataprovider.UploadFileProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * Created by Michael on 24.07.2014.
 */
@Service
public class FilesServiceImpl implements FilesService{

    @Autowired
    private FileSystemService fileSystem;

    private DownloadFileProvider getDownloadAbsoluteFileProvider(String path) throws IOException {
        return new DownloadAbsoluteFileProvider(path);
    }

    private UploadFileProvider getUploadAbsoluteFileProvider(String path, String name) throws IOException {
        return new UploadAbsoluteFileProvider(path, name);
    }

    @Override
    @GlobalAccessPointcut
    public DownloadFileProvider getDownloadGlobalFileProvider(String path) throws IOException {
        return getDownloadAbsoluteFileProvider(path);
    }

    @Override
    @HomeAccessPointcut
    public DownloadFileProvider getDownloadHomeFileProvider(String path, String username) throws IOException {
        return getDownloadAbsoluteFileProvider(fileSystem.buildHomePath(path, username).toString());
    }

    @Override
    @SharedAccessPointcut
    public DownloadFileProvider getDownloadSharedFileProvider(String path, String username) throws IOException {
        return getDownloadAbsoluteFileProvider(path);
    }

    @Override
    @GlobalAccessPointcut
    public UploadFileProvider getUploadGlobalFileProvider(String path, String fileName) throws IOException {
        return getUploadAbsoluteFileProvider(path, fileName);
    }

    @Override
    @HomeAccessPointcut
    public UploadFileProvider getUploadHomeFileProvider(String path, String username, String fileName) throws IOException {
        return getUploadAbsoluteFileProvider(fileSystem.buildHomePath(path, username).toString(), fileName);
    }

    @Override
    @SharedAccessPointcut
    public UploadFileProvider getUploadSharedFileProvider(String path, String username, String fileName) throws IOException {
        return getUploadAbsoluteFileProvider(path, fileName);
    }
}
