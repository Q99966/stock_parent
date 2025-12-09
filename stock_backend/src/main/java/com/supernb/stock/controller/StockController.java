package com.supernb.stock.controller;

import com.supernb.stock.domain.vo.resp.PageResult;
import com.supernb.stock.domain.vo.resp.R;
import com.supernb.stock.pojo.domain.InnerMarketDomain;
import com.supernb.stock.pojo.domain.Stock4MinuteDomain;
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

import javax.servlet.http.HttpServletResponse;
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

    /**
     * 将指定页的股票数据导出到excel表下
     * @param response
     * @param page  当前页
     * @param pageSize 每页大小
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "int", name = "page", value = "当前页"),
            @ApiImplicitParam(paramType = "query", dataType = "int", name = "pageSize", value = "每页大小")
    })
    @ApiOperation(value = "涨幅榜导出功能", notes = "将指定页的股票数据导出到excel表下", httpMethod = "GET")
    @GetMapping("/stock/export")
    public void stockExport(HttpServletResponse response,
                            @RequestParam(name = "page",required = false,defaultValue = "1") Integer page,
                            @RequestParam(name = "pageSize",required = false,defaultValue = "20") Integer pageSize){
        stockService.stockExport(response,page,pageSize);
    }

    /**
     * 功能描述：统计国内A股大盘T日和T-1日成交量对比功能（成交量为沪市和深市成交量之和）
     * @return
     */
    @ApiOperation(value = "每分钟成交量对比图", notes = "功能描述：统计国内A股大盘T日和T-1日成交量对比功能（成交量为沪市和深市成交量之和）", httpMethod = "GET")
    @GetMapping("/stock/tradeAmt")
    public R<Map<String,List>> getComparedStockTradeAmt(){
        return stockService.getComparedStockTradeAmt();
    }

    /**
     * 查询当前时间下股票的涨跌幅度区间统计功能
     * 如果当前日期不在有效时间内，则以最近的一个股票交易时间作为查询点
     * @return
     */
    @ApiOperation(value = "个股涨跌", notes = "查询当前时间下股票的涨跌幅度区间统计功能 如果当前日期不在有效时间内，则以最近的一个股票交易时间作为查询点", httpMethod = "GET")
    @GetMapping("/stock/updown")
    public R<Map> getStockUpDown(){
        return stockService.getStockUpDownScopeCount();
    }

    /**
     * 功能描述：查询单个个股的分时行情数据，也就是统计指定股票T日每分钟的交易数据；
     *         如果当前日期不在有效时间内，则以最近的一个股票交易时间作为查询时间点
     * @param code 股票编码
     * @return
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "string", name = "code", value = "股票编码", required = true)
    })
    @ApiOperation(value = "个股分时K线行情功能", notes = "功能描述：查询单个个股的分时行情数据，也就是统计指定股票T日每分钟的交易数据；         如果当前日期不在有效时间内，则以最近的一个股票交易时间作为查询时间点", httpMethod = "GET")
    @GetMapping("/stock/screen/time-sharing")
    public R<List<Stock4MinuteDomain>> stockScreenTimeSharing(@RequestParam("code") String code){
        return stockService.stockScreenTimeSharing(code);
    }

}
