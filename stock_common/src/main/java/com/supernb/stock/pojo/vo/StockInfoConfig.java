package com.supernb.stock.pojo.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

/**
 * 股票配置信息
 */
@ApiModel(description = "股票配置信息")
@ConfigurationProperties(prefix = "stock")
@Data
public class StockInfoConfig {
    /**
     * A股大盘ID集合
     */
    @ApiModelProperty("A股大盘ID集合")
    private List<String> inner;
    /**
     * 外盘ID集合
     */
    @ApiModelProperty("外盘ID集合")
    private List<String> outer;
    /**
     * 股票区间
     */
    private List<String> upDownRange;
    /**
     * 大盘参数获取url
     */
    @ApiModelProperty("大盘参数获取url")
    private String marketUrl;
    /**
     * 板块参数获取url
     */
    @ApiModelProperty("板块参数获取url")
    private String blockUrl;
}