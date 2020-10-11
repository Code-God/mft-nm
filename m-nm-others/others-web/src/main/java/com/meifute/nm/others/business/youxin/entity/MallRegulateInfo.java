package com.meifute.nm.others.business.youxin.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MallRegulateInfo {

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