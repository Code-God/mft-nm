package com.meifute.nm.othersserver.domain.vo.moments;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Classname MomentOperationVO
 * @Description
 * @Date 2020-08-13 12:04
 * @Created by MR. Xb.Wu
 */
@Data
public class MomentOperationVO {

    private String id;

    @ApiModelProperty("素材id")
    private String momentId;

    @ApiModelProperty("0点赞，1发圈")
    private String type;

}
