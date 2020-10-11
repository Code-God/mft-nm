package com.meifute.nm.othersserver.domain.vo.moments;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Classname SortClassification
 * @Description
 * @Date 2020-08-04 16:17
 * @Created by MR. Xb.Wu
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SortClassification implements Serializable {

    private String id;

    @ApiModelProperty("0 升，1降")
    private Integer upOrDown;
}
