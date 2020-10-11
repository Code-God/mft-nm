package com.meifute.nm.auth.base;


/**
 * @Classname RequestLogAdvice
 * @Description
 * @Date 2020-07-06 19:48
 * @Created by MR. Xb.Wu
 */
public class SingletonMallResponse {

    private static final String SUCCESS_CODE = "200";
    private static final String SUCCESS_MESSAGE = "SUCCESS";
    private static final String FAILED_CODE = "500";
    private static final String FAILED_MESSAGE = "FAILED";
    private static BaseResponse successBaseResponse = new BaseResponse(SUCCESS_CODE, SUCCESS_MESSAGE);
    private static BaseResponse failedBaseResponse = new BaseResponse(FAILED_CODE, FAILED_MESSAGE);

    private SingletonMallResponse() {
    }

    public static BaseResponse getSuccessBaseResponse() {
        if (successBaseResponse == null) {
            successBaseResponse = new BaseResponse(SUCCESS_CODE, SUCCESS_MESSAGE);
        }
        return successBaseResponse;
    }

    public static BaseResponse getFailedBaseResponse() {
        if (failedBaseResponse == null) {
            failedBaseResponse = new BaseResponse(FAILED_CODE, FAILED_MESSAGE);
        }
        return failedBaseResponse;
    }

}
