package com.meifute.nm.pay.service.account;


/**
 * @Classname AccountService
 * @Description
 * @Date 2020-08-11 10:18
 * @Created by MR. Xb.Wu
 */
public interface AccountService {

    /**
     * 创建账户
     */
    void createAccount();

    /**
     * 分页获取所有余额账户信息
     */
    void queryBalanceAccountPage();

    /**
     * 分页获取所有积分账户信息
     */
    void queryCreditAccountPage();

    /**
     * 变更余额
     */
    void changeBalance();

    /**
     * 变更积分
     */
    void changeCredit();

    /**
     * 根据用户id获取账户信息(包含积分账户和余额账户)
     */
    void queryAccountByUserId();

    /**
     * 验证账户密码
     */
    void verifyAccountPassword();
}
