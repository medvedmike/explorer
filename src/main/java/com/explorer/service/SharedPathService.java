package com.explorer.service;

import com.explorer.domain.SharedPath;
import com.explorer.service.exceptions.UserNotFoundException;

import java.io.IOException;
import java.util.List;

/**
 * Created by Michael on 16.07.2014.
 */
public interface SharedPathService {
    public List<SharedPath> getPathsByTargetUsername(String username);
    void sharePath(String sourceUsername, String targetUsername, String sharedPath) throws UserNotFoundException; //TODO проверять права при расшаривании
    void shareHomePath(String name, String targetUsername, String sharedPath) throws IOException, UserNotFoundException;
    public List<SharedPath> getPathsBySourceUsername(String username);
    public void deletePath(Integer id);
    public void deletePath(SharedPath path);

}
