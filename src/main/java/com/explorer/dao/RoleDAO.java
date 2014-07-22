package com.explorer.dao;

import com.explorer.domain.Role;

/**
 * Created by Michael on 10.07.2014.
 */
public interface RoleDAO {
    /**
     * Получегие роли по имени
     * @param name
     * @return
     */
    public Role getRole(String name);
}
