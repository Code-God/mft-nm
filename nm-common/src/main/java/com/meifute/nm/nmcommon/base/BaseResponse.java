package com.meifute.nm.nmcommon.base;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Auther: wxb
 * @Date: 2018/7/23 17:14
 * @Auto: I AM A CODE MAN !
 * @Description:
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BaseResponse {

    private String code;

    private String msg;

}
