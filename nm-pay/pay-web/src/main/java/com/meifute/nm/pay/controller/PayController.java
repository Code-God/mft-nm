package com.meifute.nm.pay.controller;

import com.meifute.nm.pay.service.pay.MobilePayService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Classname PayController
 * @Description
 * @Date 2020-08-10 12:44
 * @Created by MR. Xb.Wu
 */
@Api(tags = {"支付模块相关接口v2.0"})
@RestController
@RequestMapping("/trade/pay/")
public class PayController {

    @Autowired
    @Qualifier("weChatPayServiceImpl")
    private MobilePayService weChatPayService;

    @Autowired
    @Qualifier("aliPayServiceImpl")
    private MobilePayService aliPayService;

    @Autowired
    @Qualifier("EPayServiceImpl")
    private MobilePayService ePayService;


    @ApiOperation(value = "微信支付回调通知")
    @PostMapping("/weChat/pay/notify")
    public void weChatPayNotify() {
        weChatPayService.notifyPay();
    }

    @ApiOperation(value = "支付宝支付回调通知")
    @PostMapping("/ali/pay/notify")
    public void aliPayNotify() {
        aliPayService.notifyPay();
    }

    @ApiOperation(value = "e支付回调通知")
    @PostMapping("/e/pay/notify")
    public void ePayNotify() {
        ePayService.notifyPay();
    }
}
