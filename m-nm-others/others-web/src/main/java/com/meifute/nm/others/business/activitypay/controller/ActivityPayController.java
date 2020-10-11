package com.meifute.nm.others.business.activitypay.controller;

import com.meifute.nm.others.business.activitypay.service.ActivityPayService;
import com.meifute.nm.otherscommon.base.BaseController;
import com.meifute.nm.otherscommon.base.MallResponse;
import com.meifute.nm.othersserver.domain.dto.PayParam;
import com.meifute.nm.othersserver.domain.dto.UnifiedWeChatDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Classname ActivityPayController
 * @Description
 * @Date 2020-08-21 09:34
 * @Created by MR. Xb.Wu
 */
@RequestMapping("/activity/pay")
@RestController
@Api(tags = "活动统一预支付调用接口")
public class ActivityPayController extends BaseController {

    @Autowired
    private ActivityPayService activityPayService;

    @ApiOperation(value = "统一活动余额支付调用接口", notes = "厦门活动余额支付调用接口")
    @PostMapping("/balance/pay")
    public ResponseEntity<MallResponse<Boolean>> balancePay(@RequestBody PayParam payParam) {
        boolean b = activityPayService.balancePay(payParam);
        return ResponseEntity.ok(successResult(b));
    }

    @ApiOperation(value = "统一活动微信预付单接口", notes = "厦门活动微信预付单接口")
    @PostMapping("/weChat/pay/order")
    public ResponseEntity<MallResponse<UnifiedWeChatDto>> wxUnifiedOrderNew(@RequestBody PayParam payParam) {
        UnifiedWeChatDto unifiedWeChatDto = activityPayService.wxUnifiedOrderNew(payParam);
        return ResponseEntity.ok(successResult(unifiedWeChatDto));
    }

    @ApiOperation(value = "统一活动支付宝预付单接口", notes = "厦门活动支付宝预付单接口")
    @PostMapping("/ali/pay/order")
    public ResponseEntity<MallResponse<String>> aliUnifiedOrderNew(@RequestBody PayParam payParam) {
        String response = activityPayService.aliUnifiedOrderNew(payParam);
        return ResponseEntity.ok(successResult(response));
    }

    @ApiOperation(value = "统一支付回调", notes = "统一支付回调")
    @PostMapping("/notify/pay")
    public ResponseEntity<MallResponse<Boolean>> notifyPay(@RequestBody PayParam payParam) {
        boolean b = activityPayService.notifyPay(payParam);
        return ResponseEntity.ok(successResult(b));
    }

}
