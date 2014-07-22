package com.explorer.dao;

import com.explorer.domain.User;

/**
 * Created by Michael on 08.07.2014.
 */
public interface UserDAO {
    /**
     * Получение пользователя по имени
     * @param login
     * @return
     */
    public User getUser(String login);

    /**
     * Сохранение пользователя в базе
     * @param user
     */
    public void saveUser(User user);
}
