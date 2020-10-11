package com.meifute.nm.others.business.training.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @Classname AmoyActivitySomeone
 * @Description
 * @Date 2020-08-19 17:33
 * @Created by MR. Xb.Wu
 */
@Data
@TableName("amoy_activity_someone")
public class AmoyActivitySomeone implements Serializable {

    @TableId(type = IdType.ASSIGN_ID)
    private String id;
    @ApiModelProperty("报名id")
    private String enrollId;
    @ApiModelProperty("用户id")
    private String userId;
    @ApiModelProperty("同行人姓名")
    private String name;
    @ApiModelProperty("同行人手机号")
    private String phone;
    @ApiModelProperty("同行人身份证号")
    private String idCard;
    @ApiModelProperty("状态，0正常，1已取消")
    private Integer status;
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
