package com.meifute.nm.othersserver.domain.vo.moments;

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
public class MomentsSourceVO {

    @ApiModelProperty("素材id")
    private String id;

    @ApiModelProperty("发布时间")
    private LocalDateTime releaseTime;

    @ApiModelProperty("文案内容")
    private String content;

    @ApiModelProperty("素材图片")
    private String contentImg;

    @ApiModelProperty("0待发布，1已发布")
    private String status;

    @ApiModelProperty("资源类型。0图片，1视频")
    private String sourceType;

    @ApiModelProperty("分类id")
    private String classifyId;
}
