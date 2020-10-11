package com.meifute.nm.others.config.feignconf;

import com.meifute.nm.otherscommon.enums.SysErrorEnums;
import com.meifute.nm.otherscommon.exception.BusinessException;
import com.meifute.nm.otherscommon.exception.ExceptionEntity;
import com.meifute.nm.otherscommon.utils.JsonUtils;
import feign.Response;
import feign.Util;
import feign.codec.ErrorDecoder;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;

@Slf4j
public class FeignErrorDecoder implements ErrorDecoder {

    private RuntimeException e;
    private static final String SUCCESS = "200";

    public FeignErrorDecoder(RuntimeException e) {
        this.e = e;
    }

    @Override
    public Exception decode(String s, Response response) {
        Exception exception = new BusinessException(SysErrorEnums.SYSTEM_ERROR);
        try {
            // 获取原始的返回内容
            String json = Util.toString(response.body().asReader(StandardCharsets.UTF_8));
            exception = new RuntimeException(json);
            // 将返回内容反序列化为自定义Result
            ExceptionEntity result = JsonUtils.jsonToPojo(json, ExceptionEntity.class);
            if (result == null || result.getBaseResponse() == null || result.getBaseResponse().getMsg() == null) {
                return exception;
            }
            // 业务异常抛出简单的 RuntimeException，保留原来错误信息
            if (!SUCCESS.equals(result.getBaseResponse().getCode())) {
                exception = new BusinessException(result.getBaseResponse().getCode(), result.getBaseResponse().getMsg(), result.getServer());
            }
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
        }
        return exception;
    }
}
