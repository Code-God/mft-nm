package com.meifute.nm.auth.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.meifute.nm.auth.base.BaseResponse;
import com.meifute.nm.auth.entity.MallUser;
import com.meifute.nm.auth.entity.dto.MallUserDTO;
import com.meifute.nm.auth.entity.vo.MallUserChangePasswordVO;
import com.meifute.nm.auth.entity.vo.MallUserQueryVO;
import com.meifute.nm.auth.entity.vo.UserAddVO;
import com.meifute.nm.auth.entity.vo.UserUpdateVO;
import com.meifute.nm.auth.enums.ResultEnum;
import com.meifute.nm.auth.mapper.MallUserMapper;
import com.meifute.nm.auth.service.IMallUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author chen
 * @since 2020-08-17
 */
@Service
public class MallUserServiceImpl extends ServiceImpl<MallUserMapper, MallUser> implements IMallUserService {

    @Autowired
    MallUserMapper userMapper;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public Boolean addUser(UserAddVO userAddVO) {
        return save(
                MallUser.builder()
                .id(IdWorker.getId())
                .phone(userAddVO.getPhone())
                .username(userAddVO.getUsername())
                .password(passwordEncoder.encode(userAddVO.getPassword()))
                .build()
        );
    }

    @Override
    public Boolean updateUser(UserUpdateVO userUpdateVO) {
        MallUser user = new MallUser();
        user.setId(userUpdateVO.getId());
        if (!ObjectUtils.isEmpty(userUpdateVO.getPhone())) user.setPhone(userUpdateVO.getPhone());
        if (!ObjectUtils.isEmpty(userUpdateVO.getUsername())) user.setUsername(userUpdateVO.getUsername());
        return updateById(user);
    }

    @Override
    public Boolean removeUser(Long id) {
        return removeById(id);
    }

    @Override
    public BaseResponse changePassword(MallUserChangePasswordVO userChangePasswordVO) {
        String phone = userChangePasswordVO.getPhone();
        String code = userChangePasswordVO.getCode();
        String newPassword = userChangePasswordVO.getNewPassword();
        String newPasswordConfirm = userChangePasswordVO.getNewPasswordConfirm();
        if (!newPassword.equals(newPasswordConfirm)){
            return BaseResponse.builder().code("fail").msg("密码不一致。").build();
        }
        // todo 极光校验验证码
//        JpushUtil
        String encodePassword = passwordEncoder.encode(newPassword);
        Integer affectRows = userMapper.updatePasswordByPhone(phone,encodePassword);
        if (affectRows > 0){
            return BaseResponse.builder().code(ResultEnum.SUCCESS.getCode()).msg("密码更新成功。").build();
        }else{
            return BaseResponse.builder().code(ResultEnum.FAIL.getCode()).msg("密码更新失败。").build();
        }
    }

    @Override
    public MallUserDTO changePassword() {
        MallUserDTO mallUserDTO = null;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(!(authentication instanceof OAuth2Authentication)) return null;
        OAuth2Authentication oAuth2Authentication = (OAuth2Authentication) authentication;
        Authentication userAuthentication = oAuth2Authentication.getUserAuthentication();
        String  principal = userAuthentication.getName();
        if (!ObjectUtils.isEmpty(principal))  mallUserDTO = JSON.parseObject(principal,MallUserDTO.class);
        return mallUserDTO;
    }

    @Override
    public Page<MallUserDTO> queryByParam(MallUserQueryVO mallUserQueryVO) {
        Page<MallUserDTO> page = new Page<>(mallUserQueryVO.getPageCurrent(), mallUserQueryVO.getPageSize());
        List<MallUserDTO> userDTOPage = userMapper.queryByParam(mallUserQueryVO,page);
        page.setRecords(userDTOPage);
        return page;
    }

    @Override
    public MallUserDTO userDetail(String id) {
        MallUser mallUser = getById(id);
        return MallUserDTO.builder()
                .id(mallUser.getId())
                .phone(mallUser.getPhone())
                .username(mallUser.getUsername())
                .build();
    }

}
