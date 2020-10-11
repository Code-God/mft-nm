package com.meifute.nm.otherscommon.enums;

import com.meifute.nm.otherscommon.exception.IErrorCode;

/**
 * @Classname SysErrorEnums
 * @Description
 * @Date 2020-07-08 14:29
 * @Created by MR. Xb.Wu
 */
public enum SysErrorEnums implements IErrorCode {

    SYSTEM_ERROR("500", "当前系统繁忙，请稍后再试～"),
    REPEAT_REQUEST("9000", "重复请求"),
    EMPTY_PARAME("10000", "参数为空"),
    ERROR_PARAME("10002", "参数错误"),
    AC_EXPIRE("10003", "活动已过期"),
    ENROLLED("10004", "您已报名"),
    ENRROL_END("10005", "已截止报名"),
    AC_NOT_START("10006", "活动未开始"),
    NOT_QUALIFICATIONS("10007", "您还没有参与活动的资格哦，请联系上级或专属客服"),
    DO_NOT_RELEASE("10008", "该条活动不能发布"),
    NOT_ENROLLED("10009", "您还没有报名，不能签到"),
    NOT_DO_BACK_COST("10010", "无需退款或已经退款"),
    PROHIBIT_BACK_COST("10011", "该活动不退款"),
    AC_CLOSED("10012", "活动已取消"),
    NOT_DATA("10013", "暂无数据"),
    AC_ENROLL_EXPIRE("10014", "报名时间已截止"),
    NOT_ENROLL("10015", "您还没有报名"),
    REFUNDED("10016", "已经退款"),
    NOT_PAY("10017", "没有付款记录"),
    REFUNDING("10018", "正在退款中");

    private String errorCode;
    private String errorMessage;

    private SysErrorEnums(String errorCode, String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
