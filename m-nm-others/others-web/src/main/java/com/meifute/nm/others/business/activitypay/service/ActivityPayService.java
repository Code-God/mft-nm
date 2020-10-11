package com.meifute.nm.others.business.activitypay.service;

import com.meifute.nm.othersserver.domain.dto.PayParam;
import com.meifute.nm.othersserver.domain.dto.UnifiedWeChatDto;

/**
 * @Classname ActicityPayService
 * @Description
 * @Date 2020-08-21 10:36
 * @Created by MR. Xb.Wu
 */
public interface ActivityPayService {

    boolean balancePay(PayParam payParam);

    UnifiedWeChatDto wxUnifiedOrderNew(PayParam payParam);

    String aliUnifiedOrderNew(PayParam payParam);

    boolean notifyPay(PayParam payParam);
}
