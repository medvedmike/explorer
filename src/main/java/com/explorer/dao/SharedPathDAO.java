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
    void savePath(SharedPath path);
}
