package com.meifute.nm.others.business.userfeedback.controller;

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
 * @Classname UserFeedBackController
 * @Description
 * @Date 2020-08-14 15:42
 * @Created by MR. Xb.Wu
 */
@Slf4j
@RestController
@RequestMapping("/user/feedback")
@Api(tags = "用户反馈接口")
public class UserFeedBackController extends BaseController {

    @ApiOperation(value = "提交用户反馈信息", notes = "提交用户反馈信息")
    @PostMapping("/submit")
    public ResponseEntity<MallResponse<Boolean>> createUserFeedback() {
        return ResponseEntity.ok(successResult(true));
    }

    @ApiOperation(value = "分页查询用户反馈信息", notes = "分页查询用户反馈信息")
    @PostMapping("/page")
    public ResponseEntity<MallResponse<Boolean>> queryUserFeedbackPage() {
        return ResponseEntity.ok(successResult(true));
    }
}
