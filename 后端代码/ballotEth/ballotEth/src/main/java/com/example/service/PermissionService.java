package com.example.service;

import com.example.entity.Permission;

import java.util.List;

public interface PermissionService {
    List<String> getPermissionsByRoles(List<String> roles);
    List<String> findPermissions();
}
