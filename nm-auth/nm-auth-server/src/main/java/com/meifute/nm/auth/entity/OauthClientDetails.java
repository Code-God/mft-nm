package com.meifute.nm.auth.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.Version;
import java.time.LocalDateTime;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;

/**
 * <p>
 * 接入客户端信息
 * </p>
 *
 * @author chen
 * @since 2020-08-17
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("oauth_client_details")
@ApiModel(value="OauthClientDetails对象", description="接入客户端信息")
public class OauthClientDetails implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "客户端标识。")
    private String clientId;

    @ApiModelProperty(value = "接入资源列表。")
    private String resourceIds;

    @ApiModelProperty(value = "客户端密钥。")
    private String clientSecret;

    private String scope;

    private String authorizedGrantTypes;

    private String webServerRedirectUri;

    private String authorities;

    private Integer accessTokenValidity;

    private Integer refreshTokenValidity;

    private String additionalInformation;

    private LocalDateTime createTime;

    private Integer archived;

    private Integer trusted;

    private String autoapprove;


}
