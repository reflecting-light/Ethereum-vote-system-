package com.example.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.dao.PermissionDao;
import com.example.dao.RoleDao;
import com.example.dao.UserDao;
import com.example.dao.UserRoleDao;
import com.example.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
@Transactional
public class UserServiceImpl extends ServiceImpl<UserDao,User> implements UserService{
    @Autowired
    private UserDao userDao;

    @Autowired
    private RoleDao roleDao;

    @Autowired
    private PermissionDao permissionDao;

    @Autowired
    private PermissionService permissionService;

    @Autowired
    private UserRoleDao userRoleDao;
    @Override
    public void login(String publicAddress) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("userAddress",publicAddress);
        System.out.println("publicAddress = " + publicAddress);
        User user = userDao.selectOne(queryWrapper);
//        System.out.println("publicAddress user= " + user);
//        User user = new User();
//        user.setPublicAddress(publicAddress);
//        System.out.println("publicAddress = " + publicAddress);
//        System.out.println("nonce = " + user.getNonce());
        if(user.getId()==null){
            userDao.insert(user);
        }else{
            userDao.updateById(user);
        }

//        return user;
    }

    @Override
    public boolean isExist(String name) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("username",name);
        User user = userDao.selectOne(queryWrapper);
        if(user!=null){
            return true;
        }
        return false;
    }

    @Override
    public void add(User user) {
        userDao.insert(user);
    }

    @Override
    public User findOne(String condition) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("username",condition);
        User user =userDao.selectOne(queryWrapper);
        if(!ObjectUtils.isEmpty(user)){
            return user;
        }
        queryWrapper.clear();
        queryWrapper.like("userAddress",condition);
        System.out.println("condition = " + condition);
        user = userDao.selectOne(queryWrapper);
//        System.out.println("condition user= " + user);
        return user;
    }

    @Override
    public List<User> findUsers() {
        List<User> users = userDao.selectList(null);
        users.forEach(user -> {
            user.setRoles(roleDao.getRolesByUsername(user.getUsername()));
            user.setRoleList(roleDao.findRoles());
            user.setPermissions(permissionService.getPermissionsByRoles(user.getRoles()));
        });
        return users;
    }

    @Override
    public void saveRoleChanges(String userid, List<String> roles) {
        QueryWrapper<UserRole> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("userid",userid);
        userRoleDao.delete(queryWrapper);

        QueryWrapper<Role> queryWrapper1 = new QueryWrapper<>();
        roles.forEach(role->{
            queryWrapper1.clear();
            queryWrapper1.like("name",role);

            UserRole userRole = new UserRole();
            userRole.setRoleid(roleDao.selectOne(queryWrapper1).getId());
            userRole.setUserid(userid);

            userRoleDao.insert(userRole);
        });

    }

    @Override
    public LoginInfo getLoginInfo(User user) {
        List<String> roles= roleDao.getRolesByUsername(user.getUsername());


        List<String> permissions = permissionService.getPermissionsByRoles(roles);
//        System.out.println("permissions = " + permissions);


        System.out.println("permissions = " + permissions);
        return new LoginInfo(user.getUsername(),user.getUserAddress(),user.getNonce(),roles,permissions);
    }
}
