package com.supernb.stock.mapper;

import com.supernb.stock.pojo.entity.StockBusiness;

import java.util.List;

/**
* @author chenzhihan
* @description 针对表【stock_business(主营业务表)】的数据库操作Mapper
* @createDate 2025-11-19 14:27:16
* @Entity com.supernb.stock.pojo.entity.StockBusiness
*/
public interface StockBusinessMapper {

    int deleteByPrimaryKey(Long id);

    int insert(StockBusiness record);

    int insertSelective(StockBusiness record);

    StockBusiness selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(StockBusiness record);

    int updateByPrimaryKey(StockBusiness record);

    /**
     * 获取所有股票代码
     * @return
     */
    List<String> getAllStockCodes();
}
