package com.meifute.nm.auth.service.impl;

import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.meifute.nm.auth.entity.MallRole;
import com.meifute.nm.auth.entity.dto.MallRoleDTO;
import com.meifute.nm.auth.entity.dto.MallUserDTO;
import com.meifute.nm.auth.entity.vo.MallRoleQueryVO;
import com.meifute.nm.auth.entity.vo.RoleAddVO;
import com.meifute.nm.auth.entity.vo.RoleUpdateVO;
import com.meifute.nm.auth.mapper.MallRoleMapper;
import com.meifute.nm.auth.service.IMallRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;

/**
 * <p>
 * 角色表 服务实现类
 * </p>
 *
 * @author chen
 * @since 2020-08-17
 */
@Service
public class MallRoleServiceImpl extends ServiceImpl<MallRoleMapper, MallRole> implements IMallRoleService {

    @Autowired
    MallRoleMapper roleMapper;

    @Override
    public Boolean addRole(RoleAddVO roleAddVO) {
        return save(MallRole.builder()
                .id(IdWorker.getId())
                .name(roleAddVO.getName())
                .description(roleAddVO.getDescription())
                .status(roleAddVO.getStatus())
                .belong(roleAddVO.getBelong())
                .build()
        );
    }

    @Override
    public Boolean updateRole(RoleUpdateVO roleUpdateVO) {
        return updateById(MallRole.builder()
                .id(roleUpdateVO.getId())
                .name(roleUpdateVO.getName())
                .description(roleUpdateVO.getDescription())
                .status(roleUpdateVO.getStatus())
                .belong(roleUpdateVO.getBelong())
                .build()
        );
    }

    @Override
    public Boolean removeRole(Long id) {
        return removeById(MallRole.builder().id(id).build());
    }

    @Override
    public Page<MallRoleDTO> pageRole(MallRoleQueryVO mallRoleQueryVO) {
        Page<MallRoleDTO> page = new Page<>(mallRoleQueryVO.getPageCurrent(), mallRoleQueryVO.getPageSize());
        List<MallRoleDTO> roleDTOList = roleMapper.pageRole(mallRoleQueryVO,page);
        page.setRecords(roleDTOList);
        return page;
    }

    @Override
    public MallRoleDTO detailRole(Long id) {
        MallRole mallRole = getById(id);
        if (ObjectUtils.isEmpty(mallRole)) return null;
        return MallRoleDTO.builder()
                .id(mallRole.getId())
                .name(mallRole.getName())
                .description(mallRole.getDescription())
                .status(mallRole.getStatus())
                .belong(mallRole.getBelong())
                .build();
    }


}
