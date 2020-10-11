package com.meifute.nm.othersserver.domain.vo.moments;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * @Classname QueryMomentsSourceDto
 * @Description TODO
 * @Date 2020-06-18 18:23
 * @Created by MR. Xb.Wu
 */
@Data
public class QueryMomentsSourceDto implements Serializable {

    @ApiModelProperty("素材id")
    private String id;

    @ApiModelProperty("发布时间")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime releaseTime;

    @ApiModelProperty("文案内容")
    private String content;

    @ApiModelProperty("素材图片")
    private String contentImg;

    @ApiModelProperty("点赞数")
    private Integer giveUpNum;

    @ApiModelProperty("使用次数")
    private Integer usedNum;

    @ApiModelProperty("0待发布，1已发布")
    private String status;

    @ApiModelProperty("是否点赞")
    private Boolean isGiveUp;

    @ApiModelProperty("资源类型。0图片，1视频")
    private String sourceType;

    @ApiModelProperty("分类名称")
    private String classifyName;

    @ApiModelProperty("分类id")
    private String classifyId;

    @ApiModelProperty("创建时间")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

}
