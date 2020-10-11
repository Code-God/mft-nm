package com.meifute.nm.others.business.training.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.meifute.nm.others.business.training.entity.AmoyActivitySignin;
import com.meifute.nm.others.business.training.entity.AmoyActivitySomeone;
import com.meifute.nm.others.business.training.service.AmoyActivityEnrollService;
import com.meifute.nm.others.business.training.service.AmoyActivitySigninService;
import com.meifute.nm.others.business.training.service.AmoyActivitySomeoneService;
import com.meifute.nm.otherscommon.base.BaseController;
import com.meifute.nm.otherscommon.base.MallResponse;
import com.meifute.nm.othersserver.domain.dto.NoveltyAgentDto;
import com.meifute.nm.othersserver.domain.dto.PayParam;
import com.meifute.nm.othersserver.domain.dto.PayResult;
import com.meifute.nm.othersserver.domain.dto.UnifiedWeChatDto;
import com.meifute.nm.othersserver.domain.param.BaseParam;
import com.meifute.nm.othersserver.domain.vo.amoy.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Classname AmoyActivityController
 * @Description 厦门市场活动
 * @Date 2020-08-19 15:49
 * @Created by MR. Xb.Wu
 */
@RequestMapping("/amoy/activity")
@RestController
@Api(tags = "厦门市场活动")
public class AmoyActivityController extends BaseController {

    @Autowired
    private AmoyActivityEnrollService amoyActivityEnrollService;
    @Autowired
    private AmoyActivitySigninService amoyActivitySigninService;
    @Autowired
    private AmoyActivitySomeoneService amoyActivitySomeoneService;

    @ApiOperation(value = "获取我的报名状态", notes = "获取我的报名状态")
    @GetMapping("/enroll/status")
    public ResponseEntity<MallResponse<MyEnrollStatus>> getMyEnrollStatus() {
        MyEnrollStatus myEnrollStatus = amoyActivityEnrollService.getMyEnrollStatus();
        return ResponseEntity.ok(successResult(myEnrollStatus));
    }

    @ApiOperation(value = "报名", notes = "报名")
    @PostMapping("/enroll")
    public ResponseEntity<MallResponse<EnrollVo>> enroll(@RequestBody AmoyActivityEnrollParam activityEnrollParam) {
        EnrollVo enroll = amoyActivityEnrollService.enroll(activityEnrollParam);
        return ResponseEntity.ok(successResult(enroll));
    }

    @ApiOperation(value = "获取立即支付参数", notes = "获取立即支付参数")
    @GetMapping("/get/now/pay/res")
    public ResponseEntity<MallResponse<EnrollVo>> nowPay(@RequestParam("enrollId") String enrollId) {
        EnrollVo enroll = amoyActivityEnrollService.nowPay(enrollId);
        return ResponseEntity.ok(successResult(enroll));
    }

    @ApiOperation(value = "查询我的报名记录列表", notes = "查询我的报名记录列表")
    @PostMapping("/my/enroll/page")
    public ResponseEntity<MallResponse<Page<AmoyActivityEnrollAppVO>>> getMyEnrollPage(@RequestBody BaseParam baseParam) {
        Page<AmoyActivityEnrollAppVO> myEnrollPage = amoyActivityEnrollService.getMyEnrollPage(baseParam);
        return ResponseEntity.ok(successResult(myEnrollPage));
    }

    @ApiOperation(value = "查询我的签到记录", notes = "查询我的签到记录")
    @GetMapping("/my/signin/record")
    public ResponseEntity<MallResponse<List<AmoyActivitySignin>>> getMyEnrollPage(@RequestParam("enrollId") String enrollId) {
        List<AmoyActivitySignin> mySigninRecord = amoyActivitySigninService.getMySigninRecord(enrollId);
        return ResponseEntity.ok(successResult(mySigninRecord));
    }

    @ApiOperation(value = "签到", notes = "签到")
    @GetMapping("/signin")
    public ResponseEntity<MallResponse<Boolean>> signIn(@RequestParam("signInPlace") Integer signInPlace,
                                                        @RequestParam(value = "longitudeAndLatitude", required = false) String longitudeAndLatitude,
                                                        @RequestParam("enrollId") String enrollId) {
        boolean b = amoyActivitySigninService.signIn(signInPlace, longitudeAndLatitude, enrollId);
        return ResponseEntity.ok(successResult(b));
    }

    @ApiOperation(value = "取消报名", notes = "取消报名")
    @GetMapping("/cancel/enroll")
    public ResponseEntity<MallResponse<Boolean>> cancelEnroll(@RequestParam("enrollId") String enrollId, @RequestParam("remark") String remark) {
        boolean b = amoyActivityEnrollService.cancelEnroll(enrollId, remark);
        return ResponseEntity.ok(successResult(b));
    }

    @ApiOperation(value = "后台分页查询厦门临时活动签到记录", notes = "分页查询厦门临时活动签到记录")
    @PostMapping("/query/enroll/page")
    public ResponseEntity<MallResponse<Page<QueryEnrollPage>>> queryEnrollPage(@RequestBody QueryEnrollVO queryEnrollVO) {
        Page<QueryEnrollPage> page = amoyActivityEnrollService.queryEnrollPage(queryEnrollVO);
        return ResponseEntity.ok(successResult(page));
    }

    @ApiOperation(value = "查询达标人数", notes = "查询达标人数")
    @PostMapping("/query/new/novelty/agent/page")
    public ResponseEntity<MallResponse<Page<NoveltyAgentDto>>> getNewNoveltyAgentsPage(@RequestBody QueryAgent queryAgent) {
        Page<NoveltyAgentDto> page = amoyActivityEnrollService.getNewNoveltyAgentsPage(queryAgent);
        return ResponseEntity.ok(successResult(page));
    }

    @ApiOperation(value = "8月招总代数", notes = "8月招总代数")
    @PostMapping("/query/end/novelty/agent/page")
    public ResponseEntity<MallResponse<Page<NoveltyAgentDto>>> getEndNoveltyAgentsPage(@RequestBody QueryAgent queryAgent) {
        Page<NoveltyAgentDto> page = amoyActivityEnrollService.getEndNoveltyAgentsPage(queryAgent);
        return ResponseEntity.ok(successResult(page));
    }

    @ApiOperation(value = "查询同行人", notes = "查询同行人")
    @PostMapping("/query/someone/page")
    public ResponseEntity<MallResponse<Page<AmoyActivitySomeone>>> getSomeOnePage(@RequestBody QueryAgent queryAgent) {
        Page<AmoyActivitySomeone> someOnePage = amoyActivitySomeoneService.getSomeOnePage(queryAgent);
        return ResponseEntity.ok(successResult(someOnePage));
    }

    @ApiOperation(value = "后台新增报名", notes = "后台新增报名")
    @PostMapping("/create/enroll/record")
    public ResponseEntity<MallResponse<Boolean>> createEnrollRecord(@RequestBody AmoyActivityEnrollParam activityEnrollParam) {
        boolean enrollRecord = amoyActivityEnrollService.createEnrollRecord(activityEnrollParam);
        return ResponseEntity.ok(successResult(enrollRecord));
    }

    @ApiOperation(value = "支付完成后接收支付模块回调接口", notes = "支付完成后接收支付模块回调接口", hidden = true)
    @PostMapping("/success/pay/enroll")
    public String successEnroll(@RequestBody PayResult payResult) {
        return amoyActivityEnrollService.successEnroll(payResult, 0);
    }

    @ApiOperation(value = "后台取消报名", notes = "后台取消报名")
    @GetMapping("/admin/cancel/enroll")
    public ResponseEntity<MallResponse<Boolean>> adminCancelEnroll(@RequestParam("enrollId") String enrollId, @RequestParam("remark") String remark) {
        boolean b = amoyActivityEnrollService.adminCancelEnroll(enrollId, remark);
        return ResponseEntity.ok(successResult(b));
    }
}
