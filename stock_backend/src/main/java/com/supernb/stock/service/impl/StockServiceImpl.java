package com.supernb.stock.service.impl;

import com.supernb.stock.domain.vo.resp.R;
import com.supernb.stock.domain.vo.resp.ResponseCode;
import com.supernb.stock.mapper.StockBlockRtInfoMapper;
import com.supernb.stock.mapper.StockBusinessMapper;
import com.supernb.stock.mapper.StockMarketIndexInfoMapper;
import com.supernb.stock.pojo.domain.InnerMarketDomain;
import com.supernb.stock.pojo.domain.StockBlockDomain;
import com.supernb.stock.pojo.entity.StockBusiness;
import com.supernb.stock.pojo.vo.StockInfoConfig;
import com.supernb.stock.service.StockService;
import com.supernb.stock.utils.DateTimeUtil;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.List;

@Service
public class StockServiceImpl implements StockService {
    @Autowired
    private StockBusinessMapper stockBusinessMapper;

    @Autowired
    private StockMarketIndexInfoMapper stockMarketIndexInfoMapper;

    @Autowired
    private StockInfoConfig stockInfoConfig;

    @Autowired
    private StockBlockRtInfoMapper stockBlockRtInfoMapper;

    @Override
    public List<StockBusiness> getAllStockBusiness() {
//        return stockBusinessMapper.findAll();
        return null;
    }
    /**
     * 获取国内大盘最新数据
     * @return
     */


    @Override
    public R<List<InnerMarketDomain>> getInnerMarketInfo() {
        // 1.获取股票最新的交易时间点
        Date curDate = DateTimeUtil.getLastDate4Stock(DateTime.now()).toDate();
        // 假数据 等后续完成股票采集job工程，再将代码删除
        curDate = DateTime.parse("2021-12-28 09:31:00", DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss")).toDate();
        // 2. 获取大盘编码集合
        List<String> mCodes = stockInfoConfig.getInner();
        // 3.调用mapper查询数据
        List<InnerMarketDomain> data = stockMarketIndexInfoMapper.getMarketInfo(curDate,mCodes);
        // 4.封装并响应
        return R.ok(data);
    }

    /**
     * 获取沪深两市板块最新数据，以交易总金额降序查询，取前10条数据
     */
    @Override
    public R<List<StockBlockDomain>> sectorAllLimit() {
        //获取股票最新交易时间点
        Date lastDate = DateTimeUtil.getLastDate4Stock(DateTime.now()).toDate();
        //TODO mock数据,后续删除
        lastDate=DateTime.parse("2021-12-21 14:30:00", DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss")).toDate();
        //1.调用mapper接口获取数据
        List<StockBlockDomain> infos=stockBlockRtInfoMapper.sectorAllLimit(lastDate);
//        //2.组装数据
//        if (CollectionUtils.isEmpty(infos)) {
//            return R.error(ResponseCode.NO_RESPONSE_DATA.getMessage());
//        }
        return R.ok(infos);
    }
}
