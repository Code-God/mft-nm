package com.meifute.nm.others.business.overseasstatistics.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @Classname ActivitiesQuota
 * @Description
 * @Date 2020-08-06 11:18
 * @Created by MR. Xb.Wu
 */
@Data
@TableName("activities_quota")
public class ActivitiesQuota implements Serializable {

    @ApiModelProperty("id")
    @TableId
    private Long id;

    @ApiModelProperty("活动主表id")
    private Long statisticsId;

    @ApiModelProperty("类型 0参会人，1达标人，2达标退代，3其他活动抵扣名额")
    private Integer type;

    @ApiModelProperty("代理姓名")
    private String agentName;

    @ApiModelProperty("代理手机号")
    private String agentPhone;

    @ApiModelProperty("关联时间，type=2为退代时间，type=3为升级总代时间")
    private LocalDateTime associationTime;

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
