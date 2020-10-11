package com.meifute.nm.pay.controller.implement;

import com.meifute.nm.pay.api.journal.JournalApiService;
import com.meifute.nm.pay.service.journal.JournalRecordService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Classname JournalFeignController
 * @Description 流水相关接口
 * @Date 2020-08-11 10:04
 * @Created by MR. Xb.Wu
 */
@Slf4j
@RestController
public class JournalFeignController implements JournalApiService {

    @Autowired
    private JournalRecordService journalRecordService;

    @Override
    public void queryBalanceJournalRecordsByUserId(@PathVariable("userId") String userId) {
        journalRecordService.queryBalanceJournalRecordsByUserId();
    }

    @Override
    public void queryBalanceJournalRecordsByInput() {
        journalRecordService.queryBalanceJournalRecordsByInput();
    }

    @Override
    public void queryCreditJournalRecordsByUserId(@PathVariable("userId") String userId) {
        journalRecordService.queryCreditJournalRecordsByUserId();
    }

    @Override
    public void queryBalanceJournalRecordsPage() {
        journalRecordService.queryBalanceJournalRecordsPage();
    }

    @Override
    public void queryCreditJournalRecordsPage() {
        journalRecordService.queryCreditJournalRecordsPage();
    }
}
