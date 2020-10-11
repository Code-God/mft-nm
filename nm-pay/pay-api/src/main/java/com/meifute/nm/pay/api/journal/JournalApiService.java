package com.meifute.nm.pay.api.journal;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Classname JournalApiService
 * @Description 资金流水接口
 * @Date 2020-08-10 20:13
 * @Created by MR. Xb.Wu
 */
@Api(tags = "交易流水内部接口v2.0")
@RequestMapping(value = "/feign")
public interface JournalApiService {

    @ApiOperation(value = "根据用户id获取余额流水信息")
    @GetMapping("/balance/journal/{userId}")
    void queryBalanceJournalRecordsByUserId(@PathVariable("userId") String userId);

    @ApiOperation(value = "参数匹配余额流水信息")
    @PostMapping("/balance/journal/records")
    void queryBalanceJournalRecordsByInput();

    @ApiOperation(value = "根据用户id获取积分流水信息")
    @GetMapping("/credit/journal/{userId}")
    void queryCreditJournalRecordsByUserId(@PathVariable("userId") String userId);

    @ApiOperation(value = "分页获取所有余额流水信息")
    @PostMapping("/query/balance/journal/record/page")
    void queryBalanceJournalRecordsPage();

    @ApiOperation(value = "分页获取所有积分流水信息")
    @PostMapping("/query/credit/journal/record/page")
    void queryCreditJournalRecordsPage();
}
