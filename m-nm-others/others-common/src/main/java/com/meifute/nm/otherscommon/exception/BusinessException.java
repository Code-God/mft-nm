package com.meifute.nm.otherscommon.exception;

import lombok.Data;

import java.util.Map;

@Data
public class BusinessException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private String code;
    private String server;
    private String msg;

    private IErrorCode iErrorCode;
    private Map<String, Object> errorData;

    public BusinessException(IErrorCode iErrorCode) {
        super();
        this.iErrorCode = iErrorCode;
        this.code = iErrorCode.getErrorCode();
        this.msg = iErrorCode.getErrorMessage();
    }

    public BusinessException() {
        super();
    }

    public BusinessException(String code, String message) {
        super(message);
        this.msg = message;
        this.code = code;
    }

    public BusinessException(String code, String message, String server) {
        super(message);
        this.code = code;
        this.msg = message;
        this.server = server;
    }
}
