package com.meifute.nm.others.business.itemtrial.controller;

import com.meifute.nm.otherscommon.base.BaseController;
import com.meifute.nm.otherscommon.base.MallResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @Classname TrialItemAppController
 * @Description
 * @Date 2020-08-14 10:56
 * @Created by MR. Xb.Wu
 */
@Slf4j
@RestController
@RequestMapping("/trial/item/app")
@Api(tags = "产品试用APP接口")
public class TrialItemAppController extends BaseController {

    @ApiOperation(value = "分页获取新品试用活动列表", notes = "分页获取新品试用活动列表")
    @PostMapping("/activity/page")
    public ResponseEntity<MallResponse<Boolean>> getActivityListToApp() {
        return ResponseEntity.ok(successResult(true));
    }

    @ApiOperation(value = "根据活动id获取新品试用活动详情", notes = "根据活动id获取新品试用活动详情")
    @GetMapping("/activity/detail/{id}")
    public ResponseEntity<MallResponse<Boolean>> getActivityById(@PathVariable("id") String id) {
        return ResponseEntity.ok(successResult(true));
    }

    @ApiOperation(value = "提交试用试用申请", notes = "提交试用试用申请")
    @PostMapping("/apply/submit")
    public ResponseEntity<MallResponse<Boolean>> submitApplyFprTrial() {
        return ResponseEntity.ok(successResult(true));
    }

    @ApiOperation(value = "分页获取我的试用申请列表", notes = "分页获取我的试用申请列表")
    @PostMapping("/apply/my/page")
    public ResponseEntity<MallResponse<Boolean>> queryMyApplyForPage() {
        return ResponseEntity.ok(successResult(true));
    }

    @ApiOperation(value = "提交试用问卷报告", notes = "提交试用问卷报告")
    @PostMapping("/questionnaire/report")
    public ResponseEntity<MallResponse<Boolean>> submitQuestionnaireReport() {
        return ResponseEntity.ok(successResult(true));
    }

    @ApiOperation(value = "根据申请id查询我的试用报告", notes = "根据申请id查询我的试用报告")
    @GetMapping("/questionnaire/my/report/{applyId}")
    public ResponseEntity<MallResponse<Boolean>> queryQuestionnaireReportByApplyId(@PathVariable("applyId") String applyId) {
        return ResponseEntity.ok(successResult(true));
    }

    @ApiOperation(value = "缓存试用报告草稿", notes = "缓存试用报告草稿")
    @PostMapping("/questionnaire/cache/report/context")
    public ResponseEntity<MallResponse<Boolean>> addDraftContext() {
        return ResponseEntity.ok(successResult(true));
    }
}
