package com.explorer.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.file.attribute.PosixFilePermission;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Michael on 29.07.2014.
 */
@Component
public class PermissionManager {

    private @Value("${defaultFilePermissions}") String defaultFilePermissions;
    private @Value("${defaultDirectoryPermissions}") String defaultDirectoryPermissions;

    private void processOwner(Set<PosixFilePermission> perms, char symbol) {
        switch (symbol) {
            case '0':
                break;
            case '1':
                perms.add(PosixFilePermission.OWNER_EXECUTE);
                break;
            case '2':
                perms.add(PosixFilePermission.OWNER_WRITE);
                break;
            case '3':
                perms.add(PosixFilePermission.OWNER_EXECUTE);
                perms.add(PosixFilePermission.OWNER_WRITE);
                break;
            case '4':
                perms.add(PosixFilePermission.OWNER_READ);
                break;
            case '5':
                perms.add(PosixFilePermission.OWNER_READ);
                perms.add(PosixFilePermission.OWNER_EXECUTE);
                break;
            case '6':
                perms.add(PosixFilePermission.OWNER_READ);
                perms.add(PosixFilePermission.OWNER_WRITE);
                break;
            case '7':
                perms.add(PosixFilePermission.OWNER_READ);
                perms.add(PosixFilePermission.OWNER_WRITE);
                perms.add(PosixFilePermission.OWNER_EXECUTE);
                break;
        }
    }

    private void processGroup(Set<PosixFilePermission> perms, char symbol) {
        switch (symbol) {
            case '0':
                break;
            case '1':
                perms.add(PosixFilePermission.GROUP_EXECUTE);
                break;
            case '2':
                perms.add(PosixFilePermission.GROUP_WRITE);
                break;
            case '3':
                perms.add(PosixFilePermission.GROUP_EXECUTE);
                perms.add(PosixFilePermission.GROUP_WRITE);
                break;
            case '4':
                perms.add(PosixFilePermission.GROUP_READ);
                break;
            case '5':
                perms.add(PosixFilePermission.GROUP_READ);
                perms.add(PosixFilePermission.GROUP_EXECUTE);
                break;
            case '6':
                perms.add(PosixFilePermission.GROUP_READ);
                perms.add(PosixFilePermission.GROUP_WRITE);
                break;
            case '7':
                perms.add(PosixFilePermission.GROUP_READ);
                perms.add(PosixFilePermission.GROUP_WRITE);
                perms.add(PosixFilePermission.GROUP_EXECUTE);
                break;
        }
    }

    private void processOthers(Set<PosixFilePermission> perms, char symbol) {
        switch (symbol) {
            case '0':
                break;
            case '1':
                perms.add(PosixFilePermission.OTHERS_EXECUTE);
                break;
            case '2':
                perms.add(PosixFilePermission.OTHERS_WRITE);
                break;
            case '3':
                perms.add(PosixFilePermission.OTHERS_EXECUTE);
                perms.add(PosixFilePermission.OTHERS_WRITE);
                break;
            case '4':
                perms.add(PosixFilePermission.OTHERS_READ);
                break;
            case '5':
                perms.add(PosixFilePermission.OTHERS_READ);
                perms.add(PosixFilePermission.OTHERS_EXECUTE);
                break;
            case '6':
                perms.add(PosixFilePermission.OTHERS_READ);
                perms.add(PosixFilePermission.OTHERS_WRITE);
                break;
            case '7':
                perms.add(PosixFilePermission.OTHERS_READ);
                perms.add(PosixFilePermission.OTHERS_WRITE);
                perms.add(PosixFilePermission.OTHERS_EXECUTE);
                break;
        }
    }

    private Set<PosixFilePermission> processPerms(String perms) {
        Set<PosixFilePermission> result = new HashSet<>();
        processOwner(result, perms.charAt(0));
        processGroup(result, perms.charAt(1));
        processOthers(result, perms.charAt(2));
        return result;
    }

    public Set<PosixFilePermission> getDefaultFile() {
        return processPerms(defaultFilePermissions);
    }

    public Set<PosixFilePermission> getDefaultDir() {
        return processPerms(defaultDirectoryPermissions);
    }
}
