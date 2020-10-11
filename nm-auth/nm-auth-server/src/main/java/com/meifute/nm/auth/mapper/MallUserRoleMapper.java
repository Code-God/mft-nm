package com.meifute.nm.auth.mapper;

import com.meifute.nm.auth.entity.MallUserRole;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.meifute.nm.auth.entity.dto.MallRoleDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 用户角色表 Mapper 接口
 * </p>
 *
 * @author chen
 * @since 2020-08-17
 */
public interface MallUserRoleMapper extends BaseMapper<MallUserRole> {

    Integer deleteUserRole(@Param("mallUserRole") MallUserRole mallUserRole);

    List<String> getRoleByUserId(@Param("id") Long id);

    Integer removeUserRole(@Param("userId") Long userId, @Param("roleId") Long roleId);

    List<MallRoleDTO> listUserRole(@Param("userId") Long userId);
}
