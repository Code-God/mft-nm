package com.meifute.nm.pay.service.journal;

/**
 * @Classname JournalRecordService
 * @Description
 * @Date 2020-08-11 10:18
 * @Created by MR. Xb.Wu
 */
public interface JournalRecordService {

    /**
     * 根据用户id获取余额流水信息
     */
    void queryBalanceJournalRecordsByUserId();

    /**
     * 参数匹配余额流水信息
     */
    void queryBalanceJournalRecordsByInput();

    /**
     * 根据用户id获取积分流水信息
     */
    void queryCreditJournalRecordsByUserId();

    /**
     * 分页获取所有余额流水信息
     */
    void queryBalanceJournalRecordsPage();

    /**
     * 分页获取所有积分流水信息
     */
    void queryCreditJournalRecordsPage();
}
