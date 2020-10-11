package com.meifute.nm.pay.controller.implement;

import com.meifute.nm.pay.api.pay.TradePayApiService;
import com.meifute.nm.pay.service.pay.AccountPayService;
import com.meifute.nm.pay.service.pay.MobilePayService;
import com.meifute.nm.pay.service.payconfig.PayConfigService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Classname PayFeignController
 * @Description 支付相关接口
 * @Date 2020-08-10 12:46
 * @Created by MR. Xb.Wu
 */
@Slf4j
@RestController
public class TradePayFeignController implements TradePayApiService {

    @Autowired
    @Qualifier("weChatPayServiceImpl")
    private MobilePayService weChatPayService;

    @Autowired
    @Qualifier("aliPayServiceImpl")
    private MobilePayService aliPayService;

    @Autowired
    @Qualifier("EPayServiceImpl")
    private MobilePayService ePayService;

    @Autowired
    @Qualifier("balancePayServiceImpl")
    private AccountPayService balancePayService;

    @Autowired
    @Qualifier("creditPayServiceImpl")
    private AccountPayService creditPayService;

    @Autowired
    private PayConfigService payConfigService;

    @Override
    public void balancePay() {
        balancePayService.pay();
    }

    @Override
    public void creditPay() {
        creditPayService.pay();
    }

    @Override
    public void weChatPay() {
        weChatPayService.unifiedPay();
    }

    @Override
    public void aliPay() {
        aliPayService.unifiedPay();
    }

    @Override
    public void ePay() {
        ePayService.unifiedPay();
    }

    @Override
    public void createPayTypeConfig() {
        payConfigService.createPayTypeConfig();
    }

    @Override
    public void queryPayTypesByCompanyId(@PathVariable("companyId") String companyId) {
        payConfigService.queryPayTypesByCompanyId();
    }

    @Override
    public void queryPayConfigByCompanyId(@PathVariable("companyId") String companyId) {
        payConfigService.queryPayConfigByCompanyId();
    }
}
