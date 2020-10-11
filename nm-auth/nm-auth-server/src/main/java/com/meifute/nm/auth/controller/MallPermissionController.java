package com.meifute.nm.auth.controller;


import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.meifute.nm.auth.base.BaseController;
import com.meifute.nm.auth.base.MallResponse;
import com.meifute.nm.auth.base.ResultResponse;
import com.meifute.nm.auth.entity.MallPermission;
import com.meifute.nm.auth.entity.dto.MallPermissionDTO;
import com.meifute.nm.auth.entity.vo.PermissionAddVO;
import com.meifute.nm.auth.entity.vo.PermissionQueryVO;
import com.meifute.nm.auth.entity.vo.PermissionUpdateVO;
import com.meifute.nm.auth.service.IMallPermissionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 权限表 前端控制器
 * </p>
 *
 * @author chen
 * @since 2020-08-17
 */
@RestController
@RequestMapping("/auth/mall/permission")
@Api(tags = "权限")
public class MallPermissionController extends BaseController {

    @Autowired
    IMallPermissionService mallPermissionService;

    @ApiOperation(value = "新增权限", notes = "新增权限")
    @PostMapping(value = "/add")
    public ResponseEntity<MallResponse<Boolean>> addPermission(@RequestBody PermissionAddVO permissionAddVO) {
        return ResponseEntity.ok(new ResultResponse<Boolean>().successResult(mallPermissionService.addPermission(permissionAddVO)));
    }

    @ApiOperation(value = "更新权限", notes = "更新权限")
    @PostMapping(value = "/update")
    public ResponseEntity<MallResponse<Boolean>> updatePermission(@RequestBody PermissionUpdateVO permissionUpdateVO) {
        return ResponseEntity.ok(new ResultResponse<Boolean>().successResult(mallPermissionService.updatePermission(permissionUpdateVO)));
    }

    @ApiOperation(value = "删除权限", notes = "删除权限")
    @DeleteMapping(value = "/remove")
    public ResponseEntity<MallResponse<Boolean>> removePermission(@RequestParam Long id) {
        return ResponseEntity.ok(new ResultResponse<Boolean>().successResult(mallPermissionService.removePermission(id)));
    }

    @ApiOperation(value = "分页查询权限", notes = "分页查询权限")
    @PostMapping(value = "/page")
    public ResponseEntity<MallResponse<Page<MallPermissionDTO>>> pagePermission(@RequestBody PermissionQueryVO permissionQueryVO) {
        return ResponseEntity.ok(new ResultResponse<Page<MallPermissionDTO>>().successResult(mallPermissionService.pagePermission(permissionQueryVO)));
    }

    @ApiOperation(value = "查询权限详情", notes = "查询权限详情")
    @GetMapping(value = "/detail")
    public ResponseEntity<MallResponse<MallPermissionDTO>> detailPermission(@RequestParam Long id) {
        return ResponseEntity.ok(new ResultResponse<MallPermissionDTO>().successResult(mallPermissionService.detailPermission(id)));
    }
}
