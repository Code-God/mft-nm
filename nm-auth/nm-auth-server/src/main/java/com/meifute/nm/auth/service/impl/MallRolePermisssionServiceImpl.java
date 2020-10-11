package com.meifute.nm.auth.service.impl;

import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import com.meifute.nm.auth.entity.MallRolePermission;
import com.meifute.nm.auth.entity.dto.MallPermissionDTO;
import com.meifute.nm.auth.mapper.MallRolePermissionMapper;
import com.meifute.nm.auth.service.IMallRolePermisssionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 角色权限表 服务实现类
 * </p>
 *
 * @author chen
 * @since 2020-08-17
 */
@Service
public class MallRolePermisssionServiceImpl extends ServiceImpl<MallRolePermissionMapper, MallRolePermission> implements IMallRolePermisssionService {

    @Autowired
    MallRolePermissionMapper rolePermissionMapper;

    @Override
    public Boolean removeRolePermission(Long roleId, Long permissionId) {
        return SqlHelper.retBool(rolePermissionMapper.removeRolePermission(roleId,permissionId));
    }

    @Override
    public List<MallPermissionDTO> listRolePermission(Long roleId) {
        return rolePermissionMapper.listRolePermission(roleId);
    }
}
