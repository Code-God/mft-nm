package com.meifute.nm.others.business.youxin.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MallRegulateInfoDTO implements Serializable {

    private String id;

    private String mallUserId;

    private String adminId;

    private String regulateType;

    private String orderId;

    private Date createDate;

    private Date updateDate;

    private String status;

    private String memo;

    private String isDel;

}