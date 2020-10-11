package com.meifute.nm.auth.service;

import com.meifute.nm.auth.entity.MallRolePermission;
import com.baomidou.mybatisplus.extension.service.IService;
import com.meifute.nm.auth.entity.dto.MallPermissionDTO;

import java.util.List;

/**
 * <p>
 * 角色权限表 服务类
 * </p>
 *
 * @author chen
 * @since 2020-08-17
 */
public interface IMallRolePermisssionService extends IService<MallRolePermission> {

    Boolean removeRolePermission(Long roleId, Long permissionId);

    List<MallPermissionDTO> listRolePermission(Long roleId);
}
