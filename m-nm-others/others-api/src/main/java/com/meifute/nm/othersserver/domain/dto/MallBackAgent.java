package com.meifute.nm.othersserver.domain.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;


/**
 * @Auther: wuxb
 * @Date: 2019-02-27 13:47
 * @Auto: I AM A CODE MAN -_-!
 * @Description:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MallBackAgent implements Serializable {

    private String id;

    private String userId;

    private Date backDate;

    private String adminCode;

    private Date registerDate;

    private String backAgoLeader;

    private String backRemark;

    private String backImg;

    private String backStatus;

    private String status;

    private Date createDate;

    private String is_del;

    @ApiModelProperty(value = "退代类型 1线下 2线上")
    private String type;

    @ApiModelProperty(value = "用户昵称")
    private String nickName;

    @ApiModelProperty(value = "用户手机")
    private String phone;

    @ApiModelProperty(value = "用户头像")
    private String icon;
    @ApiModelProperty(value = "用户等级")
    private String level;

    @ApiModelProperty(value = "推荐人昵称")
    private String referNickName;

}
