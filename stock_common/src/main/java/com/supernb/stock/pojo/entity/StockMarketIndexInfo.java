package com.supernb.stock.pojo.entity;

import java.math.BigDecimal;
import java.util.Date;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 国内大盘数据详情表
 * @TableName stock_market_index_info
 */
@ApiModel(description = "国内大盘数据详情表")
@Data
public class StockMarketIndexInfo {
    /**
     * 主键字段（无业务意义）
     */
    @ApiModelProperty("主键字段（无业务意义）")
    private Long id;

    /**
     * 大盘编码
     */
    @ApiModelProperty("大盘编码")
    private String marketCode;

    /**
     * 指数名称
     */
    @ApiModelProperty("指数名称")
    private String marketName;

    /**
     * 前收盘点数
     */
    @ApiModelProperty("前收盘点数")
    private BigDecimal preClosePoint;

    /**
     * 开盘点数
     */
    @ApiModelProperty("开盘点数")
    private BigDecimal openPoint;

    /**
     * 当前点数
     */
    @ApiModelProperty("当前点数")
    private BigDecimal curPoint;

    /**
     * 最低点数
     */
    @ApiModelProperty("最低点数")
    private BigDecimal minPoint;

    /**
     * 最高点数
     */
    @ApiModelProperty("最高点数")
    private BigDecimal maxPoint;

    /**
     * 成交量(手)
     */
    @ApiModelProperty("成交量(手)")
    private Long tradeAmount;

    /**
     * 成交金额（元）
     */
    @ApiModelProperty("成交金额（元）")
    private BigDecimal tradeVolume;

    /**
     * 当前时间
     */
    @ApiModelProperty("当前时间")
    private Date curTime;
}