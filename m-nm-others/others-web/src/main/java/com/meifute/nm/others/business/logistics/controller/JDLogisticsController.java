package com.meifute.nm.others.business.logistics.controller;

import com.meifute.nm.otherscommon.base.BaseController;
import com.meifute.nm.otherscommon.base.MallResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Classname JDLogisticsController
 * @Description
 * @Date 2020-08-14 16:24
 * @Created by MR. Xb.Wu
 */
@Slf4j
@RestController
@RequestMapping("/logistics/jd")
@Api(tags = "京东物流接口")
public class JDLogisticsController extends BaseController {

    /**
     * 1。订单模块推单过来记录该笔订单。
     * 2。订单推送出去之后标记该笔订单为已推单的状态。
     * 3。轮询查询已推单的订单去京东查询物流单号并回调给订单模块，完成后标记为已更新物流单号。
     * 4。订单模块调我们关闭已推单的订单，如果关闭成功则相应的在标记该笔定单为已关闭。
     * 5。轮询查询已更新物流单号的订单，去京东查询签收状态，如果已签收则回调订单更新为已完成。如果超过15天还未签收则强制回调订单模块告知已完成。同时标记单子为已完成。
     */

    @ApiOperation(value = "京东物流推单", notes = "京东物流推单")
    @PostMapping("/push")
    public ResponseEntity<MallResponse<Boolean>> pushLogistics() {
        return ResponseEntity.ok(successResult(true));
    }


    @ApiOperation(value = "关闭已推单的订单", notes = "关闭已推单的订单")
    @PostMapping("/cancel")
    public ResponseEntity<MallResponse<Boolean>> cancelLogistics() {
        return ResponseEntity.ok(successResult(true));
    }


}
