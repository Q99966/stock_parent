package com.supernb.stock.pojo.entity;

import java.util.Date;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 主营业务表
 * @TableName stock_business
 */
@ApiModel(description = "主营业务表")
@Data
public class StockBusiness {
    /**
     *  股票编码
     */
    @ApiModelProperty("股票编码")
    private String stockCode;

    /**
     * 股票名称
     */
    @ApiModelProperty("股票名称")
    private String stockName;

    /**
     * 股票所属行业|板块标识
     */
    @ApiModelProperty("股票所属行业|板块标识")
    private String blockLabel;

    /**
     * 行业板块名称
     */
    @ApiModelProperty("行业板块名称")
    private String blockName;

    /**
     * 主营业务
     */
    @ApiModelProperty("主营业务")
    private String business;

    /**
     * 更新时间
     */
    @ApiModelProperty("更新时间")
    private Date updateTime;
}