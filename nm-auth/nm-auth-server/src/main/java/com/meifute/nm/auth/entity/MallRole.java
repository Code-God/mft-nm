package com.meifute.nm.auth.entity;

import com.baomidou.mybatisplus.annotation.Version;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableLogic;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;

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
@ApiModel(value="MallRole对象", description="角色表")
public class MallRole implements Serializable {

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

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "更新时间")
    private LocalDateTime updateTime;

    @ApiModelProperty(value = "乐观锁")
    @Version
    private Long version;

    @ApiModelProperty(value = "逻辑删除")
    @TableLogic
    private Boolean deleted;


}
