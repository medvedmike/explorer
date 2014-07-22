package com.explorer.domain.fs.dataprovider;

import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by Michael on 15.07.2014.
 * Интерфейс, обеспечивающий чтение файла из файловой системы
 */
public interface DownloadFileProvider {
    /**
     * Копирование файла в поток
     * @param stream
     * @throws IOException
     */
    void copy(OutputStream stream) throws IOException;

    /**
     * Получение размера файла
     * @return
     * @throws IOException
     */
    long getSize() throws IOException;

    /**
     * Получение имени файла
     * @return
     */
    String getName();
}
