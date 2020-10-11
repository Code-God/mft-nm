package com.meifute.nm.auth.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.meifute.nm.auth.entity.MallPermission;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.meifute.nm.auth.entity.dto.MallPermissionDTO;
import com.meifute.nm.auth.entity.vo.PermissionQueryVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 权限表 Mapper 接口
 * </p>
 *
 * @author chen
 * @since 2020-08-17
 */
public interface MallPermissionMapper extends BaseMapper<MallPermission> {

    List<MallPermissionDTO> pagePermission(@Param("permissionQueryVO") PermissionQueryVO permissionQueryVO, Page<MallPermissionDTO> page);
}
