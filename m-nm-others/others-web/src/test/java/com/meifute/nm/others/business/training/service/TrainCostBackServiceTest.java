package com.meifute.nm.others.business.training.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.meifute.nm.others.business.training.entity.TrainCostBack;
import com.meifute.nm.others.business.training.entity.TrainMeetingAffairs;
import com.meifute.nm.others.business.training.service.impl.TrainCostBackServiceImpl;
import com.meifute.nm.others.business.training.enums.CostBackStatusEnum;
import com.meifute.nm.others.business.training.enums.TrainBackFlagEnum;
import com.meifute.nm.others.feignclient.PayFeignClient;
import com.meifute.nm.otherscommon.enums.SysErrorEnums;
import com.meifute.nm.otherscommon.exception.BusinessException;
import com.meifute.nm.othersserver.domain.dto.TrainMeetingEnrollDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.Arrays;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


/**
 * @Classname TrainCostBackServiceTest
 * @Description
 * @Date 2020-07-17 15:04
 * @Created by MR. Xb.Wu
 */
class TrainCostBackServiceTest {

    @InjectMocks
    private TrainCostBackServiceImpl trainCostBackService;
    @Mock
    private TrainMeetingEnrollService trainMeetingEnrollService;
    @Mock
    private TrainMeetingAffairsService trainMeetingAffairsService;
    @Mock
    private PayFeignClient payFeignClient;
    @Mock
    private ServiceImpl iService;
    @Mock
    private BaseMapper baseMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void backCostOne_not_back() {
        String enrollId = "1";
        String remark = "退费";

        TrainMeetingEnrollDto dto = new TrainMeetingEnrollDto();
        dto.setCostBacked(CostBackStatusEnum.COST_BACKED.getCode());

        when(trainMeetingEnrollService.queryByEnrollId(enrollId)).thenReturn(dto);

        try {
            trainCostBackService.backCostOne(enrollId, remark);
        } catch (BusinessException e) {
            Assertions.assertEquals(SysErrorEnums.REFUNDED.getErrorMessage(), e.getMsg());
        }
    }

    @Test
    void backCostOne() {
        String enrollId = "1";
        String remark = "退费";
        String meetingId = "12";

        TrainMeetingEnrollDto dto = new TrainMeetingEnrollDto();
        dto.setCostBacked(CostBackStatusEnum.COST_NOT_BACKED.getCode());

        TrainMeetingAffairs affairs = new TrainMeetingAffairs();
        affairs.setId(meetingId);
        affairs.setCostBack(TrainBackFlagEnum.CAN_BACK.getCode());

        when(trainMeetingAffairsService.queryById(any())).thenReturn(affairs);

        when(trainMeetingEnrollService.queryByEnrollId(enrollId)).thenReturn(dto);

        when(payFeignClient.signUpBackCost(enrollId)).thenReturn(true);

        when(trainMeetingEnrollService.updateMeetingEnroll(dto)).thenReturn(true);

        when(baseMapper.selectList(any())).thenReturn(null);

        when(iService.save(any())).thenReturn(true);

        trainCostBackService.backCostOne(enrollId, remark);
        verify(trainMeetingEnrollService, times(1)).updateMeetingEnroll(dto);
    }



    @Test
    void queryBackCostRecordByEnrollId() {
        TrainCostBack back = new TrainCostBack();
        back.setId("111");
        when(baseMapper.selectList(any())).thenReturn(Arrays.asList(back));
        TrainCostBack costBack = trainCostBackService.queryBackCostRecordByEnrollId("111");
        Assertions.assertEquals(costBack, back);
    }
}