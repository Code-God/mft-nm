package com.meifute.nm.others.feignclient;

import com.meifute.nm.othersserver.domain.dto.PayModel;
import com.meifute.nm.othersserver.domain.dto.PayResult;
import com.meifute.nm.othersserver.domain.dto.UnifiedWeChatDto;
import com.meifute.nm.othersserver.domain.param.MobileUnifiedOrderParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Classname PayFeignClient
 * @Description
 * @Date 2020-07-08 19:14
 * @Created by MR. Xb.Wu
 */
@FeignClient("mall-pay")
@RequestMapping(value = "/api/implement/pay", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
public interface PayFeignClient {

    @GetMapping("/get/pay/type/config")
    List<Integer> getPayTypeByUserId(@RequestParam("currency") String currency, @RequestParam("userId") String userId);

    @GetMapping("/training/back/cost")
    boolean signUpBackCost(@RequestParam("id") String id);

    @ApiOperation(value = "统一查询支付结果接口")
    @PostMapping("/query/pay/records")
    List<PayResult> getPayResultByOrderIds(@RequestBody List<String> orderIds);

    @ApiOperation(value = "统一查询退款结果接口")
    @PostMapping("/query/refund/records")
    List<PayResult> getRefundResultByOrderIds(@RequestBody List<String> orderIds);

    @ApiOperation(value = "统一退款接口")
    @PostMapping("/refund")
    boolean refund(@RequestBody PayModel payModel);

    @ApiOperation(value = "统一余额支付接口")
    @PostMapping("/pay")
    boolean balancePay(@RequestBody PayModel payModel);

    @ApiOperation(value = "统一微信预付单接口")
    @PostMapping("/weChat/pay/order")
    UnifiedWeChatDto wxUnifiedOrderNew(@RequestBody MobileUnifiedOrderParam mobileUnifiedOrderParam);

    @ApiOperation(value = "统一支付宝预付单接口")
    @PostMapping("/ali/Pay/order")
    String aliUnifiedOrderNew(@RequestBody MobileUnifiedOrderParam mobileUnifiedOrderParam);
}
