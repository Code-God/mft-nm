package com.meifute.nm.auth.entity;

import com.baomidou.mybatisplus.annotation.Version;
import java.time.LocalDateTime;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;

/**
 * <p>
 * 角色权限表
 * </p>
 *
 * @author chen
 * @since 2020-08-17
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ApiModel(value="MallRolePermission对象", description="角色权限表")
public class MallRolePermission implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "role id")
    private Long roleId;

    @ApiModelProperty(value = "permission id")
    private Long permissionId;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;


}
