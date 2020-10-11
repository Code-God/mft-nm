package com.meifute.nm.others.business.training.service.impl;

import com.meifute.nm.others.business.training.entity.AmoyActivityEnroll;
import com.meifute.nm.others.business.training.service.AmoyActivityEnrollService;
import com.meifute.nm.others.feignclient.AgentFeignClient;
import com.meifute.nm.others.feignclient.PayFeignClient;
import com.meifute.nm.otherscommon.utils.redislock.RedisLock;
import com.meifute.nm.othersserver.domain.dto.PayResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Classname JobService
 * @Description
 * @Date 2020-08-20 20:12
 * @Created by MR. Xb.Wu
 */
@Slf4j
@Component
public class JobService {

    @Autowired
    private PayFeignClient payFeignClient;
    @Autowired
    private AmoyActivityEnrollService amoyActivityEnrollService;


    /**
     * 轮询查询支付结果并做处理
     */
    @Scheduled(fixedRate = 10 * 1000)
    @RedisLock(fixed = true, repeat = true)
    public void payTry() {
        List<AmoyActivityEnroll> paying = amoyActivityEnrollService.getPaying();
        if (!CollectionUtils.isEmpty(paying)) {
            List<AmoyActivityEnroll> list = new ArrayList<>();
            List<String> orderIds = paying.stream().map(AmoyActivityEnroll::getId).collect(Collectors.toList());
            List<PayResult> payResults = payFeignClient.getPayResultByOrderIds(orderIds);
            if (!CollectionUtils.isEmpty(payResults)) {
                payResults.forEach(p ->{
                    AmoyActivityEnroll activityEnroll = new AmoyActivityEnroll();
                    activityEnroll.setId(p.getOrderId());
                    activityEnroll.setEnrollStatus(2);
                    activityEnroll.setPayType(String.valueOf(p.getPayType()));
                    activityEnroll.setPayTime(p.getPayTime());
                    activityEnroll.setPayTradeNo(p.getOutTradeNo());
                    list.add(activityEnroll);
                });
                amoyActivityEnrollService.batchUpdateEnrollById(list);
            }
        }
    }

    /**
     * 定时取消（每天0点执行一次）
     */
    @Scheduled(cron = "0 0 0 * * ?")
    @RedisLock(fixed = true, repeat = true)
    public void cancelTry() {
        if (LocalDateTime.of(2020,9, 6,23,59,0).isBefore(LocalDateTime.now())) {
            amoyActivityEnrollService.closeAllNotPay();
        }
    }
}
