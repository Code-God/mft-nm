package com.meifute.nm.others.utils;

import com.meifute.nm.others.business.training.entity.TrainMeetingEnroll;
import com.meifute.nm.others.business.training.service.TrainMeetingAffairsService;
import com.meifute.nm.others.business.training.service.TrainMeetingEnrollService;
import com.meifute.nm.otherscommon.utils.redislock.RedisLock;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Classname TaskService
 * @Description
 * @Date 2020-07-08 17:57
 * @Created by MR. Xb.Wu
 */
@Slf4j
@Component
public class TaskService {

    @Autowired
    private TrainMeetingEnrollService trainMeetingEnrollService;
    @Autowired
    private TrainMeetingAffairsService trainMeetingAffairsService;

    /**
     * 定期补偿退款操作 10分钟执行一次
     */
    @Scheduled(fixedRate = 10 * 60 * 1000)
    @RedisLock(fixed = true, repeat = true)
    public void retryBackCost() {
        log.info("检测是否存在补偿退款...");
        List<TrainMeetingEnroll> list = trainMeetingEnrollService.regularNoBackList();
        if (list != null) {
            log.info("补偿退款...:{}", list.size());
            List<TrainMeetingEnroll> t = new ArrayList<>();
            list.forEach(p ->{
                TrainMeetingEnroll e = new TrainMeetingEnroll();
                e.setId(p.getId());
                e.setUpdateTime(LocalDateTime.now());
                t.add(e);
            });
            trainMeetingEnrollService.updateMeetingBatchById(t);
            List<String> ids = list.stream().map(TrainMeetingEnroll::getId).collect(Collectors.toList());
            trainMeetingAffairsService.retrySendCostEnrollDelayMQ(ids);
        }
    }

    @Scheduled(fixedRate = 5 * 1000)
    @RedisLock(fixed = true, repeat = true)
    public void endMeeting() {
        trainMeetingAffairsService.endMeeting();
    }

}
