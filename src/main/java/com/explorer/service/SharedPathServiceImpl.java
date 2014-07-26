package com.explorer.service;

import com.explorer.annotations.GlobalAccessPointcut;
import com.explorer.annotations.HomeAccessPointcut;
import com.explorer.annotations.SharedAccessPointcut;
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

    @GlobalAccessPointcut
    private void sharePath(String sharedPath, String sourceUsername, String targetUsername) throws UserNotFoundException {
        User source = userDAO.getUser(sourceUsername);
        User dest = userDAO.getUser(targetUsername);
        if (dest == null)
            throw new UserNotFoundException();
        SharedPath path = new SharedPath();
        path.setSourceUser(source);
        path.setTargetUser(dest);
        path.setPath(sharedPath);
        sharedPathDAO.savePath(path);
    }

    @Override
    @Transactional
    @GlobalAccessPointcut
    public void shareGlobalPath(String sharedPath, String name, String targetUsername) throws UserNotFoundException {
        sharePath(sharedPath, name, targetUsername);
    }

    @Override
    @Transactional
    @SharedAccessPointcut
    public void shareSharedPath(String sharedPath, String sourceUsername, String targetUsername) throws UserNotFoundException, IOException {
        sharePath(sharedPath, sourceUsername, targetUsername);
    }

    @Override
    @Transactional
    @HomeAccessPointcut
    public void shareHomePath(String sharedPath, String name, String targetUsername) throws IOException, UserNotFoundException {
        sharePath(fileSystem.buildHomePath(sharedPath, name).toString(), name, targetUsername);
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

    @Override
    @Transactional
    public void deleteByPathValue(String path) {
        sharedPathDAO.deleteByPathValue(path);
    }
}
