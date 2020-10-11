package com.meifute.nm.otherscommon.exception;

import com.meifute.nm.otherscommon.base.BaseResponse;
import com.meifute.nm.otherscommon.enums.SysErrorEnums;
import com.meifute.nm.otherscommon.utils.JsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Classname GlobalExceptionHandler
 * @Description 异常拦截
 * @Date 2020-07-06 17:33
 * @Created by MR. Xb.Wu
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @Value("${spring.application.name}")
    private String applicationName;

    /**
     * 404异常处理
     */
    @ExceptionHandler(value = NoHandlerFoundException.class)
    public ResponseEntity<ExceptionEntity> errorHandler(HttpServletRequest request, NoHandlerFoundException exception) {
        log.error("this is a exception:{0}", exception);
        return commonHandler(request,
                exception.getClass().getSimpleName(),
                exception.getMessage(),
                HttpStatus.NOT_FOUND, String.valueOf(HttpStatus.NOT_FOUND.value()), applicationName);
    }

    /**
     * 405异常处理
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ExceptionEntity> errorHandler(HttpServletRequest request, HttpRequestMethodNotSupportedException exception) {
        log.error("this is a exception:{0}", exception);
        return commonHandler(request,
                exception.getClass().getSimpleName(),
                exception.getMessage(),
                HttpStatus.METHOD_NOT_ALLOWED, String.valueOf(HttpStatus.METHOD_NOT_ALLOWED.value()), applicationName);
    }

    /**
     * 415异常处理
     */
    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public ResponseEntity<ExceptionEntity> errorHandler(HttpServletRequest request, HttpMediaTypeNotSupportedException exception) {
        log.error("this is a exception:{0}", exception);
        return commonHandler(request,
                exception.getClass().getSimpleName(),
                exception.getMessage(),
                HttpStatus.UNSUPPORTED_MEDIA_TYPE, String.valueOf(HttpStatus.UNSUPPORTED_MEDIA_TYPE.value()), applicationName);
    }

    /**
     * 500异常处理
     */
    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<ExceptionEntity> errorHandler(HttpServletRequest request, Exception exception) {
        log.error("this is a exception:{0}", exception);
        return commonHandler(request,
                exception.getClass().getSimpleName(),
                SysErrorEnums.SYSTEM_ERROR.getErrorMessage(),
                HttpStatus.INTERNAL_SERVER_ERROR, SysErrorEnums.SYSTEM_ERROR.getErrorCode(), applicationName);
    }

    /**
     * 业务异常处理
     */
    @ExceptionHandler(value = BusinessException.class)
    private ResponseEntity<ExceptionEntity> errorHandler(HttpServletRequest request, BusinessException e) {
        log.info("================该异常作为正常业务响应返回==============>: serverName:{}, code:{}, message:{}",
                e.getServer() == null ? applicationName : e.getServer(), e.getCode(), e.getMsg());
        return commonHandler(request,
                e.getClass().getSimpleName(),
                e.getMsg(),
                HttpStatus.BAD_REQUEST, e.getCode(), e.getServer());
    }


    /**
     * 表单验证异常处理
     */
    @ExceptionHandler(value = BindException.class)
    public ResponseEntity<ExceptionEntity> validExceptionHandler(BindException exception, HttpServletRequest request) {
        log.error("this is a exception:{0}", exception);
        List<FieldError> fieldErrors = exception.getBindingResult().getFieldErrors();
        Map<String, String> errors = new HashMap<>();
        for (FieldError error : fieldErrors) {
            errors.put(error.getField(), error.getDefaultMessage());
        }
        return commonHandler(request,
                exception.getClass().getSimpleName(),
                JsonUtils.objectToJson(errors),
                HttpStatus.INTERNAL_SERVER_ERROR,
                String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()),
                applicationName);
    }

    /**
     * 异常处理数据处理
     *
     * @return
     */
    private ResponseEntity<ExceptionEntity> commonHandler(HttpServletRequest request, String error, String message, HttpStatus httpStatus, String code, String serverName) {
        ExceptionEntity entity = new ExceptionEntity();
        entity.setPath(request.getRequestURI());
        entity.setError(error);
        entity.setCurrentSystemTimeStamp(String.valueOf(System.currentTimeMillis()));
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setCode(code);
        baseResponse.setMsg(message);
        entity.setBaseResponse(baseResponse);
        entity.setServer(serverName == null ? this.applicationName: serverName);
        return new ResponseEntity<>(entity, httpStatus);
    }

}
