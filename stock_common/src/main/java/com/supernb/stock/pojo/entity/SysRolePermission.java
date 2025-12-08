package com.supernb.stock.pojo.entity;

import java.util.Date;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 角色权限表
 * @TableName sys_role_permission
 */
@ApiModel(description = "角色权限表")
@Data
public class SysRolePermission {
    /**
     * 主键
     */
    @ApiModelProperty("主键")
    private Long id;

    /**
     * 角色id
     */
    @ApiModelProperty("角色id")
    private Long roleId;

    /**
     * 菜单权限id
     */
    @ApiModelProperty("菜单权限id")
    private Long permissionId;

    /**
     * 创建时间
     */
    @ApiModelProperty("创建时间")
    private Date createTime;
}