package com.supernb.stock.pojo.entity;

import java.util.Date;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 用户角色表
 * @TableName sys_user_role
 */
@ApiModel(description = "用户角色表")
@Data
public class SysUserRole {
    /**
     * 主键
     */
    @ApiModelProperty("主键")
    private Long id;

    /**
     * 用户id
     */
    @ApiModelProperty("用户id")
    private Long userId;

    /**
     * 角色id
     */
    @ApiModelProperty("角色id")
    private Long roleId;

    /**
     * 创建时间
     */
    @ApiModelProperty("创建时间")
    private Date createTime;
}