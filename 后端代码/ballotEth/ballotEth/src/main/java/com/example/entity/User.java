package com.example.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.util.List;

import static java.lang.Math.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Accessors(chain = true)
@TableName("t_user")
public class User {
    @TableId(type = IdType.AUTO)
    private String id;
    private Integer nonce =(Integer)(int)(random()*1000000);
    @TableField("userAddress")
    private String userAddress;
    private String username;
    private String password;
    private String salt;

    //    定义一个角色集合
    @TableField(exist = false)
    private List<String> roles;

    //    定义一个权限集合
    @TableField(exist = false)
    private List<String> permissions;

    @TableField(exist = false)
    private List<String> roleList;

}
