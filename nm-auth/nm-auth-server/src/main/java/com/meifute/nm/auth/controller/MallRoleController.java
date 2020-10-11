package com.meifute.nm.auth.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.meifute.nm.auth.base.BaseController;
import com.meifute.nm.auth.base.MallResponse;
import com.meifute.nm.auth.base.ResultResponse;
import com.meifute.nm.auth.entity.MallRole;
import com.meifute.nm.auth.entity.dto.MallRoleDTO;
import com.meifute.nm.auth.entity.vo.MallRoleQueryVO;
import com.meifute.nm.auth.entity.vo.RoleAddVO;
import com.meifute.nm.auth.entity.vo.RoleUpdateVO;
import com.meifute.nm.auth.service.IMallRoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.ibatis.annotations.Delete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 角色表 前端控制器
 * </p>
 *
 * @author chen
 * @since 2020-08-17
 */
@RestController
@RequestMapping("/auth/mall/role")
@Api(tags = "角色")
public class MallRoleController extends BaseController {

    @Autowired
    IMallRoleService mallRoleService;

    @ApiOperation(value = "新增角色", notes = "新增角色")
    @PostMapping(value = "/add")
    public ResponseEntity<MallResponse<Boolean>> addRole(@RequestBody RoleAddVO roleAddVO) {
        return ResponseEntity.ok(new ResultResponse<Boolean>().successResult(mallRoleService.addRole(roleAddVO)));
    }

    @ApiOperation(value = "更新角色", notes = "更新角色")
    @PostMapping(value = "/update")
    public ResponseEntity<MallResponse<Boolean>> updateRole(@RequestBody RoleUpdateVO roleUpdateVO) {
        return ResponseEntity.ok(new ResultResponse<Boolean>().successResult(mallRoleService.updateRole(roleUpdateVO)));
    }

    @ApiOperation(value = "删除角色", notes = "删除角色")
    @DeleteMapping(value = "/remove")
    public ResponseEntity<MallResponse<Boolean>> removeRole(@RequestParam Long id) {
        return ResponseEntity.ok(new ResultResponse<Boolean>().successResult(mallRoleService.removeRole(id)));
    }

    @ApiOperation(value = "分页查询角色", notes = "分页查询角色")
    @PostMapping(value = "/page")
    public ResponseEntity<MallResponse<Page<MallRoleDTO>>> pageRole(@RequestBody MallRoleQueryVO mallRoleQueryVO) {
        return ResponseEntity.ok(new ResultResponse<Page<MallRoleDTO>>().successResult(mallRoleService.pageRole(mallRoleQueryVO)));
    }

    @ApiOperation(value = "删除角色", notes = "删除角色")
    @GetMapping(value = "/detail")
    public ResponseEntity<MallResponse<MallRoleDTO>> detailRole(@RequestParam Long id) {
        return ResponseEntity.ok(new ResultResponse<MallRoleDTO>().successResult(mallRoleService.detailRole(id)));
    }

}
