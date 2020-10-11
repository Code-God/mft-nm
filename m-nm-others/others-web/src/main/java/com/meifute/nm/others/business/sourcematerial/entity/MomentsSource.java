package com.meifute.nm.others.business.sourcematerial.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @Classname MallMomentsSource
 * @Description TODO
 * @Date 2020-06-18 17:45
 * @Created by MR. Xb.Wu
 */
@Data
@TableName("moments_source")
public class MomentsSource {

    @TableId
    private Long id;

    @TableField("release_date")
    @ApiModelProperty("发布时间")
    private LocalDateTime releaseTime;

    @TableField("content")
    @ApiModelProperty("文案内容")
    private String content;

    @TableField("content_img")
    @ApiModelProperty("素材图片")
    private String contentImg;

    @TableField("status")
    @ApiModelProperty("0待发布，1已发布")
    private String status;

    @TableField("source_type")
    @ApiModelProperty("资源类型。0图片，1视频")
    private String sourceType;

    @ApiModelProperty("分类id")
    @TableField("classify_id")
    private Long classifyId;

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
