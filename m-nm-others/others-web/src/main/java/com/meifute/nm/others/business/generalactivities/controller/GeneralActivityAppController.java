package com.meifute.nm.others.business.generalactivities.controller;

import com.meifute.nm.otherscommon.base.BaseController;
import com.meifute.nm.otherscommon.base.MallResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @Classname GeneralActivityAppController
 * @Description
 * @Date 2020-08-14 14:34
 * @Created by MR. Xb.Wu
 */
@Slf4j
@RestController
@RequestMapping("/general/activity/app")
@Api(tags = "通用活动APP接口")
public class GeneralActivityAppController extends BaseController {

    @ApiOperation(value = "查询活动基本配置项", notes = "查询活动基本配置项")
    @GetMapping("/activity/config/{activityId}")
    public ResponseEntity<MallResponse<String>> queryGeneralActivityConfig(@PathVariable("activityId") String activityId) {
        return ResponseEntity.ok(successResult(""));
    }

    @ApiOperation(value = "查询活动商品信息", notes = "查询活动商品信息")
    @GetMapping("/item/info/{activityId}")
    public ResponseEntity<MallResponse<String>> queryGeneralActivityItem(@PathVariable("activityId") String activityId) {
        return ResponseEntity.ok(successResult(""));
    }

    @ApiOperation(value = "同步活动商品库存信息", notes = "同步活动商品库存信息")
    @PostMapping("/item/sync/store")
    public ResponseEntity<MallResponse<String>> syncGeneralActivityItemStore() {
        return ResponseEntity.ok(successResult(""));
    }
}
