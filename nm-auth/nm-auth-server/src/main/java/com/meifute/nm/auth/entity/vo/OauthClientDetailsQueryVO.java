package com.meifute.nm.auth.entity.vo;

import com.meifute.nm.auth.base.BaseParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

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
@ApiModel(value="OauthClientDetails对象查询VO", description="OauthClientDetails对象查询VO")
public class OauthClientDetailsQueryVO extends BaseParam implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "客户端标识。")
    private String clientId;

    @ApiModelProperty(value = "重定向地址")
    private String webServerRedirectUri;

    @ApiModelProperty(value = "额外信息。")
    private String additionalInformation;

}
