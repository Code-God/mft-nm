package com.meifute.nm.others.business.training.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.meifute.nm.others.business.training.entity.TrainMeetingEnroll;
import com.meifute.nm.others.business.training.service.TrainMeetingAffairsService;
import com.meifute.nm.others.business.training.service.TrainMeetingEnrollService;
import com.meifute.nm.others.business.training.enums.CostBackStatusEnum;
import com.meifute.nm.others.utils.CurrentUserService;
import com.meifute.nm.otherscommon.base.BaseController;
import com.meifute.nm.otherscommon.base.MallResponse;
import com.meifute.nm.otherscommon.base.ResultResponse;
import com.meifute.nm.othersserver.domain.dto.MallUser;
import com.meifute.nm.othersserver.domain.dto.MyMeetingEnrollDto;
import com.meifute.nm.othersserver.domain.dto.TrainMeetingEnrollDto;
import com.meifute.nm.othersserver.domain.vo.QueryMeetingEnrollParam;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.Collections;

/**
 * @Classname TrainEnrollController
 * @Description
 * @Date 2020-07-08 13:55
 * @Created by MR. Xb.Wu
 */
@RequestMapping("/bgw/training/enroll")
@RestController
@Api(tags = "会务培训活动报名接口")
public class TrainMeetingEnrollController extends BaseController {

    @Autowired
    private TrainMeetingEnrollService trainMeetingEnrollService;
    @Autowired
    private TrainMeetingAffairsService trainMeetingAffairsService;
    @Autowired
    private CurrentUserService currentUserService;

    @ApiOperation(value = "报名", notes = "报名")
    @GetMapping(value = "/enter")
    public ResponseEntity<MallResponse<TrainMeetingEnroll>> enrollMeeting(@RequestParam("meetingId") String meetingId) {
        MallUser currentUser = currentUserService.getCurrentUser();
        TrainMeetingEnroll meetingEnroll = trainMeetingEnrollService.createMeetingEnroll(currentUser, meetingId);
        return ResponseEntity.ok(new ResultResponse<TrainMeetingEnroll>().successResult(meetingEnroll));
    }

    @ApiOperation(value = "查询报名列表", notes = "查询报名列表")
    @PostMapping(value = "/query/page")
    public ResponseEntity<MallResponse<Page<TrainMeetingEnroll>>> queryMeetingEnrollPage(@RequestBody QueryMeetingEnrollParam queryMeetingEnrollParam) {
        Page<TrainMeetingEnroll> page = trainMeetingEnrollService.queryMeetingEnrollPage(queryMeetingEnrollParam);
        return ResponseEntity.ok(new ResultResponse<Page<TrainMeetingEnroll>>().successResult(page));
    }

    @ApiOperation(value = "查询我的报名记录", notes = "查询我的报名记录")
    @PostMapping(value = "/query/my/page")
    public ResponseEntity<MallResponse<Page<MyMeetingEnrollDto>>> queryMyMeetingEnrollPage(@RequestBody QueryMeetingEnrollParam queryMeetingEnrollParam) {
        Page<MyMeetingEnrollDto> page = trainMeetingEnrollService.queryMyMeetingEnrollPage(queryMeetingEnrollParam);
        return ResponseEntity.ok(new ResultResponse<Page<MyMeetingEnrollDto>>().successResult(page));
    }

    @ApiOperation(value = "签到", notes = "签到")
    @GetMapping(value = "/sign/up")
    public ResponseEntity<MallResponse<Boolean>> signUp(@RequestParam("meetingId") String meetingId) {
        TrainMeetingEnrollDto enrollDto = trainMeetingEnrollService.signUp(meetingId);
        if (CostBackStatusEnum.COST_BACKING.getCode().equals(enrollDto.getCostBacked())) {
            trainMeetingAffairsService.sendCostEnrollDelayMQ(Collections.singletonList(enrollDto.getId()));
        }
        return ResponseEntity.ok(new ResultResponse<Boolean>().successResult(true));
    }


    @ApiOperation(value = "导出报名列表", notes = "导出报名列表")
    @PostMapping(value = "/export/enroll/info")
    public ResponseEntity<MallResponse<Boolean>> exportMeetingEnroll(@RequestBody QueryMeetingEnrollParam queryMeetingEnrollParam, HttpServletResponse response) {
        trainMeetingEnrollService.exportMeetingEnroll(queryMeetingEnrollParam, response);
        return ResponseEntity.ok(new ResultResponse<Boolean>().successResult(true));
    }

}
