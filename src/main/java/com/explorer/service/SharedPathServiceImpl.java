package com.explorer.service;

import com.explorer.dao.SharedPathDAO;
import com.explorer.dao.UserDAO;
import com.explorer.domain.SharedPath;
import com.explorer.domain.User;
import com.explorer.service.exceptions.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
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

    @Autowired
    private FileSystemService fileSystem;

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

    @Override
    @Transactional
    public void shareHomePath(String name, String targetUsername, String sharedPath) throws IOException, UserNotFoundException {
        sharePath(name, targetUsername, fileSystem.buildHomePath(sharedPath, name).toString());
    }

    @Override
    @Transactional
    public List<SharedPath> getPathsBySourceUsername(String username) {
        return sharedPathDAO.getPathsBySourceUsername(username);
    }

    @Override
    @Transactional
    public void deletePath(Integer id) {
        sharedPathDAO.deletePath(id);
    }

    @Override
    @Transactional
    public void deletePath(SharedPath path) {
        sharedPathDAO.deletePath(path);
    }
}
