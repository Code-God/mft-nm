package com.meifute.nm.others.business.training.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @Classname TrainCostBack
 * @Description
 * @Date 2020-07-08 18:04
 * @Created by MR. Xb.Wu
 */
@Data
@TableName("train_cost_back")
public class TrainCostBack implements Serializable {

    @ApiModelProperty("id")
    @TableId(type = IdType.ASSIGN_ID)
    private String id;

    @ApiModelProperty("报名id")
    private String enrollId;

    @ApiModelProperty("退费金额")
    private BigDecimal cost;

    @ApiModelProperty("退款时间")
    private LocalDateTime backTime;

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
