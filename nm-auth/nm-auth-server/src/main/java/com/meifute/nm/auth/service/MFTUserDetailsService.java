package com.meifute.nm.auth.service;

import com.alibaba.fastjson.JSON;
import com.meifute.nm.auth.entity.MallUser;
import com.meifute.nm.auth.entity.dto.MallUserDTO;
import com.meifute.nm.auth.mapper.MallRolePermissionMapper;
import com.meifute.nm.auth.mapper.MallUserMapper;
import com.meifute.nm.auth.mapper.MallUserRoleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Service
public class MFTUserDetailsService implements UserDetailsService {

    @Autowired
    private MallUserMapper userMapper;

    @Autowired
    private MallUserRoleMapper mallUserRoleMapper;

    @Autowired
    private MallRolePermissionMapper mallRolePermissionMapper;

    //加载user的信息
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        MallUser mallUser = userMapper.getUserByUsername(username);
        if (mallUser == null) return null;
        List<String> roleIdList = mallUserRoleMapper.getRoleByUserId(mallUser.getId());
        List<String> permissionIdList = mallRolePermissionMapper.getPermissionByUserId(roleIdList);
        String[] permissionArray = new String[permissionIdList.size()];
        permissionIdList.toArray(permissionArray);
        MallUserDTO mallUserDTO = MallUserDTO.builder().id(mallUser.getId()).phone(mallUser.getPhone()).username(mallUser.getUsername()).roleIdList(roleIdList).permissionIdList(permissionIdList).build();
        UserDetails userDetails = User.withUsername(JSON.toJSONString(mallUserDTO)).password(mallUser.getPassword()).authorities(permissionArray).build();
        return userDetails;
    }

}