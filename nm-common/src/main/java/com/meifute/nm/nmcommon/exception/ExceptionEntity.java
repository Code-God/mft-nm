package com.meifute.nm.nmcommon.exception;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.meifute.nm.nmcommon.base.BaseResponse;
import lombok.Data;

/**
 * @Classname ExceptionEntity
 * @Description TODO
 * @Date 2020-07-06 19:48
 * @Created by MR. Xb.Wu
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ExceptionEntity<T> {

    private String currentSystemTimeStamp;

    private String error;

    private String path;

    private String server;

    private BaseResponse baseResponse;

    private T data;
}
