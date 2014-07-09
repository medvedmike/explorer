package com.explorer.dao;

import com.explorer.domain.User;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Michael on 08.07.2014.
 */
@Repository
public class UserDAOImpl implements UserDAO {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public User getUser(String login) {
        Query query = sessionFactory.getCurrentSession().createQuery("from User where username = :usr_login");
        query.setParameter("usr_login", login);
        List users = query.list();
        return users.isEmpty() ? null : (User) users.get(0);
    }

    @Override
    public void saveUser(User user) {
        sessionFactory.getCurrentSession().save(user);
    }
}
