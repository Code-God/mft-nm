package com.meifute.nm.auth.service.impl;

import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import com.meifute.nm.auth.entity.MallUserRole;
import com.meifute.nm.auth.entity.dto.MallRoleDTO;
import com.meifute.nm.auth.mapper.MallUserRoleMapper;
import com.meifute.nm.auth.service.IMallUserRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 用户角色表 服务实现类
 * </p>
 *
 * @author chen
 * @since 2020-08-17
 */
@Service
public class MallUserRoleServiceImpl extends ServiceImpl<MallUserRoleMapper, MallUserRole> implements IMallUserRoleService {

    @Autowired
    MallUserRoleMapper mallUserRoleMapper;

    @Override
    public Boolean deleteUserRole(MallUserRole mallUserRole) {
        Integer ret = mallUserRoleMapper.deleteUserRole(mallUserRole);
        return SqlHelper.retBool(ret);
    }

    @Override
    public Boolean removeUserRole(Long userId, Long roleId) {
        return SqlHelper.retBool(mallUserRoleMapper.removeUserRole(userId,roleId));
    }

    @Override
    public List<MallRoleDTO> listUserRole(Long userId) {
        List<MallRoleDTO> listUserRole = mallUserRoleMapper.listUserRole(userId);
        return listUserRole;
    }
}
