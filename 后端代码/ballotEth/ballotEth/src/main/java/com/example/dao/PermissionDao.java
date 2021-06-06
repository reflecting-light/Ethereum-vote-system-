package com.example.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.entity.Permission;
import com.example.entity.Role;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface PermissionDao extends BaseMapper<Permission> {

//    @Select("select u.*, r.* from t_user u left join t_user_role ur on u.id=ur.userid left join t_role r on ur.roleid=r.id where u.username = #{username}" )
    @Select("select p.url from t_role r left join t_role_perms rp on r.id=rp.roleid left join t_perms p on rp.permid=p.id where r.name = #{role}")
    List<String> getPermissionsByRoles(String role);
    @Select("select p.name from t_perms p")
    List<String> findPermissions();
}
