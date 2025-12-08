package com.supernb.stock.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.supernb.stock.domain.vo.resp.PageResult;
import com.supernb.stock.domain.vo.resp.R;
import com.supernb.stock.domain.vo.resp.ResponseCode;
import com.supernb.stock.mapper.StockBlockRtInfoMapper;
import com.supernb.stock.mapper.StockBusinessMapper;
import com.supernb.stock.mapper.StockMarketIndexInfoMapper;
import com.supernb.stock.mapper.StockRtInfoMapper;
import com.supernb.stock.pojo.domain.InnerMarketDomain;
import com.supernb.stock.pojo.domain.StockBlockDomain;
import com.supernb.stock.pojo.domain.StockUpdownDomain;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @Autowired
    private StockRtInfoMapper stockRtInfoMapper;

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
    public R<List<StockBlockDomain>> getSectorAllLimit() {
        //获取股票最新交易时间点
        Date lastDate = DateTimeUtil.getLastDate4Stock(DateTime.now()).toDate();
        //TODO mock数据,后续删除
        lastDate=DateTime.parse("2021-12-21 14:30:00", DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss")).toDate();
        //1.调用mapper接口获取数据
        List<StockBlockDomain> infos=stockBlockRtInfoMapper.getSectorAllLimit(lastDate);
//        //2.组装数据
//        if (CollectionUtils.isEmpty(infos)) {
//            return R.error(ResponseCode.NO_RESPONSE_DATA.getMessage());
//        }
        return R.ok(infos);
    }

    /**
     * 分页查询股票最新数据，并按照涨幅排序查询
     * @param page 当前页
     * @param pageSize 每页大小
     * @return
     */
    @Override
    public R<PageResult> getStockPageInfo(Integer page, Integer pageSize) {
        //1.设置PageHelper分页参数
        PageHelper.startPage(page,pageSize);
        //2.获取当前最新的股票交易时间点
        Date curDate = DateTimeUtil.getLastDate4Stock(DateTime.now()).toDate();
        //制作假数据
        curDate= DateTime.parse("2021-12-30 09:42:00", DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss")).toDate();
        //3.调用mapper接口查询
        List<StockUpdownDomain> infos= stockRtInfoMapper.getStockInfoByTime(curDate);
//        if (CollectionUtils.isEmpty(infos)) {
//            return R.error(ResponseCode.NO_RESPONSE_DATA);
//        }
        //4.组装PageInfo对象，获取分页的具体信息,因为PageInfo包含了丰富的分页信息，而部分分页信息是前端不需要的
        //PageInfo<StockUpdownDomain> pageInfo = new PageInfo<>(infos);
//        PageResult<StockUpdownDomain> pageResult = new PageResult<>(pageInfo);
        PageResult<StockUpdownDomain> pageResult = new PageResult<>(new PageInfo<>(infos));
        //5.封装响应数据
        return R.ok(pageResult);
    }
    /**
     * 获取涨幅榜数据，取前4条
     */
    @Override
    public R<List<StockUpdownDomain>> getStockPageLimitInfo() {
        //1.1 获取最新股票交易时间点
        DateTime curDateTime = DateTimeUtil.getLastDate4Stock(DateTime.now());
        Date curTime = curDateTime.toDate();
        //TODO mock数据，后续删除
        curTime= DateTime.parse("2022-01-06 14:25:00", DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss")).toDate();
        //3.调用mapper接口查询
        List<StockUpdownDomain> infos= stockRtInfoMapper.getStockLimitInfoByTime(curTime);
        return R.ok(infos);
    }

    /**
     * 统计最新交易日下股票每分钟涨跌停的数量
     * @return
     */
    @Override
    public R<Map> getStockUpdownCount() {
        //1.获取最新的交易时间范围 openTime  curTime
        //1.1 获取最新股票交易时间点
        DateTime curDateTime = DateTimeUtil.getLastDate4Stock(DateTime.now());
        Date curTime = curDateTime.toDate();
        //TODO mock数据，后续删除
        curTime= DateTime.parse("2022-01-06 14:25:00", DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss")).toDate();
        //1.2 获取最新交易时间对应的开盘时间
        DateTime openDate = DateTimeUtil.getOpenDate(curDateTime);
        Date openTime = openDate.toDate();
        //TODO mock数据，后续删除
        openTime= DateTime.parse("2022-01-06 09:30:00", DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss")).toDate();
        //2.查询涨停数据
        //约定mapper中flag入参： 1-》涨停数据 0：跌停
        List<Map> upCounts=stockRtInfoMapper.getStockUpdownCount(openTime,curTime,1);
        //3.查询跌停数据
        List<Map> dwCounts=stockRtInfoMapper.getStockUpdownCount(openTime,curTime,0);
        //4.组装数据
        HashMap<String, List> mapInfo = new HashMap<>();
        mapInfo.put("upList",upCounts);
        mapInfo.put("downList",dwCounts);
        //5.返回结果
        return R.ok(mapInfo);
    }

}
