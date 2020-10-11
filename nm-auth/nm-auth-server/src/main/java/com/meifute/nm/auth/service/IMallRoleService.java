package com.meifute.nm.auth.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.meifute.nm.auth.entity.MallRole;
import com.baomidou.mybatisplus.extension.service.IService;
import com.meifute.nm.auth.entity.dto.MallRoleDTO;
import com.meifute.nm.auth.entity.vo.MallRoleQueryVO;
import com.meifute.nm.auth.entity.vo.RoleAddVO;
import com.meifute.nm.auth.entity.vo.RoleUpdateVO;

/**
 * <p>
 * 角色表 服务类
 * </p>
 *
 * @author chen
 * @since 2020-08-17
 */
public interface IMallRoleService extends IService<MallRole> {

    Boolean addRole(RoleAddVO roleAddVO);

    Boolean updateRole(RoleUpdateVO roleUpdateVO);

    Boolean removeRole(Long id);

    Page<MallRoleDTO> pageRole(MallRoleQueryVO mallRoleQueryVO);

    MallRoleDTO detailRole(Long id);
}
