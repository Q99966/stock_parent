package com.supernb.stock.mapper;

import com.supernb.stock.pojo.domain.Stock4MinuteDomain;
import com.supernb.stock.pojo.domain.StockUpdownDomain;
import com.supernb.stock.pojo.entity.StockRtInfo;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
* @author chenzhihan
* @description 针对表【stock_rt_info(个股详情信息表)】的数据库操作Mapper
* @createDate 2025-11-19 14:27:16
* @Entity com.supernb.stock.pojo.entity.StockRtInfo
*/
public interface StockRtInfoMapper {

    int deleteByPrimaryKey(Long id);

    int insert(StockRtInfo record);

    int insertSelective(StockRtInfo record);

    StockRtInfo selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(StockRtInfo record);

    int updateByPrimaryKey(StockRtInfo record);

    /**
     * 查询指定时间点下的股票数据集合
     * @param curDate 当前时间
     * @return
     */
    List<StockUpdownDomain> getStockInfoByTime(@Param("curDate") Date curDate);

    /**
     * 统计最新交易日下股票每分钟涨跌停的数量
     * @param openTime 开盘时间
     * @param curTime 当前时间
     * @param flag 1-涨停 0-跌停
     * @return
     */
    List<Map> getStockUpdownCount(@Param("openTime") Date openTime, @Param("curTime") Date curTime, @Param("flag") int flag);

    /**
     * 获取涨幅榜数据，取前4条
     */
    List<StockUpdownDomain> getStockLimitInfoByTime(@Param("curTime") Date curTime);

    /**
     * 统计指定时间点下股票在个涨跌区间的数量
     * @param curDate
     * @return
     */
    List<Map> getStockUpDownSectionByTime(@Param("dateTime") Date curDate);

    /**
     * 根据时间范围查询指定股票的交易流水
     * @param code 股票code
     * @param startTime 起始时间
     * @param endTime 终止时间
     * @return
     */
    List<Stock4MinuteDomain> getStockInfoByCodeAndDate(@Param("code") String code, @Param("startTime") Date startTime, @Param("endTime") Date endTime);
}
