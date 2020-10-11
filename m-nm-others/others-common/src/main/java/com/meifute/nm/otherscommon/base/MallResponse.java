package com.meifute.nm.otherscommon.base;

import lombok.Data;

/**
 * @Auther: wxb
 * @Date: 2018/7/20 16:49
 * @Auto: I AM A CODE MAN !
 * @Description:
 */
@Data
public class MallResponse<T> {

    private String currentSystemTimeStamp;

    private String rtnCode;

    private String rtnExt;

    private String rtnFtype;

    private String rtnMsg;

    private String rtnTip;

    private BaseResponse baseResponse;

    private T data;

}
