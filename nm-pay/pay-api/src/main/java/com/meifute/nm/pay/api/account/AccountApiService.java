package com.meifute.nm.pay.api.account;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Classname AccountApiService
 * @Description 账户信息接口
 * @Date 2020-08-10 20:11
 * @Created by MR. Xb.Wu
 */
@Api(tags = "账户信息内部接口v2.0")
@RequestMapping(value = "/feign")
public interface AccountApiService {

    @ApiOperation(value = "创建账户信息（自动创建余额和积分账户）")
    @PostMapping("/account")
    void createAccount();

    @ApiOperation(value = "根据用户id获取账户信息(包含积分账户和余额账户)")
    @GetMapping("/account/{userId}")
    void queryAccountByUserId(@ApiParam(name = "userId", value = "用户id", required = true) @PathVariable("userId") String userId);

    @ApiOperation(value = "分页获取所有余额账户信息")
    @PostMapping("/balance/account/page")
    void queryBalanceAccountPage();

    @ApiOperation(value = "分页获取所有积分账户信息")
    @PostMapping("/query/credit/account/page")
    void queryCreditAccountPage();

    @ApiOperation(value = "验证账户密码")
    @PostMapping("/verify/account/password")
    void verifyAccountPassword();
}
