package com.meifute.nm.auth.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import com.meifute.nm.auth.entity.OauthClientDetails;
import com.meifute.nm.auth.entity.dto.OauthClientDetailsDTO;
import com.meifute.nm.auth.entity.vo.OauthClientDetailsAddVO;
import com.meifute.nm.auth.entity.vo.OauthClientDetailsQueryVO;
import com.meifute.nm.auth.entity.vo.OauthClientDetailsUpdateVO;
import com.meifute.nm.auth.mapper.OauthClientDetailsMapper;
import com.meifute.nm.auth.service.IOauthClientDetailsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 接入客户端信息 服务实现类
 * </p>
 *
 * @author chen
 * @since 2020-08-17
 */
@Service
public class OauthClientDetailsServiceImpl extends ServiceImpl<OauthClientDetailsMapper, OauthClientDetails> implements IOauthClientDetailsService {

    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    OauthClientDetailsMapper oauthClientDetailsMapper;

    @Override
    public Boolean addClient(OauthClientDetailsAddVO oauthClientDetailsAddVO) {
        return save(
                OauthClientDetails.builder()
                .clientId(oauthClientDetailsAddVO.getClientId())
                .clientSecret(oauthClientDetailsAddVO.getClientSecret())
                .resourceIds(oauthClientDetailsAddVO.getResourceIds())
                .scope(oauthClientDetailsAddVO.getScope())
                .authorizedGrantTypes(oauthClientDetailsAddVO.getAuthorizedGrantTypes())
                .webServerRedirectUri(oauthClientDetailsAddVO.getWebServerRedirectUri())
                .authorities(oauthClientDetailsAddVO.getAuthorities())
                .accessTokenValidity(oauthClientDetailsAddVO.getAccessTokenValidity())
                .refreshTokenValidity(oauthClientDetailsAddVO.getRefreshTokenValidity())
                .additionalInformation(oauthClientDetailsAddVO.getAdditionalInformation())
                .archived(0)
                .trusted(0)
                .autoapprove("false")
                .build()
        );
    }

    @Override
    public Boolean updateClient(OauthClientDetailsUpdateVO oauthClientDetailsUpdateVO) {
        String encodeClientSecret = passwordEncoder.encode(oauthClientDetailsUpdateVO.getClientSecret());
        oauthClientDetailsUpdateVO.setClientSecret(encodeClientSecret);
        return SqlHelper.retBool(oauthClientDetailsMapper.updateClient(oauthClientDetailsUpdateVO));
    }

    @Override
    public Boolean removeClient(String clientId) {
        return oauthClientDetailsMapper.removeClient(clientId);
    }

    @Override
    public Page<OauthClientDetailsDTO> pageClient(OauthClientDetailsQueryVO oauthClientDetailsQueryVO) {
        Page<OauthClientDetailsDTO> page = new Page<>(oauthClientDetailsQueryVO.getPageCurrent(), oauthClientDetailsQueryVO.getPageSize());
        List<OauthClientDetailsDTO> listClient = oauthClientDetailsMapper.pageClient(oauthClientDetailsQueryVO,page);
        page.setRecords(listClient);
        return page;
    }

    @Override
    public OauthClientDetailsDTO detailClient(String clientId) {
        return oauthClientDetailsMapper.detailClient(clientId);
    }


}
