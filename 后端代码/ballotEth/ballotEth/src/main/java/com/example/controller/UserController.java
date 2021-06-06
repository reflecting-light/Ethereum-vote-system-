package com.example.controller;

import com.example.entity.*;
import com.example.service.PermissionService;
import com.example.service.UserService;
import com.example.utils.SaltUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@RestController
@CrossOrigin
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private PermissionService permissionService;

//    @Autowired
//    private UserDao userDao;
    @GetMapping("login")
    public Result login(String publicAddress){
        Result result = new Result();

        try{

            userService.login(publicAddress);
            User user=userService.findOne(publicAddress);
            LoginInfo user1=userService.getLoginInfo(user);
            result.setSuccess(true);
            result.setMsg("登陆成功");
            result.setResult(user1);
            System.out.println("user = " + user);

        }catch (Exception e){
            e.printStackTrace();
            System.out.println("error occured during logining = " + e);
            result.setSuccess(false);
            result.setMsg("出错了。。。");
            result.setResult(null);
        }

        return result;
    }

    @PostMapping("loginByUsername")
    public  Result loginByUserName(@RequestBody User user){
        Result result = new Result();
        Subject subject = SecurityUtils.getSubject();
        try{
            subject.login(new UsernamePasswordToken(user.getUsername(),user.getPassword()));
            LoginInfo user1=userService.getLoginInfo(user);
            System.out.println("user = " + user1);
            System.out.println("登陆成功");
            result.setSuccess(true);
            result.setMsg("登陆成功");
            result.setResult(user1);
        }catch (UnknownAccountException e){
            e.printStackTrace();
            result.setSuccess(false);
            result.setMsg("用户名错误");
            System.out.println("用户名错误！");
        }catch (IncorrectCredentialsException e){
            e.printStackTrace();
            result.setSuccess(false);
            result.setMsg("密码错误");
            System.out.println("密码错误");
        }
        return result;
    }

    @PostMapping("register")
    public Result register(@RequestBody User user){
        Result result = new Result();

        String username = user.getUsername();
//        String password = user.getPassword();
//        username = HtmlUtils.htmlEscape(username);
//        user.setUsername(username);
        System.out.println("user = " + user);
        try{
            if(userService.isExist(username)){
                result.setSuccess(false);
                result.setMsg("该用户名已存在");
            }else {
                //        对明文密码进行MD5 +salt +hash 加密

//        1.生成随机盐
                String salt = SaltUtils.getSalt(8);

//        2.将生成的随机盐保存到数据库
                user.setSalt(salt);

                Md5Hash md5Hash = new Md5Hash(user.getPassword(),salt,1024);

//        3.将加密后的密码保存到数据库
                user.setPassword(md5Hash.toHex());
                userService.add(user);

                result.setSuccess(true);
                result.setMsg("注册成功");
            }
        }catch (Exception e){
            e.printStackTrace();
            result.setSuccess(false);
            result.setMsg("发生了点小意外。");
        }
        return result;
    }

    @GetMapping("getUsers")
    public Result getUserInfo(){
        Result result = new Result();
        try{
            List<User> users=userService.findUsers();

//            List<String> username=new ArrayList<>();
//            List<String> userAddress=new ArrayList<>();
//            users.forEach(user -> {
//                username.add(user.getUsername());
//                userAddress.add(user.getUserAddress());
//            });
//            List<String> roles =  roleService.findRoles();
//            List<String> permissions = permissionService.findPermissions();
//            result.setResult(new UsersInfo(username,userAddress,roles,permissions));
            result.setResult(users);
            result.setMsg("查询成功");
            result.setSuccess(true);
        }catch (Exception e){
            e.printStackTrace();
            result.setSuccess(false);
            result.setMsg("信息读取失败");
            result.setResult(null);
        }
        return result;
    }

    @PostMapping("addRole")
    public Result addUserRole(@RequestBody User user){
        Result result = new Result();
//        System.out.println("user = " + user);

        try{
            userService.saveRoleChanges(user.getId(),user.getRoles());
            result.setMsg("成功！");
            result.setSuccess(true);
        }catch (Exception e){
            e.printStackTrace();
            result.setSuccess(false);
            result.setMsg("出了点小错~");
        }

        return result;
    }
    @GetMapping("logout")
    public Result logout(){
        Result result = new Result();

        Subject subject = SecurityUtils.getSubject();
        try{
            subject.logout();
            result.setSuccess(true);
            result.setMsg("退出成功");
        }catch (Exception e){
            e.printStackTrace();
            result.setSuccess(false);
            result.setMsg("哟，出错了");
        }
        return result;
    }
//    @PostMapping("loginByName")
//    public Result loginByName(@RequestBody User user){
//        Result result = new Result();
//        Subject subject = SecurityUtils.getSubject();
//        UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(user.getUsername(),user.getPassword());
//
//        try{
//            User user1 = userService.findOne(user.getUsername());
//            subject.login(usernamePasswordToken);
//            return ResultFactory.buildSuccessResult()
//
//        }catch (Exception e){
//
//        }
//
//
//
//    }


}
