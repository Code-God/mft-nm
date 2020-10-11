package com.meifute.nm.auth.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.meifute.nm.auth.base.BaseResponse;
import com.meifute.nm.auth.entity.MallUser;
import com.baomidou.mybatisplus.extension.service.IService;
import com.meifute.nm.auth.entity.dto.MallUserDTO;
import com.meifute.nm.auth.entity.vo.MallUserChangePasswordVO;
import com.meifute.nm.auth.entity.vo.MallUserQueryVO;
import com.meifute.nm.auth.entity.vo.UserAddVO;
import com.meifute.nm.auth.entity.vo.UserUpdateVO;

import java.util.List;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author chen
 * @since 2020-08-17
 */
public interface IMallUserService extends IService<MallUser> {

    Boolean addUser(UserAddVO userAddVO);

    Boolean updateUser(UserUpdateVO userUpdateVO);

    Boolean removeUser(Long id);

    BaseResponse changePassword(MallUserChangePasswordVO userChangePasswordVO);

    MallUserDTO changePassword();

    Page<MallUserDTO> queryByParam(MallUserQueryVO mallUserQueryVO);

    MallUserDTO userDetail(String id);
}
