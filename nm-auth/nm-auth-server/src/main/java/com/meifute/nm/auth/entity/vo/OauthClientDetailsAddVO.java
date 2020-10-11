package com.meifute.nm.auth.entity.vo;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

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
@ApiModel(value="OauthClientDetails对象新增VO", description="OauthClientDetails对象新增VO")
public class OauthClientDetailsAddVO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "客户端标识。",required = true)
    private String clientId;

    @ApiModelProperty(value = "接入资源列表。",required = true)
    private String resourceIds;

    @ApiModelProperty(value = "客户端密钥。",required = true)
    private String clientSecret;

    @ApiModelProperty(value = "范围",required = true)
    private String scope;

    @ApiModelProperty(value = "授权类型",required = true)
    private String authorizedGrantTypes;

    @ApiModelProperty(value = "重定向地址")
    private String webServerRedirectUri;

    @ApiModelProperty(value = "权限")
    private String authorities;

    @ApiModelProperty(value = "token有效期",required = true)
    private Integer accessTokenValidity;

    @ApiModelProperty(value = "更新token有效期",required = true)
    private Integer refreshTokenValidity;

    @ApiModelProperty(value = "额外信息。")
    private String additionalInformation;

}
