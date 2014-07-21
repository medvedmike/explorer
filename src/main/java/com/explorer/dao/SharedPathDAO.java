package com.explorer.dao;

import com.explorer.domain.SharedPath;

import java.util.List;

/**
 * Created by Michael on 16.07.2014.
 */
public interface SharedPathDAO {

    /**
     * Получение списка дирректорий, доступных пользователю
     * @param username имя целевого пользователя
     * @return список доступных путей
     */
    public List<SharedPath> getPathsByTargetUsername(String username);

    /**
     * Получение списка путей которые расшарил пользователь
     * @param username имя пользователя
     * @return список путей
     */
    public List<SharedPath> getPathsBySourceUsername(String username);

    /**
     * Сохранение пути
     * @param path путь
     */
    public void savePath(SharedPath path);

    /**
     * Удаление пути по идентификатору
     * @param id идентификатор пути
     */
    public void deletePath(Integer id);

    /**
     * Удаление пути
     * @param path объект, представляющий данные, которые нужно стереть
     */
    public void deletePath(SharedPath path);
}
