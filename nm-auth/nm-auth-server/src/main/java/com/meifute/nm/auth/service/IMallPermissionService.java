package com.meifute.nm.auth.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.meifute.nm.auth.entity.MallPermission;
import com.baomidou.mybatisplus.extension.service.IService;
import com.meifute.nm.auth.entity.dto.MallPermissionDTO;
import com.meifute.nm.auth.entity.vo.PermissionAddVO;
import com.meifute.nm.auth.entity.vo.PermissionQueryVO;
import com.meifute.nm.auth.entity.vo.PermissionUpdateVO;

/**
 * <p>
 * 权限表 服务类
 * </p>
 *
 * @author chen
 * @since 2020-08-17
 */
public interface IMallPermissionService extends IService<MallPermission> {

    Boolean addPermission(PermissionAddVO permissionAddVO);

    Boolean updatePermission(PermissionUpdateVO permissionUpdateVO);

    Boolean removePermission(Long id);

    Page<MallPermissionDTO> pagePermission(PermissionQueryVO permissionQueryVO);

    MallPermissionDTO detailPermission(Long id);
}
