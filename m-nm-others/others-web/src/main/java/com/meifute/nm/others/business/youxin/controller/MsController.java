package com.meifute.nm.others.business.youxin.controller;

import com.meifute.nm.others.business.youxin.entity.BooleanResultDTO;
import com.meifute.nm.others.business.youxin.entity.CloudStockDTO;
import com.meifute.nm.others.business.youxin.entity.MsAgentDTO;
import com.meifute.nm.others.business.youxin.entity.OrderCreateVO;
import com.meifute.nm.others.business.youxin.service.IMsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @ClassName: MsController
 * @Description:
 * @Author: Chen
 * @Date: 2020/7/16 10:47
 * @Version: 1.0
 */

@RequestMapping("/ms")
@RestController
@Api(tags = "会员商城 有信接口")
public class MsController {

    @Autowired
    IMsService msService;

    @ApiOperation(value = "有信调用系统获取代理信息", notes = "有信调用系统获取代理信息")
    @GetMapping("/agent")
    public MsAgentDTO getMsAgent(@RequestParam("seqid")String seqid,
                                 @RequestParam("phone")String phone,
                                 @RequestParam(value = "number",required = false)String number,
                                 @RequestParam(value = "verifyType",required = false)String verifyType,
                                 @RequestParam("sign")String sign){
        return msService.getMsAgent(seqid,phone,number,verifyType,sign);
    }

    @ApiOperation(value = "有信调用系统 获取代理云库存", notes = "有信调用系统 获取代理云库存")
    @GetMapping("/cloud/stock")
    public CloudStockDTO agentCloudStock(@RequestParam("seqid")String seqid,
                                         @RequestParam(value = "itemCode",required = false)String itemCode,
                                         @RequestParam(value = "childIdentify")String childIdentify,
                                         @RequestParam(value = "wareCode",required = false)String wareCode,
                                         @RequestParam("sign")String sign){
        return msService.agentCloudStock(seqid,itemCode,childIdentify,wareCode,sign);
    }


    @ApiOperation(value = "有信调用系统,创建订单，扣减代理云库存", notes = "有信调用系统,创建订单，扣减代理云库存")
    @PostMapping("/create/order")
    public BooleanResultDTO createOrder(@RequestBody OrderCreateVO orderCreateVO){
        return msService.createOrder(orderCreateVO);
    }


    @ApiOperation(value = "有信调用系统,取消订单，增加代理云库存", notes = "有信调用系统,取消订单，增加代理云库存")
    @GetMapping("/cancel/Order")
    public BooleanResultDTO cancelOrder(@RequestParam("seqid")String seqid,
                                  @RequestParam("orderCode")String orderCode,
                                  @RequestParam(value = "subOrderCode",required = false)String subOrderCode,
                                  @RequestParam(value = "reason",required = false)String reason,
                                  @RequestParam("sign")String sign){
        return msService.cancelOrder(seqid,orderCode,subOrderCode,reason,sign);
    }







}