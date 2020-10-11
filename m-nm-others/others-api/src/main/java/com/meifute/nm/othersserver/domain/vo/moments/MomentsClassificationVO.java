package com.meifute.nm.othersserver.domain.vo.moments;


import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Classname mallMomentsClassification
 * @Description
 * @Date 2020-08-04 11:07
 * @Created by MR. Xb.Wu
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MomentsClassificationVO implements Serializable {

    private String id;

    @ApiModelProperty("排序")
    private Integer sort;

    @ApiModelProperty("分类名称")
    private String classifyName;

    @ApiModelProperty("分类描述")
    private String classifyDesc;

}
