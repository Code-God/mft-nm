package com.meifute.nm.auth.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.meifute.nm.auth.base.BaseController;
import com.meifute.nm.auth.base.MallResponse;
import com.meifute.nm.auth.base.ResultResponse;
import com.meifute.nm.auth.entity.MallRolePermission;
import com.meifute.nm.auth.entity.MallUserRole;
import com.meifute.nm.auth.entity.dto.MallPermissionDTO;
import com.meifute.nm.auth.service.IMallRolePermisssionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 角色权限表 前端控制器
 * </p>
 *
 * @author chen
 * @since 2020-08-17
 */
@RestController
@RequestMapping("/auth/mall/role/permission")
@Api(tags = "角色权限")
public class MallRolePermissionController extends BaseController {

    @Autowired
    IMallRolePermisssionService mallRolePermissionService;


    @ApiOperation(value = "新增角色权限", notes = "新增角色权限")
    @GetMapping(value = "/add")
    public ResponseEntity<MallResponse<Boolean>> addRolePermission(@RequestParam Long roleId,@RequestParam Long permissionId) {
        return ResponseEntity.ok(new ResultResponse<Boolean>().successResult(mallRolePermissionService.save(MallRolePermission.builder().roleId(roleId).permissionId(permissionId).build())));
    }

    @ApiOperation(value = "删除角色权限", notes = "删除角色权限")
    @DeleteMapping(value = "/remove")
    public ResponseEntity<MallResponse<Boolean>> removeRolePermission(@RequestParam Long roleId,@RequestParam Long permissionId) {
        return ResponseEntity.ok(new ResultResponse<Boolean>().successResult(mallRolePermissionService.removeRolePermission(roleId,permissionId)));
    }

    @ApiOperation(value = "角色权限列表", notes = "角色权限列表")
    @GetMapping(value = "/list")
    public ResponseEntity<MallResponse<List<MallPermissionDTO>>> listRolePermission(@RequestParam Long roleId) {
        return ResponseEntity.ok(new ResultResponse<List<MallPermissionDTO>>().successResult(mallRolePermissionService.listRolePermission(roleId)));
    }

}
