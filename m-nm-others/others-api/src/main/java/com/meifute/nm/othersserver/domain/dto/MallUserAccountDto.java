package com.meifute.nm.othersserver.domain.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @Auther: liuzh
 * @Date: 2018/7/25 16:25
 * @Auto: I AM A CODE MAN !
 * @Description:
 */
@Data
@ApiModel(value = "获取用户账户信息")
public class MallUserAccountDto implements Serializable {

    @ApiModelProperty(value = "id")
    private String accountCode;
    @ApiModelProperty(value = "用户id")
    private String mallUserId;
    @ApiModelProperty(value = "余额")
    private BigDecimal amt;
    @ApiModelProperty(value = "积分")
    private BigDecimal credit;
    @ApiModelProperty(value = "非提现余额")
    private BigDecimal noPutAmt;
    @ApiModelProperty(value = "0新账户，1非新账户")
    private String isNewStatus;
    @ApiModelProperty(value = "状态，0正常，1禁用，2注销")
    private String status;
    @ApiModelProperty(value = "冻结余额")
    private BigDecimal amount;

}
