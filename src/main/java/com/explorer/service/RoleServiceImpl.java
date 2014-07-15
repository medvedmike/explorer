package com.explorer.service;

import com.explorer.dao.RoleDAO;
import com.explorer.domain.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Michael on 10.07.2014.
 */
@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleDAO roleDAO;

    @Override
    @Transactional
    public Role getRole(String name) {
        return roleDAO.getRole(name);
    }
}
