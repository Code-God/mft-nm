package com.meifute.nm.others.business.youxin.enums;

import com.google.common.base.Objects;
import lombok.Getter;

/**
 * @Auther: wxb
 * @Date: 2018/10/11 15:57
 * @Auto: I AM A CODE MAN -_-!
 * @Description:
 */
public enum YouXinResponseCodeEnum {

    RESPONSE_CODE_0000("0", "成功"),
    RESPONSE_CODE_2000("2000", "验证签名失败"),
    RESPONSE_CODE_2001("2001", "数据格式错误"),
    RESPONSE_CODE_2002("2002", "系统异常或超时"),
    RESPONSE_CODE_3000("3000", "手机号不存在"),
    RESPONSE_CODE_3001("3001", "用户编号不存在"),
    RESPONSE_CODE_3002("3002", "手机号与用户编号不匹配"),
    RESPONSE_CODE_5010("5010", "主订单号不存在"),
    RESPONSE_CODE_5011("5011", "子订单号不存在"),
    RESPONSE_CODE_5012("5012", "已发货不能取消"),
    RESPONSE_CODE_5013("5013", "订单已经被取消"),
    RESPONSE_CODE_5014("5014", "订单号已经存在"),


    X("X", "X");

    @Getter
    private String code;
    @Getter
    private String desc;


    YouXinResponseCodeEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static String explain(String code) {
        for (YouXinResponseCodeEnum responseCodeEnum : YouXinResponseCodeEnum.values()) {
            if (Objects.equal(code, responseCodeEnum.code)) {
                return responseCodeEnum.desc;
            }
        }
        return code;
    }
}
