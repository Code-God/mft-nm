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
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value="MallUser对象新增VO", description="MallUser对象新增VO")
public class UserAddVO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "电话",required = true)
    private String phone;

    @ApiModelProperty(value = "用户名",required = true)
    private String username;

    @ApiModelProperty(value = "密码",required = true)
    private String password;

}
