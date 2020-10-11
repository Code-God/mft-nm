package com.meifute.nm.auth.entity.vo;

import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.Version;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 用户表
 * </p>
 *
 * @author chen
 * @since 2020-08-17
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value="MallUser对象更改密码VO", description="MallUser对象更改密码VO")
public class MallUserChangePasswordVO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "电话",required = true)
    private String phone;

    @ApiModelProperty(value = "验证码",required = true)
    private String code;

    @ApiModelProperty(value = "新密码",required = true)
    private String newPassword;

    @ApiModelProperty(value = "新密码确认",required = true)
    private String newPasswordConfirm;

}
