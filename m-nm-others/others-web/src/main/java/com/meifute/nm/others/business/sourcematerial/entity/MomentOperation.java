package com.meifute.nm.others.business.sourcematerial.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @Classname MallMomentOperation
 * @Description TODO
 * @Date 2020-06-18 17:53
 * @Created by MR. Xb.Wu
 */
@Data
@TableName("moments_operation")
public class MomentOperation {

    @TableId
    private Long id;

    @TableField("moment_id")
    @ApiModelProperty("素材id")
    private String momentId;

    @TableField("type")
    @ApiModelProperty("0点赞，1发圈")
    private String type;

    @TableField("user_id")
    @ApiModelProperty("用户id")
    private Long userId;

    @ApiModelProperty("创建时间")
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @ApiModelProperty("更新时间")
    @TableField(value = "update_time", fill = FieldFill.UPDATE)
    private LocalDateTime updateTime;

    @ApiModelProperty("版本号")
    @Version
    private Long version;

    @ApiModelProperty("逻辑删除")
    private Integer deleted;
}
