package com.supernb.stock.mapper;

import com.supernb.stock.pojo.entity.SysUserRole;

/**
* @author chenzhihan
* @description 针对表【sys_user_role(用户角色表)】的数据库操作Mapper
* @createDate 2025-11-19 14:27:16
* @Entity com.supernb.stock.pojo.entity.SysUserRole
*/
public interface SysUserRoleMapper {

    int deleteByPrimaryKey(Long id);

    int insert(SysUserRole record);

    int insertSelective(SysUserRole record);

    SysUserRole selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SysUserRole record);

    int updateByPrimaryKey(SysUserRole record);

}
