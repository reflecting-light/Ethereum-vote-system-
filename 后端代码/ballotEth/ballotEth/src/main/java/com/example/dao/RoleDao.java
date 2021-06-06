package com.example.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.entity.Role;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface RoleDao extends BaseMapper<Role> {

    @Select("select r.name from t_user u left join t_user_role ur on u.id=ur.userid left join t_role r on ur.roleid=r.id where u.username = #{username}" )
    List<String> getRolesByUsername(String name);

    @Select("select r.name from t_role r")
    List<String> findRoles();

}
