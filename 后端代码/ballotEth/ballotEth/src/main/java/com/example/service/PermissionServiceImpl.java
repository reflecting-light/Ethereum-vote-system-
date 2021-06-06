package com.example.service;

import com.example.dao.PermissionDao;
import com.example.entity.Permission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class PermissionServiceImpl implements PermissionService{
    @Autowired
    private PermissionDao permissionDao;
    @Override
    public List<String> getPermissionsByRoles(List<String> roles) {
        List<String> permissions = new ArrayList<>();
        roles.forEach(role ->{
            System.out.println("role = " + role);
            List<String> perms =permissionDao.getPermissionsByRoles(role);
//            if(perms!=['null']){
                permissions.addAll(perms) ;
//            }
            System.out.println("permissions = " + permissionDao.getPermissionsByRoles(role));
        });
        return permissions;
    }

    @Override
    public List<String> findPermissions() {
        return permissionDao.findPermissions();
    }
}
