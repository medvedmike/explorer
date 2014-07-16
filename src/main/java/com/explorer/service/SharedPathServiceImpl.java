package com.explorer.service;

import com.explorer.dao.SharedPathDAO;
import com.explorer.dao.UserDAO;
import com.explorer.domain.SharedPath;
import com.explorer.domain.User;
import com.explorer.service.exceptions.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Michael on 16.07.2014.
 */
@Service
public class SharedPathServiceImpl implements SharedPathService{

    @Autowired
    private SharedPathDAO sharedPathDAO;

    @Autowired
    private UserDAO userDAO;

    @Override
    @Transactional
    public List<SharedPath> getPathsByTargetUsername(String username) {
        return sharedPathDAO.getPathsByTargetUsername(username);
    }

    @Override
    @Transactional
    public void sharePath(String sourceUsername, String targetUsername, String sharedPath) throws UserNotFoundException {
        User source = userDAO.getUser(sourceUsername);
        User dest = userDAO.getUser(targetUsername);
        if (dest == null)
            throw new UserNotFoundException();
        SharedPath path = new SharedPath(); //TODO check rights
        path.setSourceUser(source);
        path.setTargetUser(dest);
        path.setPath(sharedPath);
        sharedPathDAO.savePath(path);
    }
}
