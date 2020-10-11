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
 * 角色表
 * </p>
 *
 * @author chen
 * @since 2020-08-17
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value="MallRole对象分页查询参数", description="MallRole对象分页查询参数")
public class MallRoleQueryVO extends BaseParam implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键id")
    private Long id;

    @ApiModelProperty(value = "角色名称")
    private String name;

    @ApiModelProperty(value = "角色描述")
    private String description;

    @ApiModelProperty(value = "状态：0=失效，1=生效")
    private Integer status;

    @ApiModelProperty(value = "归属：0=美浮特，1=学习平台,2医院")
    private Integer belong;

}
