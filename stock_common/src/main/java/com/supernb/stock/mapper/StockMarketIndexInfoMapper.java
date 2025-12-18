package com.supernb.stock.mapper;

import com.supernb.stock.pojo.domain.InnerMarketDomain;
import com.supernb.stock.pojo.entity.StockMarketIndexInfo;
import org.apache.ibatis.annotations.Param;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
* @author chenzhihan
* @description 针对表【stock_market_index_info(国内大盘数据详情表)】的数据库操作Mapper
* @createDate 2025-11-19 14:27:16
* @Entity com.supernb.stock.pojo.entity.StockMarketIndexInfo
*/
public interface StockMarketIndexInfoMapper {

    int deleteByPrimaryKey(Long id);

    int insert(StockMarketIndexInfo record);

    int insertSelective(StockMarketIndexInfo record);

    StockMarketIndexInfo selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(StockMarketIndexInfo record);

    int updateByPrimaryKey(StockMarketIndexInfo record);

    /**
     * 根据指定时间点来查询大盘数据
     * @param curDate 指定时间点
     * @param marketCodes 大盘编码集合
     * @return
     */
    List<InnerMarketDomain> getMarketInfo(@Param("curDate") Date curDate, @Param("marketCodes") List<String> marketCodes);

    /**
     * 统计指定时间内 指定大盘在每分钟内的成交量
     * @param markedIds 大盘编码集合
     * @param startTime4T 开盘时间
     * @param endTime4T 截止时间
     * @return
     */
    List<Map> getSumAmtInfo(@Param("marketCodes") List<String> markedIds, @Param("startTime4T") Date startTime4T, @Param("endTime4T") Date endTime4T);

    /**
     * 批量插入大盘数据
     * @param list 大盘实体对象集合
     * @return
     */
    int insertBatch(@Param("infos") ArrayList<StockMarketIndexInfo> list);
}
