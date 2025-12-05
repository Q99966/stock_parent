package com.supernb.stock.service;

import com.supernb.stock.domain.vo.resp.R;
import com.supernb.stock.pojo.domain.InnerMarketDomain;
import com.supernb.stock.pojo.domain.StockBlockDomain;
import com.supernb.stock.pojo.entity.StockBusiness;

import java.util.List;

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
    R<List<StockBlockDomain>> sectorAllLimit();
}
