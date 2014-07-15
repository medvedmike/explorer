package com.explorer.dao;

import com.explorer.domain.Role;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Michael on 10.07.2014.
 */
@Repository
public class RoleDAOImpl implements RoleDAO{

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public Role getRole(String name) {
        Query query = sessionFactory.getCurrentSession().createQuery("from Role where role = :role");
        query.setParameter("role", name);
        List roles = query.list();
        return roles.isEmpty() ? null : (Role) roles.get(0);
    }
}
