package com.meifute.nm.auth.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.meifute.nm.auth.base.BaseController;
import com.meifute.nm.auth.base.BaseResponse;
import com.meifute.nm.auth.base.MallResponse;
import com.meifute.nm.auth.base.ResultResponse;
import com.meifute.nm.auth.entity.MallUser;
import com.meifute.nm.auth.entity.dto.MallUserDTO;
import com.meifute.nm.auth.entity.vo.MallUserChangePasswordVO;
import com.meifute.nm.auth.entity.vo.MallUserQueryVO;
import com.meifute.nm.auth.entity.vo.UserAddVO;
import com.meifute.nm.auth.entity.vo.UserUpdateVO;
import com.meifute.nm.auth.service.IMallUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author chen
 * @since 2020-08-17
 */
@RestController
@RequestMapping("/auth/mall/user")
@Api(tags = "用户")
public class MallUserController extends BaseController {

    @Autowired
    IMallUserService mallUserService;

    @ApiOperation(value = "新增用户", notes = "新增用户")
    @PostMapping(value = "/add")
    public ResponseEntity<MallResponse<Boolean>> addUser(@RequestBody UserAddVO userAddVO) {
        return ResponseEntity.ok(new ResultResponse<Boolean>().successResult(mallUserService.addUser(userAddVO)));
    }

    @ApiOperation(value = "更新用户", notes = "更新用户")
    @PostMapping(value = "/update")
    public ResponseEntity<MallResponse<Boolean>> updateUser(@RequestBody UserUpdateVO userUpdateVO) {
        return ResponseEntity.ok(new ResultResponse<Boolean>().successResult(mallUserService.updateUser(userUpdateVO)));
    }

    @ApiOperation(value = "删除用户", notes = "删除用户")
    @DeleteMapping(value = "/remove")
    public ResponseEntity<MallResponse<Boolean>> removeUser(@RequestParam Long id) {
        return ResponseEntity.ok(new ResultResponse<Boolean>().successResult(mallUserService.removeUser(id)));
    }

    @ApiOperation(value = "修改密码", notes = "修改密码")
    @PostMapping(value = "/change/password")
    public ResponseEntity<MallResponse<BaseResponse>> changePassword(@RequestBody MallUserChangePasswordVO userChangePasswordVO) {
        return ResponseEntity.ok(new ResultResponse<BaseResponse>().successResult(mallUserService.changePassword(userChangePasswordVO)));
    }


    @ApiOperation(value = "当前操作人信息", notes = "当前操作人信息")
    @GetMapping(value = "/current/user/info")
    public ResponseEntity<MallResponse<MallUserDTO>> currentOperatorInfo() {
        return ResponseEntity.ok(new ResultResponse<MallUserDTO>().successResult(mallUserService.changePassword()));
    }

//    @ApiOperation(value = "强制用户退出", notes = "强制用户退出")
//    @GetMapping(value = "/logout/user/id")
//    public ResponseEntity<MallResponse<Boolean>> logoutByUserId(@RequestParam Long id) {
//        return ResponseEntity.ok(new ResultResponse<Boolean>().successResult(true));
//    }

    @ApiOperation(value = "查询用户列表", notes = "查询用户列表")
    @PostMapping(value = "/page")
    public ResponseEntity<MallResponse<Page<MallUserDTO>>> queryByParam(@RequestBody MallUserQueryVO mallUserQueryVO) {
        return ResponseEntity.ok(new ResultResponse<Page<MallUserDTO>>().successResult(mallUserService.queryByParam(mallUserQueryVO)));
    }

    @ApiOperation(value = "查询用户详情", notes = "查询用户详情")
    @GetMapping(value = "/detail")
    public ResponseEntity<MallResponse<MallUserDTO>> detail(@RequestParam String id) {
        return ResponseEntity.ok(new ResultResponse<MallUserDTO>().successResult(mallUserService.userDetail(id)));
    }


}
