package com.meifute.nm.others.business.training.controller;

import com.meifute.nm.others.business.training.entity.TrainCostBack;
import com.meifute.nm.others.business.training.service.TrainCostBackService;
import com.meifute.nm.otherscommon.base.BaseController;
import com.meifute.nm.otherscommon.base.MallResponse;
import com.meifute.nm.otherscommon.base.ResultResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @Classname TrainCostBackController
 * @Description
 * @Date 2020-07-08 18:14
 * @Created by MR. Xb.Wu
 */
@RequestMapping("/bgw/training/cost")
@RestController
@Api(tags = "会务培训活动退费接口")
public class TrainCostBackController extends BaseController {

    @Autowired
    private TrainCostBackService trainCostBackService;

    @ApiOperation(value = "退费", notes = "退费")
    @GetMapping(value = "/back")
    public ResponseEntity<MallResponse<Boolean>> backCostOne(@RequestParam("enrollId") String enrollId, @RequestParam("remark") String remark) {
        trainCostBackService.backCostOne(enrollId, remark);
        return ResponseEntity.ok(new ResultResponse<Boolean>().successResult(true));
    }

    @ApiOperation(value = "查询退费记录", notes = "查询退费记录")
    @GetMapping(value = "/query/back/record")
    public ResponseEntity<MallResponse<TrainCostBack>> queryBackCostRecordByEnrollId(@RequestParam("enrollId") String enrollId) {
        TrainCostBack costBack = trainCostBackService.queryBackCostRecordByEnrollId(enrollId);
        return ResponseEntity.ok(new ResultResponse<TrainCostBack>().successResult(costBack));
    }
}
