package com.meifute.nm.others.business.youxin.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @ClassName: CloudStockDTO
 * @Description:
 * @Author: Chen
 * @Date: 2020/7/16 16:53
 * @Version: 1.0
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CloudStockDTO extends BaseResponse implements Serializable {

    @ApiModelProperty("代理库存信息")
    private List<CloudStockInfo> data;
}