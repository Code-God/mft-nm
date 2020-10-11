package com.meifute.nm.auth.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.meifute.nm.auth.entity.MallRole;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.meifute.nm.auth.entity.dto.MallRoleDTO;
import com.meifute.nm.auth.entity.vo.MallRoleQueryVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 角色表 Mapper 接口
 * </p>
 *
 * @author chen
 * @since 2020-08-17
 */
public interface MallRoleMapper extends BaseMapper<MallRole> {

    List<MallRoleDTO> pageRole(@Param("mallRoleQueryVO") MallRoleQueryVO mallRoleQueryVO,Page<MallRoleDTO> page);
}
