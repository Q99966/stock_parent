package com.supernb.stock.pojo.entity;

import java.util.Date;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 角色表
 * @TableName sys_role
 */
@ApiModel(description = "角色表")
@Data
public class SysRole {
    /**
     * 主键
     */
    @ApiModelProperty("主键")
    private Long id;

    /**
     * 角色名称
     */
    @ApiModelProperty("角色名称")
    private String name;

    /**
     * 描述
     */
    @ApiModelProperty("描述")
    private String description;

    /**
     * 状态(1:正常0:弃用)
     */
    @ApiModelProperty("状态(1:正常0:弃用)")
    private Integer status;

    /**
     * 创建时间
     */
    @ApiModelProperty("创建时间")
    private Date createTime;

    /**
     * 更新时间
     */
    @ApiModelProperty("更新时间")
    private Date updateTime;

    /**
     * 是否删除(1未删除；0已删除)
     */
    @ApiModelProperty("是否删除(1未删除；0已删除)")
    private Integer deleted;
}