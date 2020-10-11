package com.meifute.nm.pay.controller.implement;

import com.meifute.nm.pay.api.account.AccountApiService;
import com.meifute.nm.pay.service.account.AccountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Classname AccountFeignController
 * @Description 账户相关接口
 * @Date 2020-08-11 10:03
 * @Created by MR. Xb.Wu
 */
@Slf4j
@RestController
public class AccountFeignController implements AccountApiService {

    @Autowired
    private AccountService accountService;

    @Override
    public void createAccount() {
        accountService.createAccount();
    }

    @Override
    public void queryAccountByUserId(String userId) {
        accountService.queryAccountByUserId();
    }

    @Override
    public void queryBalanceAccountPage() {
        accountService.queryBalanceAccountPage();
    }

    @Override
    public void queryCreditAccountPage() {
        accountService.queryCreditAccountPage();
    }

    @Override
    public void verifyAccountPassword() {
        accountService.verifyAccountPassword();
    }
}
