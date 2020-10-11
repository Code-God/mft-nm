package com.meifute.nm.auth.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.meifute.nm.auth.entity.OauthClientDetails;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.meifute.nm.auth.entity.dto.OauthClientDetailsDTO;
import com.meifute.nm.auth.entity.vo.OauthClientDetailsQueryVO;
import com.meifute.nm.auth.entity.vo.OauthClientDetailsUpdateVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 接入客户端信息 Mapper 接口
 * </p>
 *
 * @author chen
 * @since 2020-08-17
 */
public interface OauthClientDetailsMapper extends BaseMapper<OauthClientDetails> {

    Integer updateClient(@Param("oauthClientDetailsUpdateVO") OauthClientDetailsUpdateVO oauthClientDetailsUpdateVO);

    Boolean removeClient(@Param("clientId") String clientId);

    List<OauthClientDetailsDTO> pageClient(@Param("oauthClientDetailsQueryVO") OauthClientDetailsQueryVO oauthClientDetailsQueryVO, Page<OauthClientDetailsDTO> page);

    OauthClientDetailsDTO detailClient(@Param("clientId") String clientId);
}
