package com.meifute.nm.others.business.generalactivities.controller;

import com.meifute.nm.otherscommon.base.BaseController;
import com.meifute.nm.otherscommon.base.MallResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

/**
 * @Classname GeneralActivityAdminController
 * @Description
 * @Date 2020-08-14 14:34
 * @Created by MR. Xb.Wu
 */
@Slf4j
@RestController
@RequestMapping("/general/activity/admin")
@Api(tags = "通用活动后台接口")
public class GeneralActivityAdminController extends BaseController {


    @ApiOperation(value = "新增活动", notes = "新增活动")
    @PostMapping("/activity")
    public ResponseEntity<MallResponse<Boolean>> createGeneralActivity() {
        return ResponseEntity.ok(successResult(true));
    }

    @ApiOperation(value = "分页查询活动主列表", notes = "分页查询活动主列表")
    @PostMapping("/activity/page")
    public ResponseEntity<MallResponse<Boolean>> queryGeneralActivityPage() {
        return ResponseEntity.ok(successResult(true));
    }

    @ApiOperation(value = "修改活动", notes = "修改活动")
    @PutMapping("/activity")
    public ResponseEntity<MallResponse<Boolean>> editGeneralActivity() {
        return ResponseEntity.ok(successResult(true));
    }

    @ApiOperation(value = "删除活动", notes = "删除活动")
    @DeleteMapping("/activity/{id}")
    public ResponseEntity<MallResponse<Boolean>> deleteGeneralActivity(@PathVariable("id") String id) {
        return ResponseEntity.ok(successResult(true));
    }

    @ApiOperation(value = "新增活动产品SPU", notes = "新增活动产品SPU")
    @PostMapping("/item")
    public ResponseEntity<MallResponse<Boolean>> createGeneralActivityItem() {
        return ResponseEntity.ok(successResult(true));
    }

    @ApiOperation(value = "编辑活动产品SPU", notes = "编辑活动产品SPU")
    @PutMapping("/item")
    public ResponseEntity<MallResponse<Boolean>> editGeneralActivityItem() {
        return ResponseEntity.ok(successResult(true));
    }

    @ApiOperation(value = "分页获取活动产品SPU主列表", notes = "分页获取活动产品SPU主列表")
    @PostMapping("/item/page")
    public ResponseEntity<MallResponse<Boolean>> queryGeneralActivityItemPage() {
        return ResponseEntity.ok(successResult(true));
    }

    @ApiOperation(value = "获取活动产品SPU所有列表", notes = "获取活动产品SPU所有列表")
    @GetMapping("/item/list")
    public ResponseEntity<MallResponse<Boolean>> queryGeneralActivityItemList() {
        return ResponseEntity.ok(successResult(true));
    }

    @ApiOperation(value = "删除活动产品SPU", notes = "删除活动产品SPU")
    @DeleteMapping("/item/{id}")
    public ResponseEntity<MallResponse<Boolean>> deleteAcItem(@PathVariable("id") String id) {
        return ResponseEntity.ok(successResult(true));
    }

    @ApiOperation(value = "新增活动指定人员", notes = "新增活动指定人员")
    @PostMapping("/appoint/user")
    public ResponseEntity<MallResponse<Boolean>> createAppointUser() {
        return ResponseEntity.ok(successResult(true));
    }

    @ApiOperation(value = "删除活动指定人员", notes = "删除活动指定人员")
    @DeleteMapping("/appoint/user/{id}")
    public ResponseEntity<MallResponse<Boolean>> deleteAppointUser(@PathVariable("id") String id) {
        return ResponseEntity.ok(successResult(true));
    }

    @ApiOperation(value = "分页查询活动指定人员", notes = "分页查询活动指定人员")
    @PostMapping("/appoint/user/page")
    public ResponseEntity<MallResponse<Boolean>> createAcUser() {
        return ResponseEntity.ok(successResult(true));
    }

    @ApiOperation(value = "导入活动指定人员", notes = "导入活动指定人员")
    @PostMapping("/appoint/user/import/{activityId}")
    public ResponseEntity<MallResponse<String>> importAppointUser(@RequestBody MultipartFile file, @PathVariable("activityId") String activityId, HttpServletResponse response) {
        return ResponseEntity.ok(successResult(""));
    }

    @ApiOperation(value = "获取指定人员导入结果", notes = "获取指定人员导入结果")
    @GetMapping("/appoint/user/import/result/{key}")
    public ResponseEntity<MallResponse<String>> importAppointUser(@PathVariable("key") String key, HttpServletResponse response) {
        return ResponseEntity.ok(successResult("success"));
    }

}
