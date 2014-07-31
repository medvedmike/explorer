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
        if (symbol == '1' || symbol == '3' || symbol == '5' || symbol == '7')
            perms.add(PosixFilePermission.OWNER_EXECUTE);
        if (symbol == '2' || symbol == '3' || symbol == '6' || symbol == '7')
            perms.add(PosixFilePermission.OWNER_WRITE);
        if (symbol == '4' || symbol == '5' || symbol == '6' || symbol == '7')
            perms.add(PosixFilePermission.OWNER_READ);
    }

    private void processGroup(Set<PosixFilePermission> perms, char symbol) {
        if (symbol == '1' || symbol == '3' || symbol == '5' || symbol == '7')
            perms.add(PosixFilePermission.GROUP_EXECUTE);
        if (symbol == '2' || symbol == '3' || symbol == '6' || symbol == '7')
            perms.add(PosixFilePermission.GROUP_WRITE);
        if (symbol == '4' || symbol == '5' || symbol == '6' || symbol == '7')
            perms.add(PosixFilePermission.GROUP_READ);
    }

    private void processOthers(Set<PosixFilePermission> perms, char symbol) {
        if (symbol == '1' || symbol == '3' || symbol == '5' || symbol == '7')
            perms.add(PosixFilePermission.OTHERS_EXECUTE);
        if (symbol == '2' || symbol == '3' || symbol == '6' || symbol == '7')
            perms.add(PosixFilePermission.OTHERS_WRITE);
        if (symbol == '4' || symbol == '5' || symbol == '6' || symbol == '7')
            perms.add(PosixFilePermission.OTHERS_READ);
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
