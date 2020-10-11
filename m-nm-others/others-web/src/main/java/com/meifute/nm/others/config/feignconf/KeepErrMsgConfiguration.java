package com.meifute.nm.others.config.feignconf;

import com.meifute.nm.otherscommon.exception.BusinessException;
import feign.codec.ErrorDecoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KeepErrMsgConfiguration {

    @Bean
    public ErrorDecoder errorDecoder() {
        return new FeignErrorDecoder(new BusinessException());
    }

}
