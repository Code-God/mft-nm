package com.meifute.nm.others.business.training.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.meifute.nm.others.business.training.entity.TrainMeetingEnroll;
import com.meifute.nm.others.business.training.service.TrainMeetingEnrollService;
import com.meifute.nm.others.business.training.enums.MeetingStatusEnum;
import com.meifute.nm.others.business.training.enums.TrainBackFlagEnum;
import com.meifute.nm.othersserver.domain.dto.MyMeetingAffairsDto;
import com.meifute.nm.othersserver.domain.dto.TrainMeetingAffairsDto;
import com.meifute.nm.othersserver.domain.vo.QueryMeetingParam;
import com.meifute.nm.others.business.training.entity.TrainMeetingAffairs;
import com.meifute.nm.others.business.training.service.TrainMeetingAffairsService;
import com.meifute.nm.otherscommon.base.BaseController;
import com.meifute.nm.otherscommon.base.MallResponse;
import com.meifute.nm.otherscommon.base.ResultResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @Classname TrainEnrollController
 * @Description 会务活动
 * @Date 2020-07-06 15:58
 * @Created by MR. Xb.Wu
 */
@Slf4j
@RequestMapping("/bgw/training/meeting")
@RestController
@Api(tags = "会务培训活动接口")
public class TrainMeetingAffairsController extends BaseController {

    @Autowired
    private TrainMeetingAffairsService trainMeetingAffairsService;

    @Autowired
    private TrainMeetingEnrollService trainMeetingEnrollService;

    @ApiOperation(value = "创建会务活动", notes = "创建会务活动")
    @PostMapping(value = "/create/meeting")
    public ResponseEntity<MallResponse<Boolean>> createMeeting(@RequestBody TrainMeetingAffairsDto trainMeetingAffairsDto) {
        trainMeetingAffairsService.createMeeting(trainMeetingAffairsDto);
        return ResponseEntity.ok(new ResultResponse<Boolean>().successResult(true));
    }

    @ApiOperation(value = "更新会务活动", notes = "更新会务活动")
    @PostMapping(value = "/update/meeting")
    public ResponseEntity<MallResponse<Boolean>> updateMeeting(@RequestBody TrainMeetingAffairsDto trainMeetingAffairsDto) {
        trainMeetingAffairsService.updateMeeting(trainMeetingAffairsDto);
        return ResponseEntity.ok(new ResultResponse<Boolean>().successResult(true));
    }

    @ApiOperation(value = "查询会务活动列表", notes = "查询会务活动列表")
    @PostMapping(value = "/query/meeting/page")
    public ResponseEntity<MallResponse<Page<TrainMeetingAffairs>>> queryMeetingPage(@RequestBody QueryMeetingParam queryMeetingParam) {
        Page<TrainMeetingAffairs> page = trainMeetingAffairsService.queryMeetingPage(queryMeetingParam);
        return ResponseEntity.ok(new ResultResponse<Page<TrainMeetingAffairs>>().successResult(page));
    }

    @ApiOperation(value = "查询会务活动列表（app使用）", notes = "查询会务活动列表 （app使用）")
    @PostMapping(value = "/query/my/meeting/page")
    public ResponseEntity<MallResponse<Page<MyMeetingAffairsDto>>> queryMeetingPageToApp(@RequestBody QueryMeetingParam queryMeetingParam) {
        Page<MyMeetingAffairsDto> page = trainMeetingAffairsService.queryMeetingPageToApp(queryMeetingParam);
        return ResponseEntity.ok(new ResultResponse<Page<MyMeetingAffairsDto>>().successResult(page));
    }

    @ApiOperation(value = "发布/取消发布会务活动", notes = "发布/取消发布会务活动")
    @GetMapping(value = "/release/or/close/meeting")
    public ResponseEntity<MallResponse<Boolean>> releaseOrCloseMeeting(@RequestParam("id") String id, @RequestParam("status") String status) {
        //发布
        if (MeetingStatusEnum.AC_RELEASED.getCode().equals(status)) {
            trainMeetingAffairsService.releaseMeeting(id, status);
        }
        //取消
        if (MeetingStatusEnum.AC_CLOSED.getCode().equals(status)) {
            TrainMeetingAffairs affairs = trainMeetingAffairsService.queryById(id);
            List<TrainMeetingEnroll> dto = null;
            if (TrainBackFlagEnum.CAN_BACK.getCode() == affairs.getCostBack()) {
                dto = trainMeetingEnrollService.queryNoBackList(id);
            }
            boolean b = trainMeetingAffairsService.closeMeeting(affairs, status, dto);
            if (b && dto != null) {
                List<String> ids = dto.stream().map(TrainMeetingEnroll::getId).collect(Collectors.toList());
                trainMeetingAffairsService.sendCostEnrollDelayMQ(ids);
            }
        }
        return ResponseEntity.ok(new ResultResponse<Boolean>().successResult(true));
    }

    @ApiOperation(value = "查询会务详情", notes = "查询会务详情")
    @GetMapping(value = "/query/meeting/detail")
    public ResponseEntity<MallResponse<MyMeetingAffairsDto>> queryMeetingDetail(@RequestParam("id") String id) {
        MyMeetingAffairsDto page = trainMeetingAffairsService.queryMeetingDetail(id);
        return ResponseEntity.ok(new ResultResponse<MyMeetingAffairsDto>().successResult(page));
    }

}
