package com.meifute.nm.others.business.activitypay.service.impl;

import com.meifute.nm.others.business.activitypay.service.ActivityPayService;
import com.meifute.nm.others.business.training.service.AmoyActivityEnrollService;
import com.meifute.nm.othersserver.domain.dto.PayParam;
import com.meifute.nm.othersserver.domain.dto.PayResult;
import com.meifute.nm.othersserver.domain.dto.UnifiedWeChatDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Classname ActivityPayServiceImpl
 * @Description
 * @Date 2020-08-21 10:37
 * @Created by MR. Xb.Wu
 */
@Slf4j
@Service
public class ActivityPayServiceImpl implements ActivityPayService {

    @Autowired
    private AmoyActivityEnrollService amoyActivityEnrollService;

    @Override
    public boolean balancePay(PayParam payParam) {
        boolean result = false;
        switch (payParam.getOrderType()) {
            case 0:
                break;
            case 1:
                break;
            case 2:
                break;
            case 3:
                break;
            case 4:
                result = amoyActivityEnrollService.balancePay(payParam);
                break;
        }
        return result;
    }

    @Override
    public UnifiedWeChatDto wxUnifiedOrderNew(PayParam payParam) {
        UnifiedWeChatDto unifiedWeChatDto = null;
        switch (payParam.getOrderType()) {
            case 0:
                break;
            case 1:
                break;
            case 2:
                break;
            case 3:
                break;
            case 4:
                unifiedWeChatDto = amoyActivityEnrollService.wxUnifiedOrderNew(payParam);
                break;
        }
        return unifiedWeChatDto;
    }

    @Override
    public String aliUnifiedOrderNew(PayParam payParam) {
        String aliResponse = null;
        switch (payParam.getOrderType()) {
            case 0:
                break;
            case 1:
                break;
            case 2:
                break;
            case 3:
                break;
            case 4:
                aliResponse = amoyActivityEnrollService.aliUnifiedOrderNew(payParam);
                break;
        }
        return aliResponse;
    }

    @Override
    public boolean notifyPay(PayParam payParam) {
        String response = "FIAL";
        PayResult payResult = new PayResult();
        payResult.setOrderId(payParam.getOrderId());
        switch (payParam.getOrderType()) {
            case 0:
                break;
            case 1:
                break;
            case 2:
                break;
            case 3:
                break;
            case 4:
                response = amoyActivityEnrollService.successEnroll(payResult, 1);
                break;
        }
        return "SUCCESS".equalsIgnoreCase(response);
    }
}
