package com.supernb.stock;


import com.google.common.collect.Lists;
import com.supernb.stock.mapper.StockBusinessMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.stream.Collectors;

@SpringBootTest
public class TestMapper {
    @Autowired
    StockBusinessMapper stockBusinessMapper;

    @Test
    public void Test01(){
        List<String> allStockCodes = stockBusinessMapper.getAllStockCodes();
        System.out.println(allStockCodes);
        // 添加股票前缀
        allStockCodes = allStockCodes.stream().map(code->code.startsWith("6")?"sh"+code:"sz"+code).collect(Collectors.toList());
        // 将大集合分割成小集合，一个集合最多15个
        Lists.partition(allStockCodes,15).forEach( codes ->
                System.out.printf("size:%d:%s\n",codes.size(),codes)
        );
    }
}
