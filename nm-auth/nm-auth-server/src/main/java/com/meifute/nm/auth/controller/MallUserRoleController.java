package com.meifute.nm.auth.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.meifute.nm.auth.base.BaseController;
import com.meifute.nm.auth.base.MallResponse;
import com.meifute.nm.auth.base.ResultResponse;
import com.meifute.nm.auth.entity.MallRole;
import com.meifute.nm.auth.entity.MallUserRole;
import com.meifute.nm.auth.entity.dto.MallRoleDTO;
import com.meifute.nm.auth.service.IMallUserRoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 用户角色表 前端控制器
 * </p>
 *
 * @author chen
 * @since 2020-08-17
 */
@RestController
@RequestMapping("/auth/mall/user/role")
@Api(tags = "用户角色")
public class MallUserRoleController extends BaseController {

    @Autowired
    IMallUserRoleService mallUserRoleService;

    @ApiOperation(value = "新增用户角色", notes = "新增用户角色")
    @GetMapping(value = "/add")
    public ResponseEntity<MallResponse<Boolean>> addUserRole(@RequestParam Long userId,@RequestParam Long roleId) {
        return ResponseEntity.ok(new ResultResponse<Boolean>().successResult(mallUserRoleService.save(MallUserRole.builder().userId(userId).roleId(roleId).build())));
    }

    @ApiOperation(value = "删除用户角色", notes = "删除用户角色")
    @DeleteMapping(value = "/remove")
    public ResponseEntity<MallResponse<Boolean>> removeUserRole(@RequestParam Long userId,@RequestParam Long roleId) {
        return ResponseEntity.ok(new ResultResponse<Boolean>().successResult(mallUserRoleService.removeUserRole(userId,roleId)));
    }

    @ApiOperation(value = "用户角色列表", notes = "用户角色列表")
    @GetMapping(value = "/list")
    public ResponseEntity<MallResponse<List<MallRoleDTO>>> listUserRole(@RequestParam Long userId) {
        return ResponseEntity.ok(new ResultResponse<List<MallRoleDTO>>().successResult(mallUserRoleService.listUserRole(userId)));
    }

}
