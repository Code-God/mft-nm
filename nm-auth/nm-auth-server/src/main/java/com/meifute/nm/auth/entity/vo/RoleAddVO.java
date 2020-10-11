package com.meifute.nm.auth.entity.vo;

import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.Version;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

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
@ApiModel(value="MallRole对象新增VO", description="MallRole对象新增VO")
public class RoleAddVO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "角色名称",required = true)
    private String name;

    @ApiModelProperty(value = "角色描述",required = true)
    private String description;

    @ApiModelProperty(value = "状态：0=失效，1=生效",required = true)
    private Integer status;

    @ApiModelProperty(value = "归属：0=美浮特，1=学习平台,2医院",required = true)
    private Integer belong;

}
