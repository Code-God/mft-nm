package com.meifute.nm.others.business.itemtrial.controller;

import com.meifute.nm.otherscommon.base.BaseController;
import com.meifute.nm.otherscommon.base.MallResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


/**
 * @Classname TrialItemController
 * @Description
 * @Date 2020-08-14 10:51
 * @Created by MR. Xb.Wu
 */
@Slf4j
@RestController
@RequestMapping("/trial/item/admin")
@Api(tags = "产品试用后台接口")
public class TrialItemAdminController extends BaseController {


    @ApiOperation(value = "创建试用产品活动配置", notes = "创建试用产品活动配置")
    @PostMapping("/activity/config")
    public ResponseEntity<MallResponse<Boolean>> createTrialItemConfig() {
        return ResponseEntity.ok(successResult(true));
    }

    @ApiOperation(value = "更新试用产品活动配置", notes = "更新试用产品活动配置")
    @PutMapping("/activity/config")
    public ResponseEntity<MallResponse<Boolean>> editTrialItemConfig() {
        return ResponseEntity.ok(successResult(true));
    }

    @ApiOperation(value = "分页获取试用产品活动配置列表", notes = "分页获取试用产品活动配置列表")
    @PostMapping("/activity/config/page")
    public ResponseEntity<MallResponse<Boolean>> queryTrialItemConfigPage() {
        return ResponseEntity.ok(successResult(true));
    }

    @ApiOperation(value = "创建试用产品", notes = "创建试用产品")
    @PostMapping("/item")
    public ResponseEntity<MallResponse<Boolean>> createTrialItem() {
        return ResponseEntity.ok(successResult(true));
    }

    @ApiOperation(value = "更新试用产品", notes = "更新试用产品")
    @PutMapping("/item")
    public ResponseEntity<MallResponse<Boolean>> editTrialItem() {
        return ResponseEntity.ok(successResult(true));
    }

    @ApiOperation(value = "分页获取试用产品列表", notes = "分页获取试用产品列表")
    @PostMapping("/item/page")
    public ResponseEntity<MallResponse<Boolean>> queryTrialItemPage() {
        return ResponseEntity.ok(successResult(true));
    }

    @ApiOperation(value = "创建试用产品问卷模版", notes = "创建试用产品问卷模版")
    @PostMapping("/questionnaire/template")
    public ResponseEntity<MallResponse<Boolean>> createQuestionnaireTemplate() {
        return ResponseEntity.ok(successResult(true));
    }

    @ApiOperation(value = "更新试用产品问卷模版", notes = "更新试用产品问卷模版")
    @PutMapping("/questionnaire/template")
    public ResponseEntity<MallResponse<Boolean>> editQuestionnaireTemplate() {
        return ResponseEntity.ok(successResult(true));
    }

    @ApiOperation(value = "分页获取试用产品问卷模版列表", notes = "分页获取试用产品问卷模版列表")
    @PostMapping("/questionnaire/template/page")
    public ResponseEntity<MallResponse<Boolean>> queryQuestionnaireTemplatePage() {
        return ResponseEntity.ok(successResult(true));
    }

    @ApiOperation(value = "根据id查看试用产品问卷模版详情", notes = "根据id查看试用产品问卷模版详情")
    @GetMapping("/questionnaire/template/detail/{id}")
    public ResponseEntity<MallResponse<Boolean>> queryQuestionnaireTemplateDetailById(@PathVariable("id") String id) {
        return ResponseEntity.ok(successResult(true));
    }

    @ApiOperation(value = "分页获取试用产品问卷报告记录列表", notes = "分页获取试用产品问卷报告记录列表")
    @PostMapping("/questionnaire/report/page")
    public ResponseEntity<MallResponse<Boolean>> queryQuestionnaireReportPage() {
        return ResponseEntity.ok(successResult(true));
    }

    @ApiOperation(value = "根据问卷报告id查看问卷报告记录详情", notes = "根据问卷报告id查看问卷报告记录详情")
    @GetMapping("/questionnaire/report/detail/{id}")
    public ResponseEntity<MallResponse<Boolean>> queryQuestionnaireReportDetailById(@PathVariable("id") String id) {
        return ResponseEntity.ok(successResult(true));
    }

    @ApiOperation(value = "给问卷评分", notes = "给问卷评分")
    @PostMapping("/questionnaire/report/scoring")
    public ResponseEntity<MallResponse<Boolean>> establishScoring() {
        return ResponseEntity.ok(successResult(true));
    }

    @ApiOperation(value = "审核试用申请", notes = "审核试用申请")
    @PostMapping("/apply/verify")
    public ResponseEntity<MallResponse<Boolean>> verifyApplyForTrial() {
        return ResponseEntity.ok(successResult(true));
    }

    @ApiOperation(value = "批量审核试用申请", notes = "批量审核试用申请")
    @PostMapping("/apply/verify/batch")
    public ResponseEntity<MallResponse<Boolean>> verifyApplyForTrialBatch() {
        return ResponseEntity.ok(successResult(true));
    }

    @ApiOperation(value = "分页获取试用申请列表", notes = "分页获取试用申请列表")
    @PostMapping("/apply/page")
    public ResponseEntity<MallResponse<Boolean>> getApplyForProductPage() {
        return ResponseEntity.ok(successResult(true));
    }

    @ApiOperation(value = "获取试用申请各个状态的数量", notes = "获取试用申请各个状态的数量")
    @GetMapping("/apply/status/num")
    public ResponseEntity<MallResponse<Boolean>> getMyApplyNumberForPage() {
        return ResponseEntity.ok(successResult(true));
    }

}
