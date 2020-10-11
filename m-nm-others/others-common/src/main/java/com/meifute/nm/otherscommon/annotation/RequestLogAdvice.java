package com.meifute.nm.otherscommon.annotation;

import com.google.common.collect.Maps;
import com.meifute.nm.otherscommon.utils.JsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.CodeSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.Map;

/**
 * @Classname RequestLogAdvice
 * @Description AOP日志打印
 * @Date 2020-07-06 19:48
 * @Created by MR. Xb.Wu
 */
@Slf4j
@Aspect
@Component
public class RequestLogAdvice {

    @Pointcut(value = "execution(* com.meifute.nm..*.controller..*.*(..)))")
    public void pointcut() {
    }

    @Around("pointcut()")
    public Object handle(ProceedingJoinPoint joinPoint) throws Throwable {

        Long startTime = System.currentTimeMillis();
        preHandle(joinPoint);

        Object retVal = joinPoint.proceed();

        postHandle(retVal, startTime);

        return retVal;
    }


    private void preHandle(ProceedingJoinPoint joinPoint) {
        try {
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            String requestPath = request.getRequestURI();

            String method_type = request.getMethod();
            Map<String, Object> paramsMap = Maps.newHashMap();
            if ("GET".equalsIgnoreCase(method_type)) {
                //查询全部入参
                Enumeration<String> paramNames = request.getParameterNames();

                while (paramNames.hasMoreElements()) {
                    String paramName = paramNames.nextElement();
                    paramsMap.put(paramName, request.getParameter(paramName));
                }
            } else {
                Object[] paramValues = joinPoint.getArgs();
                String[] paramNameArr = ((CodeSignature) joinPoint.getSignature()).getParameterNames();
                for (int i = 0; i < paramNameArr.length; i++) {
                    paramsMap.put(paramNameArr[i], paramValues[i]);
                }
            }
            log.info("-----------" + method_type + "-------------request[{}]--start--[request params：{}]----------", requestPath, paramsMap);
        } catch (Exception e) {
            log.info("controller拦截器异常....:{0}", e);
        }
    }

    private void postHandle(Object retVal, Long startTime) {
        try {
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();

            Long useTime = System.currentTimeMillis() - startTime;

            String requestPath = request.getRequestURI();
            log.info(" ----------------------response[{}]--end--[耗时：{}ms，response result:{}, response path[{}]]----------", requestPath, useTime, JsonUtils.objectToJson(retVal), requestPath);
        } catch (Exception e) {
            log.info("controller拦截器异常....:{0}", e);
        }
    }

}
