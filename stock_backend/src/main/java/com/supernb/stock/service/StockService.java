package com.supernb.stock.service;

import com.supernb.stock.domain.vo.resp.PageResult;
import com.supernb.stock.domain.vo.resp.R;
import com.supernb.stock.pojo.domain.InnerMarketDomain;
import com.supernb.stock.pojo.domain.StockBlockDomain;
import com.supernb.stock.pojo.domain.StockUpdownDomain;
import com.supernb.stock.pojo.entity.StockBusiness;

import java.util.List;
import java.util.Map;

public interface StockService {
    List<StockBusiness> getAllStockBusiness();

    /**
     * 获取国内大盘最新数据
     * @return
     */

    R<List<InnerMarketDomain>> getInnerMarketInfo();

    /**
     * 获取沪深两市板块最新数据，以交易总金额降序查询，取前10条数据
     */
    R<List<StockBlockDomain>> getSectorAllLimit();

    /**
     * 分页查询股票最新数据，并按照涨幅排序查询
     * @param page 当前页
     * @param pageSize 每页大小
     * @return
     */
    R<PageResult> getStockPageInfo(Integer page, Integer pageSize);

    /**
     * 统计最新交易日下股票每分钟涨跌停的数量
     * @return
     */
    R<Map> getStockUpdownCount();
    /**
     * 获取涨幅榜数据，取前4条
     */
    R<List<StockUpdownDomain>> getStockPageLimitInfo();
}
