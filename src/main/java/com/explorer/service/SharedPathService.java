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
    void shareGlobalPath(String sharedPath, String sourceUsername, String targetUsername) throws UserNotFoundException;
    void shareSharedPath(String sharedPath, String sourceUsername, String targetUsername) throws UserNotFoundException, IOException;
    void shareHomePath(String sharedPath, String name, String targetUsername) throws IOException, UserNotFoundException;
    public List<SharedPath> getPathsBySourceUsername(String username);
    public void deletePath(Integer id);
    public void deletePath(SharedPath path);

}
