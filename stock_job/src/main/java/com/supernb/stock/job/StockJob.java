package com.supernb.stock.job;
import com.supernb.stock.service.StockTimerTaskService;
import com.xxl.job.core.handler.annotation.XxlJob;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 定义采集股票数据的定时任务
 */

@Component
public class StockJob {

    @Autowired
    private StockTimerTaskService stockTimerTaskService;

    /**
     * 测试采集任务
     */
    @XxlJob("job_test")
    public void jobTest() {
        System.out.println("当前时间：" + DateTime.now().toString("yyyy-MM-dd HH:mm:ss"));
    }

    /**
     * 定义定时任务，采集国内大盘数据
     */
    @XxlJob("getInnerMarketInfo")
    public void getInnerMarketInfo(){
        stockTimerTaskService.getInnerMarketInfo();
    }

    /**
     * 定时采集A股数据
     */
    @XxlJob("getStockRtIndex")
    public void getStockRtIndex(){
        stockTimerTaskService.getStockRtIndex();
    }

    /**
     * 板块定时任务
     */
    @XxlJob("getStockBlockRInfo")
    public void getStockBlockRInfo(){
        stockTimerTaskService.getStockBlockRInfo();
    }
}