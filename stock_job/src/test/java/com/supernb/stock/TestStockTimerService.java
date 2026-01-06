package com.supernb.stock;

import com.supernb.stock.service.StockTimerTaskService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class TestStockTimerService {
    @Autowired
    private StockTimerTaskService stockTimerService;

    /**
     * 获取大盘数据
     */
    @Test
    public void test01() throws InterruptedException {
//        stockTimerService.getInnerMarketInfo();
        stockTimerService.getStockRtIndex();
//        stockTimerService.getStockBlockRInfo();
        Thread.sleep(5000);
    }
}    