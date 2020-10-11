package com.meifute.nm.pay.service.payconfig;


/**
 * @Classname PayTypeConfigService
 * @Description
 * @Date 2020-08-11 11:10
 * @Created by MR. Xb.Wu
 */
public interface PayConfigService {

    /**
     * 创建分公司支付配置信息
     */
    void createPayTypeConfig();

    /**
     * 根据分公司id获取支付方式
     */
    void queryPayTypesByCompanyId();

    /**
     * 根据分公司id获取支付配置信息(包含银行卡信息，支付宝配置信息，微信配置信息)
     */
    void queryPayConfigByCompanyId();
}
