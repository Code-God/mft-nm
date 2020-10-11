package com.meifute.nm.auth.mapper;

import com.meifute.nm.auth.entity.MallRolePermission;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.meifute.nm.auth.entity.dto.MallPermissionDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 角色权限表 Mapper 接口
 * </p>
 *
 * @author chen
 * @since 2020-08-17
 */
public interface MallRolePermissionMapper extends BaseMapper<MallRolePermission> {

    List<String> getPermissionByUserId(@Param("roleIdList") List<String> roleIdList);

    Integer removeRolePermission(@Param("roleId") Long roleId, @Param("permissionId") Long permissionId);

    List<MallPermissionDTO> listRolePermission(@Param("roleId") Long roleId);

}
