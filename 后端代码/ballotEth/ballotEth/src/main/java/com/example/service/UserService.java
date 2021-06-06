package com.example.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.entity.LoginInfo;
import com.example.entity.User;
import jnr.ffi.annotations.In;

import java.util.List;

public interface UserService extends IService<User> {
    void login(String publicAddress);
    boolean isExist(String name);
    void add(User user);
    User findOne(String condition);
    List<User> findUsers();
    void saveRoleChanges(String userid,List<String> roles);
    LoginInfo getLoginInfo(User user);
}
