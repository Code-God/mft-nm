package com.meifute.nm.othersserver.domain.vo.moments;

import com.meifute.nm.othersserver.domain.param.BaseParam;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


/**
 * @Classname QueryMomentsSourceApp
 * @Description
 * @Date 2020-08-13 11:24
 * @Created by MR. Xb.Wu
 */
@Data
public class QueryAppMomentsSource extends BaseParam {

    @ApiModelProperty("文案内容")
    private String content;

    @ApiModelProperty("分类id")
    private String classifyId;
}
