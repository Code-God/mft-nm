package com.meifute.nm.pay.service.pay;

/**
 * @Classname TradePayService
 * @Description
 * @Date 2020-08-11 10:18
 * @Created by MR. Xb.Wu
 */
public interface MobilePayService {

    /**
     * 创建移动支付单
     */
    void unifiedPay();

    /**
     * 支付回调通知
     */
    void notifyPay();

}
