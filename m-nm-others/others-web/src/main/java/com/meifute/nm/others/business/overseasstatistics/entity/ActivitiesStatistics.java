package com.meifute.nm.others.business.overseasstatistics.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @Classname ActivitiesStatistics
 * @Description
 * @Date 2020-08-06 11:13
 * @Created by MR. Xb.Wu
 */
@Data
@TableName("activities_statistics")
public class ActivitiesStatistics implements Serializable {

    @ApiModelProperty("id")
    @TableId
    private Long id;

    @ApiModelProperty("代理姓名")
    private String agentName;

    @ApiModelProperty("代理手机号")
    private String agentPhone;

    @ApiModelProperty("用户id")
    private String userId;

    @ApiModelProperty("活动名称")
    private String activityName;

    @ApiModelProperty("活动时间")
    private LocalDateTime activityTime;

    @ApiModelProperty("活动时间")
    private LocalDateTime activityEndTime;

    @ApiModelProperty("付费金额")
    private BigDecimal paymentAmount;

    @ApiModelProperty("已扣金额")
    private BigDecimal deductedAmount;

    @ApiModelProperty("备注说明")
    private String remark;

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
