package com.meifute.nm.auth.base;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @Classname RequestLogAdvice
 * @Description
 * @Date 2020-07-06 19:48
 * @Created by MR. Xb.Wu
 */
@Slf4j
@Controller
public class BaseController {

    @Autowired
    protected HttpServletRequest request;

    @Autowired
    protected HttpSession session;


    /**
     * 统一成功返回数据
     *
     * @param t
     * @return
     */
    protected <T> MallResponse<T> successResult(T t) {
        MallResponse<T> response = new MallResponse<>();
        response.setCurrentSystemTimeStamp(String.valueOf(System.currentTimeMillis()));
        response.setBaseResponse(SingletonMallResponse.getSuccessBaseResponse());
        response.setData(t);
        return response;
    }


    /**
     * 统一失败返回数据
     *
     * @param t
     * @return
     */
    protected <T> MallResponse<T> failedResult(T t) {
        MallResponse<T> response = new MallResponse<>();
        response.setCurrentSystemTimeStamp(String.valueOf(System.currentTimeMillis()));
        response.setBaseResponse(SingletonMallResponse.getFailedBaseResponse());
        response.setData(t);
        return response;
    }

}

