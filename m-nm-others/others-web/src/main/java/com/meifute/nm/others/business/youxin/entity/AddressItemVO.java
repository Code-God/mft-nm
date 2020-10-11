package com.meifute.nm.others.business.youxin.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @ClassName: AddressItemVO
 * @Description:
 * @Author: Chen
 * @Date: 2020/7/16 19:31
 * @Version: 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddressItemVO implements Serializable {

    @ApiModelProperty("收件人姓名")
    private String receiverName;

    @ApiModelProperty("收件人手机")
    private String receiverPhone;

    @ApiModelProperty("省")
    private String province;

    @ApiModelProperty("市")
    private String city;

    @ApiModelProperty("区")
    private String district;

    @ApiModelProperty("地址")
    private String address;
}