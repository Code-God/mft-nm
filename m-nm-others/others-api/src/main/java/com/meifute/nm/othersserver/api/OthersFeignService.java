package com.meifute.nm.othersserver.api;

import com.meifute.nm.othersserver.domain.dto.TrainMeetingAffairsDto;
import com.meifute.nm.othersserver.domain.dto.TrainMeetingEnrollDto;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.*;

/**
 * @Classname TrainingFeignClient
 * @Description TODO
 * @Date 2020-07-07 10:01
 * @Created by MR. Xb.Wu
 */
@Api(tags = "会务培训活动内部接口")
@RequestMapping("/bgw/training/feign/")
public interface OthersFeignService {

    @PostMapping("/update/meeting")
    boolean updateMeeting(@RequestBody TrainMeetingAffairsDto trainMeetingAffairsDto);

    @GetMapping("/get/enroll/by/id")
    TrainMeetingEnrollDto queryByEnrollId(@RequestParam("id") String id);

    @PostMapping("/update/enroll")
    boolean updateEnrollById(@RequestBody TrainMeetingEnrollDto trainMeetingEnrollDto);
}
