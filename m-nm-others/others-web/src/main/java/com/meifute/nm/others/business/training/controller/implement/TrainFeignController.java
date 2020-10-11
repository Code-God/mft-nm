package com.meifute.nm.others.business.training.controller.implement;

import com.meifute.nm.othersserver.api.OthersFeignService;
import com.meifute.nm.othersserver.domain.dto.TrainMeetingAffairsDto;
import com.meifute.nm.othersserver.domain.dto.TrainMeetingEnrollDto;
import com.meifute.nm.others.business.training.service.TrainMeetingAffairsService;
import com.meifute.nm.others.business.training.service.TrainMeetingEnrollService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Classname OthersFeignController
 * @Description
 * @Date 2020-07-07 10:10
 * @Created by MR. Xb.Wu
 */
@Slf4j
@RestController
public class TrainFeignController implements OthersFeignService {

    @Autowired
    private TrainMeetingAffairsService trainMeetingAffairsService;
    @Autowired
    private TrainMeetingEnrollService trainMeetingEnrollService;

    @Override
    public boolean updateMeeting(@RequestBody TrainMeetingAffairsDto trainMeetingAffairsDto) {
        return trainMeetingAffairsService.updateMeeting(trainMeetingAffairsDto);
    }

    @Override
    public TrainMeetingEnrollDto queryByEnrollId(@RequestParam("id") String id) {
        return trainMeetingEnrollService.queryByEnrollId(id);
    }

    @Override
    public boolean updateEnrollById(@RequestBody TrainMeetingEnrollDto trainMeetingEnrollDto) {
        return trainMeetingEnrollService.updateMeetingEnroll(trainMeetingEnrollDto);
    }


}
