package com.meifute.nm.auth.service.impl;

import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.meifute.nm.auth.entity.MallPermission;
import com.meifute.nm.auth.entity.dto.MallPermissionDTO;
import com.meifute.nm.auth.entity.dto.MallRoleDTO;
import com.meifute.nm.auth.entity.vo.PermissionAddVO;
import com.meifute.nm.auth.entity.vo.PermissionQueryVO;
import com.meifute.nm.auth.entity.vo.PermissionUpdateVO;
import com.meifute.nm.auth.mapper.MallPermissionMapper;
import com.meifute.nm.auth.service.IMallPermissionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;

/**
 * <p>
 * 权限表 服务实现类
 * </p>
 *
 * @author chen
 * @since 2020-08-17
 */
@Service
public class MallPermissionServiceImpl extends ServiceImpl<MallPermissionMapper, MallPermission> implements IMallPermissionService {

    @Autowired
    MallPermissionMapper permissionMapper;

    @Override
    public Boolean addPermission(PermissionAddVO permissionAddVO) {
        return save(MallPermission.builder()
                .id(IdWorker.getId())
                .name(permissionAddVO.getName())
                .description(permissionAddVO.getDescription())
                .belong(permissionAddVO.getBelong())
                .status(permissionAddVO.getBelong())
                .build());
    }

    @Override
    public Boolean updatePermission(PermissionUpdateVO permissionUpdateVO) {
        return updateById(MallPermission.builder()
                .id(permissionUpdateVO.getId())
                .name(permissionUpdateVO.getName())
                .description(permissionUpdateVO.getDescription())
                .status(permissionUpdateVO.getStatus())
                .belong(permissionUpdateVO.getBelong())
                .build());
    }

    @Override
    public Boolean removePermission(Long id) {
        return removeById(MallPermission.builder().id(id).build());
    }

    @Override
    public Page<MallPermissionDTO> pagePermission(PermissionQueryVO permissionQueryVO) {
        Page<MallPermissionDTO> page = new Page<>(permissionQueryVO.getPageCurrent(), permissionQueryVO.getPageSize());
        List<MallPermissionDTO> roleDTOList = permissionMapper.pagePermission(permissionQueryVO,page);
        page.setRecords(roleDTOList);
        return page;
    }

    @Override
    public MallPermissionDTO detailPermission(Long id) {
        MallPermission mallPermission = getById(id);
        if (ObjectUtils.isEmpty(mallPermission)) return null;

        return MallPermissionDTO.builder()
                .id(mallPermission.getId())
                .name(mallPermission.getName())
                .description(mallPermission.getDescription())
                .status(mallPermission.getStatus())
                .belong(mallPermission.getBelong())
                .build();
    }


}
