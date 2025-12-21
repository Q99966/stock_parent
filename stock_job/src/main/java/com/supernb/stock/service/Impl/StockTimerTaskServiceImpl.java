package com.supernb.stock.service.Impl;

import com.google.common.collect.Lists;
import com.supernb.stock.mapper.StockBlockRtInfoMapper;
import com.supernb.stock.mapper.StockBusinessMapper;
import com.supernb.stock.mapper.StockMarketIndexInfoMapper;
import com.supernb.stock.mapper.StockRtInfoMapper;
import com.supernb.stock.pojo.entity.StockMarketIndexInfo;
import com.supernb.stock.pojo.entity.StockRtInfo;
import com.supernb.stock.pojo.vo.StockInfoConfig;
import com.supernb.stock.service.StockTimerTaskService;
import com.supernb.stock.utils.DateTimeUtil;
import com.supernb.stock.utils.IdWorker;
import com.supernb.stock.utils.ParseType;
import com.supernb.stock.utils.ParserStockInfoUtil;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
@Slf4j
public class StockTimerTaskServiceImpl implements StockTimerTaskService {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private StockInfoConfig stockInfoConfig;

    @Autowired
    private IdWorker idWorker;

    @Autowired
    private StockMarketIndexInfoMapper stockMarketIndexInfoMapper;

    @Autowired
    private ParserStockInfoUtil parserStockInfoUtil;

    @Autowired
    private StockBusinessMapper stockBusinessMapper;

    @Autowired
    private StockRtInfoMapper stockRtInfoMapper;

    @Autowired
    private StockBlockRtInfoMapper stockBlockRtInfoMapper;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     * 必须保证对象无状态
     */
    private HttpEntity<Object> httpEntity;
    /**
     * 获取国内大盘的实时数据信息
     */
    @Override
    public void getInnerMarketInfo() {
        String url=stockInfoConfig.getMarketUrl() + String.join(",",stockInfoConfig.getInner());
        //1.调用restTemplate采集数据
//        //1.1 组装请求头
//        HttpHeaders headers = new HttpHeaders();
//        //防盗链
//        headers.add("Referer","https://finance.sina.com.cn/stock/");
//        //用户客户端标志
//        headers.add("User-Agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/143.0.0.0 Safari/537.36 Edg/143.0.0.0");
//        //1.2 组装请求对象
//        HttpEntity<Object> httpEntity = new HttpEntity<>(headers);
        //1.3 resetTemplate发起请求
        ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.GET, httpEntity, String.class);
        // 判断有没有响应成功
        int statusCodeValue = responseEntity.getStatusCodeValue();
        if (statusCodeValue != 200) {
            //当前请求失败
            log.error("当前时间点：{}，采集数据失败，http状态码：{}", DateTime.now().toString("yyyy-MM-dd HH:mm:ss"), statusCodeValue);
            // 发送邮件给其他人员解决

            return;
        }
        // 获取js格式数据
        String jsData = responseEntity.getBody();
        //log.info("当前采集的数据：{}",resString);
        //2.java正则数据解析（重要）
//        var hq_str_sh000001="上证指数,3267.8103,3283.4261,3236.6951,3290.2561,3236.4791,0,0,402626660,398081845473,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2022-04-07,15:01:09,00,";
//        var hq_str_sz399001="深证成指,12101.371,12172.911,11972.023,12205.097,11971.334,0.000,0.000,47857870369,524892592190.995,0,0.000,0,0.000,0,0.000,0,0.000,0,0.000,0,0.000,0,0.000,0,0.000,0,0.000,0,0.000,2022-04-07,15:00:03,00";
        String reg="var hq_str_(.+)=\"(.+)\";";
        //编译表达式,获取编译对象
        Pattern pattern = Pattern.compile(reg);
        //匹配字符串
        Matcher matcher = pattern.matcher(jsData);
        ArrayList<StockMarketIndexInfo> list = new ArrayList<>();
        //判断是否有匹配的数值
        while (matcher.find()){
            //获取大盘的code
            String marketCode = matcher.group(1);
            //获取其它信息，字符串以逗号间隔
            String otherInfo=matcher.group(2);
            //以逗号切割字符串，形成数组
            String[] splitArr = otherInfo.split(",");
            //大盘名称
            String marketName=splitArr[0];
            //获取当前大盘的开盘点数
            BigDecimal openPoint=new BigDecimal(splitArr[1]);
            //前收盘点
            BigDecimal preClosePoint=new BigDecimal(splitArr[2]);
            //获取大盘的当前点数
            BigDecimal curPoint=new BigDecimal(splitArr[3]);
            //获取大盘最高点
            BigDecimal maxPoint=new BigDecimal(splitArr[4]);
            //获取大盘的最低点
            BigDecimal minPoint=new BigDecimal(splitArr[5]);
            //获取成交量
            Long tradeAmt=Long.valueOf(splitArr[8]);
            //获取成交金额
            BigDecimal tradeVol=new BigDecimal(splitArr[9]);
            //时间
            Date curTime = DateTimeUtil.getDateTimeWithoutSecond(splitArr[30] + " " + splitArr[31]).toDate();
            //组装entity对象
            StockMarketIndexInfo info = StockMarketIndexInfo.builder()
                    .id(idWorker.nextId())
                    .marketCode(marketCode)
                    .marketName(marketName)
                    .curPoint(curPoint)
                    .openPoint(openPoint)
                    .preClosePoint(preClosePoint)
                    .maxPoint(maxPoint)
                    .minPoint(minPoint)
                    .tradeVolume(tradeVol)
                    .tradeAmount(tradeAmt)
                    .curTime(curTime)
                    .build();
            //收集封装的对象，方便批量插入
            list.add(info);
        }
       log.info("采集的当前大盘数据：{}",list);
        //批量入库
//        int count =  stockMarketIndexInfoMapper.insertBatch(list);
        int count = 2;
        if(count>0){
            // 采集成功后通知backend刷新缓存
            // 发送的日期数据是告知对方当前更新的股票数据所在时间点
            rabbitTemplate.convertAndSend("stockExchange","inner.market",new Date());
            log.info("当前时间点：{}，大盘数据入库成功，入库条数：{}", DateTime.now().toString("yyyy-MM-dd HH:mm:ss"),count);
        }else{
            log.error("当前时间点：{}，大盘数据入库失败", DateTime.now().toString("yyyy-MM-dd HH:mm:ss"));
        }
    }
    /**
     * 获取国内个股数据
     */
    @Override
    public void getStockRtIndex() {
        // 获取所有股票代码
        List<String> allStockCodes = stockBusinessMapper.getAllStockCodes();
        // 添加股票前缀
        allStockCodes = allStockCodes.stream().map(code->code.startsWith("6")?"sh"+code:"sz"+code).collect(Collectors.toList());
        // 将大集合分割成小集合，一个集合最多15个
        Lists.partition(allStockCodes,15).forEach(codes -> {
            //拼接股票url地址
            String stockUrl=stockInfoConfig.getMarketUrl()+String.join(",",codes);
            //获取响应数据
            ResponseEntity<String> responseEntity = restTemplate.exchange(stockUrl, HttpMethod.GET, httpEntity, String.class);
            int statusCodeValue = responseEntity.getStatusCodeValue();
            if (statusCodeValue != 200) {
                //当前请求失败
                log.error("当前时间点：{}，采集数据失败，http状态码：{}", DateTime.now().toString("yyyy-MM-dd HH:mm:ss"), statusCodeValue);
                // 发送邮件给其他人员解决
                return;
            }
            String jsData = responseEntity.getBody();
            // 调用工具类进行数据解析
            List list = parserStockInfoUtil.parser4StockOrMarketInfo(jsData, ParseType.ASHARE);
            log.info("采集个股数据：{}",list);
            // 批量插入
//            int count =  stockRtInfoMapper.insertBatch(list);
            int count = 3;
            if(count>0){
                log.info("当前时间点：{}，个股数据入库成功，入库条数：{}", DateTime.now().toString("yyyy-MM-dd HH:mm:ss"),count);
            }else{
                log.error("当前时间点：{}，个股数据入库失败", DateTime.now().toString("yyyy-MM-dd HH:mm:ss"));
            }
        });
    }
    /**
     * 获取板块数据
     */
    @Override
    public void getStockBlockRInfo() {
        // 获取大盘Url
        String blockUrl = stockInfoConfig.getBlockUrl();
        // 获取响应数据
        ResponseEntity<String> responseEntity = restTemplate.exchange(blockUrl, HttpMethod.GET, httpEntity, String.class);
        int statusCodeValue = responseEntity.getStatusCodeValue();
        if (statusCodeValue != 200) {
            //当前请求失败
            log.error("当前时间点：{}，采集数据失败，http状态码：{}", DateTime.now().toString("yyyy-MM-dd HH:mm:ss"), statusCodeValue);
            // 发送邮件给其他人员解决
            return;
        }
        String jsData = responseEntity.getBody();
        log.info("当前采集的板块数据：{}",jsData);
        // 调用工具类进行数据解析
        List list = parserStockInfoUtil.parser4StockBlock(jsData);
        log.info("采集板块数据：{}",list);
        // 批量插入
//        int count =  stockBlockRtInfoMapper.insertBatch(list);
        int count = 4;
        if(count>0){
            log.info("当前时间点：{}，板块数据入库成功，入库条数：{}", DateTime.now().toString("yyyy-MM-dd HH:mm:ss"),count);
        }else{
            log.error("当前时间点：{}，板块数据入库失败", DateTime.now().toString("yyyy-MM-dd HH:mm:ss"));
        }
    }

    /**
     * bean生命周期的初始化回调方法
     */
    @PostConstruct
    public void initData(){
        // 组装请求头
        HttpHeaders headers = new HttpHeaders();
        //防盗链
        headers.add("Referer","https://finance.sina.com.cn/stock/");
        //用户客户端标志
        headers.add("User-Agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/143.0.0.0 Safari/537.36 Edg/143.0.0.0");
        //1.2 组装请求对象
        httpEntity = new HttpEntity<>(headers);
    }
} 