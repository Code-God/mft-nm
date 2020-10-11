package com.meifute.nm.othersserver.domain.vo.moments;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.meifute.nm.othersserver.domain.param.BaseParam;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @Classname QueryMomentsSource
 * @Description TODO
 * @Date 2020-06-18 18:20
 * @Created by MR. Xb.Wu
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QueryAdminMomentsSource extends BaseParam {

    @ApiModelProperty("发布时间-起始")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime releaseStartTime;

    @ApiModelProperty("发布时间-截止")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime releaseEndTime;

    @ApiModelProperty("创建时间-起始")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createStartTime;

    @ApiModelProperty("创建时间-截止")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createEndTime;

    @ApiModelProperty("文案内容")
    private String content;

    @ApiModelProperty("状态，0待发布，1已发布")
    private String status;

    @ApiModelProperty("分类id")
    private String classifyId;
}
