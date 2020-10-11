package com.meifute.nm.auth.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.meifute.nm.auth.entity.MallUser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.meifute.nm.auth.entity.dto.MallUserDTO;
import com.meifute.nm.auth.entity.vo.MallUserQueryVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 用户表 Mapper 接口
 * </p>
 *
 * @author chen
 * @since 2020-08-17
 */
public interface MallUserMapper extends BaseMapper<MallUser> {

    MallUser getUserByUsername(@Param("username") String username);

    MallUser getUserByPhone(@Param("phone") String phone);

    Integer updatePasswordByPhone(@Param("phone") String phone, @Param("encodePassword") String encodePassword);

    List<MallUserDTO> queryByParam(@Param("mallUserQueryVO") MallUserQueryVO mallUserQueryVO, Page<MallUserDTO> page);
}
