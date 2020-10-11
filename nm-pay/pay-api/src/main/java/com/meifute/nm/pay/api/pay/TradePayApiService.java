package com.meifute.nm.pay.api.pay;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Classname TradePayApiService
 * @Description 支付相关接口
 * @Date 2020-08-10 20:15
 * @Created by MR. Xb.Wu
 */
@Api(tags = "支付相关内部接口v2.0")
@RequestMapping(value = "/feign")
public interface TradePayApiService {

    @ApiOperation(value = "余额支付")
    @PostMapping("/balance/apy")
    void balancePay();

    @ApiOperation(value = "积分支付")
    @PostMapping("/credit/apy")
    void creditPay();

    @ApiOperation(value = "微信支付")
    @PostMapping("/weChat/apy")
    void weChatPay();

    @ApiOperation(value = "支付宝支付")
    @PostMapping("/ali/apy")
    void aliPay();

    @ApiOperation(value = "e支付")
    @PostMapping("/e/apy")
    void ePay();

    @ApiOperation(value = "创建分公司支付配置信息")
    @PostMapping("/pay/config")
    void createPayTypeConfig();

    @ApiOperation(value = "根据分公司id获取支付方式")
    @GetMapping("/payment/types/{companyId}")
    void queryPayTypesByCompanyId(@PathVariable("companyId") String companyId);

    @ApiOperation(value = "根据分公司id获取支付配置信息(包含银行卡信息，支付宝配置信息，微信配置信息)")
    @GetMapping("/payment/configs/{companyId}")
    void queryPayConfigByCompanyId(@PathVariable("companyId") String companyId);
}
