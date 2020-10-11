package com.meifute.nm.auth.entity.vo;

import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.Version;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 权限表
 * </p>
 *
 * @author chen
 * @since 2020-08-17
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="MallPermission对象新增VO", description="MallPermission对象新增VO")
public class PermissionAddVO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "权限名称",required = true)
    private String name;

    @ApiModelProperty(value = "权限描述",required = true)
    private String description;

    @ApiModelProperty(value = "状态：0=失效，1=生效",required = true)
    private String status;

    @ApiModelProperty(value = "归属：0=美浮特，1=学习平台",required = true)
    private String belong;

}
