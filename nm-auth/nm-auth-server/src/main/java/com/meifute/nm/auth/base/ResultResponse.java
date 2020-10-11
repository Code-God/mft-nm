package com.meifute.nm.auth.base;

/**
 * @Classname RessultResponse
 * @Description
 * @Date 2020-07-07 13:49
 * @Created by MR. Xb.Wu
 */
public class ResultResponse<T> {

    private MallResponse<T> response;

    public ResultResponse() {
    }

    public MallResponse<T> successResult(T t) {
        response = new MallResponse<>();
        response.setData(t);
        response.setCurrentSystemTimeStamp(String.valueOf(System.currentTimeMillis()));
        response.setBaseResponse(SingletonMallResponse.getSuccessBaseResponse());
        return response;
    }
}
