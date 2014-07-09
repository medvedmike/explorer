package com.explorer.dao;

import com.explorer.domain.User;

/**
 * Created by Michael on 08.07.2014.
 */
public interface UserDAO {
    public User getUser(String login);
    public void saveUser(User user);
}
