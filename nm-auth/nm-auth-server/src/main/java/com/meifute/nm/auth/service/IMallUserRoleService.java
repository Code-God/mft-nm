package com.meifute.nm.auth.service;

import com.meifute.nm.auth.entity.MallUserRole;
import com.baomidou.mybatisplus.extension.service.IService;
import com.meifute.nm.auth.entity.dto.MallRoleDTO;

import java.util.List;

/**
 * <p>
 * 用户角色表 服务类
 * </p>
 *
 * @author chen
 * @since 2020-08-17
 */
public interface IMallUserRoleService extends IService<MallUserRole> {

    Boolean deleteUserRole(MallUserRole mallUserRole);

    Boolean removeUserRole(Long userId, Long roleId);

    List<MallRoleDTO> listUserRole(Long userId);
}
