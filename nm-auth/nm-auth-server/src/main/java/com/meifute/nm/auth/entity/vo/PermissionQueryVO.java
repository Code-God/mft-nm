package com.meifute.nm.auth.entity.vo;

import com.meifute.nm.auth.base.BaseParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 权限表
 * </p>
 *
 * @author chen
 * @since 2020-08-17
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value="MallPermission查询VO", description="MallPermission查询VO")
public class PermissionQueryVO extends BaseParam implements Serializable  {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键id",required = true)
    private Long id;

    @ApiModelProperty(value = "权限名称")
    private String name;

    @ApiModelProperty(value = "状态：0=失效，1=生效")
    private String status;

    @ApiModelProperty(value = "归属：0=美浮特，1=学习平台")
    private String belong;

}
