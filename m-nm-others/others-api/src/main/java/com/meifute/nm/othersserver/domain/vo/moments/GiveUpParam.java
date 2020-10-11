package com.meifute.nm.othersserver.domain.vo.moments;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Classname GiveUpParam
 * @Description TODO
 * @Date 2020-06-18 19:07
 * @Created by MR. Xb.Wu
 */
@Data
public class GiveUpParam implements Serializable {

    @ApiModelProperty("素材id")
    private String momentId;

    @ApiModelProperty("0点赞，1取消点赞")
    private Integer flag;
}
