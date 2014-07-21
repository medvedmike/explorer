package com.explorer.dao;

import com.explorer.domain.SharedPath;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Michael on 16.07.2014.
 */
@Repository
public class SharedPathDAOImpl implements SharedPathDAO {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    @SuppressWarnings("unchecked")
    public List<SharedPath> getPathsByTargetUsername(String username) {
        Query query = sessionFactory.getCurrentSession().createQuery("from SharedPath where targetUser.id = " +
                "(select id from User where username = :username)");
        query.setParameter("username", username);
        return query.list();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<SharedPath> getPathsBySourceUsername(String username) {
        Query query = sessionFactory.getCurrentSession().createQuery("from SharedPath where sourceUser.id = " +
                "(select id from User where username = :username)");
        query.setParameter("username", username);
        return query.list();
    }

    @Override
    public void savePath(SharedPath path) {
        sessionFactory.getCurrentSession().save(path);
    }

    @Override
    public void deletePath(Integer id) {
        deletePath((SharedPath)sessionFactory.getCurrentSession().load(SharedPath.class, id));
    }

    @Override
    public void deletePath(SharedPath path) {
        if (path != null)
            sessionFactory.getCurrentSession().delete(path);
    }
}
