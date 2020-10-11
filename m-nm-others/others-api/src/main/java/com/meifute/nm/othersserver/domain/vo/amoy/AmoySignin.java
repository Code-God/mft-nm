package com.meifute.nm.othersserver.domain.vo.amoy;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @Classname AmoyActivitySignin
 * @Description
 * @Date 2020-08-19 17:36
 * @Created by MR. Xb.Wu
 */
@Data
public class AmoySignin implements Serializable {

    private String id;
    @ApiModelProperty("报名id")
    private String enrollId;
    @ApiModelProperty("用户id")
    private String userId;
    @ApiModelProperty("签到场所，1酒店，2旅游车，3会议大厅，4晚宴")
    private Integer signInPlace;
    @ApiModelProperty("签到时的经纬度")
    private String longitudeAndLatitude;

}
