package com.supernb.stock.controller;

import com.supernb.stock.domain.vo.resp.PageResult;
import com.supernb.stock.domain.vo.resp.R;
import com.supernb.stock.pojo.domain.InnerMarketDomain;
import com.supernb.stock.pojo.domain.StockBlockDomain;
import com.supernb.stock.pojo.domain.StockUpdownDomain;
import com.supernb.stock.service.StockService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * 股票相关接口定义
 */

@Api(value = "/api/quot", tags = {"股票相关接口定义"})
@RestController
@RequestMapping("/api/quot")
public class StockController {
    @Autowired
    private StockService stockService;

    /**
     * 获取国内最新大盘指数
     * @return
     */
    @ApiOperation(value = "国内指数", notes = "获取国内最新大盘指数", httpMethod = "GET")
    @GetMapping("/index/all")
    public R<List<InnerMarketDomain>> innerIndexAll(){
        return stockService.getInnerMarketInfo();
    }

    /**
     * 获取沪深两市板块最新数据，以交易总金额降序查询，取前10条数据
     */
    @ApiOperation(value = "板块指数", notes = "获取沪深两市板块最新数据，以交易总金额降序查询，取前10条数据", httpMethod = "GET")
    @GetMapping("/sector/all")
    public R<List<StockBlockDomain>> sectorAll(){
        return stockService.getSectorAllLimit();
    }
    /**
     * 分页查询股票最新数据，并按照涨幅排序查询
     * @param page 当前页
     * @param pageSize 每页大小
     * @return
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "int", name = "page", value = "当前页"),
            @ApiImplicitParam(paramType = "query", dataType = "int", name = "pageSize", value = "每页大小")
    })
    @ApiOperation(value = "涨幅榜->更多选项", notes = "分页查询股票最新数据，并按照涨幅排序查询", httpMethod = "GET")
    @GetMapping("/stock/all")
    public R<PageResult> getStockPageInfo(@RequestParam(name = "page",required = false,defaultValue = "1") Integer page,
                                          @RequestParam(name = "pageSize",required = false,defaultValue = "20") Integer pageSize){
        return stockService.getStockPageInfo(page,pageSize);
    }
    /**
     * 获取涨幅榜数据，取前4条
     */
    @ApiOperation(value = "涨幅榜", notes = "获取涨幅榜数据，取前4条", httpMethod = "GET")
    @GetMapping("/stock/increase")
     public R<List<StockUpdownDomain>> getStockPageLimitInfo(){
         return stockService.getStockPageLimitInfo();
     }


    /**
     * 统计最新交易日下股票每分钟涨跌停的数量
     * @return
     */
    @ApiOperation(value = "涨停跌停数", notes = "统计最新交易日下股票每分钟涨跌停的数量", httpMethod = "GET")
    @GetMapping("/stock/updown/count")
    public R<Map> getStockUpdownCount(){
        return stockService.getStockUpdownCount();
    }
}
