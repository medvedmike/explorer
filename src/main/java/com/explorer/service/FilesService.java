package com.explorer.service;

import com.explorer.domain.fs.dataprovider.DownloadFileProvider;
import com.explorer.domain.fs.dataprovider.UploadFileProvider;

import java.io.IOException;

/**
 * Created by Michael on 24.07.2014.
 * Сервис который предоставляет доступ к объектам, отвечающим за скачвание и загрузку файлов
 */
public interface FilesService {
    public DownloadFileProvider getDownloadGlobalFileProvider(String path) throws IOException;
    public DownloadFileProvider getDownloadHomeFileProvider(String path, String username) throws IOException;
    public DownloadFileProvider getDownloadSharedFileProvider(String path, String username) throws IOException;

    public UploadFileProvider getUploadGlobalFileProvider(String path, String fileName) throws IOException;
    public UploadFileProvider getUploadHomeFileProvider(String path, String username, String fileName) throws IOException;
    public UploadFileProvider getUploadSharedFileProvider(String path, String username, String fileName) throws IOException;

}
