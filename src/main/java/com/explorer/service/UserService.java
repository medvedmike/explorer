package com.explorer.service;

import com.explorer.domain.User;

/**
 * Created by Michael on 08.07.2014.
 */
public interface UserService {
    public User getUser(String login);
    public void saveUser(User user);
}
