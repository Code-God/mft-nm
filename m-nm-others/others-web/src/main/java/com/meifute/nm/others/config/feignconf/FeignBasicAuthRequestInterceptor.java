package com.meifute.nm.others.config.feignconf;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * @Classname FeignBasicAuthRequestInterceptor
 * @Description token传递
 * @Date 2020-07-06 17:20
 * @Created by MR. Xb.Wu
 */
@Slf4j
public class FeignBasicAuthRequestInterceptor implements RequestInterceptor {

    @Override
    public void apply(RequestTemplate requestTemplate) {
        try {
            RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
            HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
            Object attribute = request.getAttribute("OAuth2AuthenticationDetails.ACCESS_TOKEN_VALUE");
            String token = attribute == null ? "" : attribute.toString();
            if (!StringUtils.isEmpty(token)) {
                log.info("FeignBasicAuthRequestInterceptor apply ->:{}", token);
                requestTemplate.header("Authorization", "Bearer " + token);
            }
        } catch (Exception e) {
            log.info("FeignBasicAuthRequestInterceptor apply ->{}", e.getMessage());
        }

    }
}
