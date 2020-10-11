package com.meifute.nm.auth.entity.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * <p>
 * 用户表
 * </p>
 *
 * @author chen
 * @since 2020-08-17
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value="MallUser对象更新VO", description="MallUser对象更新VO")
public class UserUpdateVO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键id",required = true)
    private Long id;

    @ApiModelProperty(value = "电话")
    private String phone;

    @ApiModelProperty(value = "用户名")
    private String username;

}
