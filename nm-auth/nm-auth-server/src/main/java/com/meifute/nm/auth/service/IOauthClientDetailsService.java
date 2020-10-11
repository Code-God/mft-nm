package com.meifute.nm.auth.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.meifute.nm.auth.entity.OauthClientDetails;
import com.baomidou.mybatisplus.extension.service.IService;
import com.meifute.nm.auth.entity.dto.OauthClientDetailsDTO;
import com.meifute.nm.auth.entity.vo.OauthClientDetailsAddVO;
import com.meifute.nm.auth.entity.vo.OauthClientDetailsQueryVO;
import com.meifute.nm.auth.entity.vo.OauthClientDetailsUpdateVO;

/**
 * <p>
 * 接入客户端信息 服务类
 * </p>
 *
 * @author chen
 * @since 2020-08-17
 */
public interface IOauthClientDetailsService extends IService<OauthClientDetails> {

    Boolean addClient(OauthClientDetailsAddVO oauthClientDetailsAddVO);

    Boolean updateClient(OauthClientDetailsUpdateVO oauthClientDetailsUpdateVO);

    Boolean removeClient(String clientId);

    Page<OauthClientDetailsDTO> pageClient(OauthClientDetailsQueryVO oauthClientDetailsQueryVO);

    OauthClientDetailsDTO detailClient(String clientId);
}
