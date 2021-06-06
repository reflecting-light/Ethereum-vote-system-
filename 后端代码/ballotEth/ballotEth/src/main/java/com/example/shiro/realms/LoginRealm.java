package com.example.shiro.realms;

import com.example.dao.RoleDao;
import com.example.entity.Role;
import com.example.entity.User;
import com.example.service.UserService;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.List;

public class LoginRealm extends AuthorizingRealm {
    @Resource
    private UserService userService;

//因为本系统采用前后端分离的形式，（即，无法在页面中使用shiro标签进行权限控制），所以，此函数根本不起作用
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
//        获取身份信息
        String principal= (String) principalCollection.getPrimaryPrincipal();
//        float f = (float) 20.1;
//        Double d =100;
//        System.out.println("principalCollection = " + principalCollection);
//        根据主身份信息获取其角色和权限信息
        if("Jessica".equals(principal)){
            SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
            simpleAuthorizationInfo.addRole("admin");
            return simpleAuthorizationInfo;
        }



        return null;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
//       1. 在token中获取用户名
        String principal = (String) authenticationToken.getPrincipal();

//        2.根据用户身份查询数据库
//        获取工厂中的UserService对象
        User user= userService.findOne(principal);
        if(!ObjectUtils.isEmpty(user)){
            return new SimpleAuthenticationInfo(user.getUsername(),user.getPassword(), ByteSource.Util.bytes(user.getSalt()),this.getName());
        }

        return null;
    }
}
