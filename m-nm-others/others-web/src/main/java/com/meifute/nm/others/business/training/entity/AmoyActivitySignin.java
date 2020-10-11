package com.meifute.nm.others.business.training.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @Classname AmoyActivitySignin
 * @Description
 * @Date 2020-08-19 17:36
 * @Created by MR. Xb.Wu
 */
@Data
@TableName("amoy_activity_signin")
public class AmoyActivitySignin implements Serializable {

    @TableId(type = IdType.ASSIGN_ID)
    private String id;
    @ApiModelProperty("报名id")
    private String enrollId;
    @ApiModelProperty("用户id")
    private String userId;
    @ApiModelProperty("签到场所，1酒店，2旅游车，3会议大厅，4晚宴")
    private Integer signInPlace;
    @ApiModelProperty("签到时的经纬度")
    private String longitudeAndLatitude;
    @ApiModelProperty("创建时间")
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private LocalDateTime createTime;
    @ApiModelProperty("更新时间")
    @TableField(value = "update_time", fill = FieldFill.UPDATE)
    private LocalDateTime updateTime;
    @Version
    private Long version;
    @ApiModelProperty("逻辑删除")
    private Integer deleted;

}
