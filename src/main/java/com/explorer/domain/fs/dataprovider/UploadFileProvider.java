package com.explorer.domain.fs.dataprovider;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Michael on 15.07.2014.
 * Интерфейс, аозволяющий сохранять файлы в файловой системе
 */
public interface UploadFileProvider {
    /**
     * Сохранение файла из полученного потока для чтения
     * @param stream
     * @throws IOException
     */
    void write(InputStream stream) throws IOException;
}
