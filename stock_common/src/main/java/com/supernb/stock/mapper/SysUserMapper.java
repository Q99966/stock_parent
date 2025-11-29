package com.supernb.stock.mapper;

import com.supernb.stock.pojo.entity.SysUser;
import org.apache.ibatis.annotations.Param;

/**
* @author chenzhihan
* @description 针对表【sys_user(用户表)】的数据库操作Mapper
* @createDate 2025-11-19 14:27:16
* @Entity com.supernb.stock.pojo.entity.SysUser
*/
public interface SysUserMapper {

    SysUser findByUserName(@Param("name") String userName);

    int deleteByPrimaryKey(Long id);

    int insert(SysUser record);

    int insertSelective(SysUser record);

    SysUser selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SysUser record);

    int updateByPrimaryKey(SysUser record);

}
