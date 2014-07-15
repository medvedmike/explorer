package com.explorer.service;

import com.explorer.dao.UserDAO;
import com.explorer.domain.Role;
import com.explorer.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Michael on 08.07.2014.
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private RoleService roleService;

    @Autowired
    private FileSystemService fileSystem;

    @Override
    @Transactional
    public User getUser(String login) {
        return userDAO.getUser(login);
    }

    @Override
    @Transactional
    public void saveUser(User user) {
        Md5PasswordEncoder encoder = new Md5PasswordEncoder();
        user.setPassword(encoder.encodePassword(user.getPassword(), user.getUsername()));

        Role userRole = roleService.getRole("ROLE_USER");
        List<Role> roles = new ArrayList<>();
        roles.add(userRole);
        user.setAuthority(roles);

        File dir = fileSystem.createUserDirectory(user.getUsername());
        user.setHome(dir.getAbsolutePath());
        userDAO.saveUser(user);
    }
}
