package com.meifute.nm.others.business.training.enums;

import com.google.common.base.Objects;
import lombok.Getter;

/**
 * @Classname SignUpStatusEnum
 * @Description
 * @Date 2020-07-08 17:00
 * @Created by MR. Xb.Wu
 */
public enum SignUpStatusEnum {

    NOT_SIGN_UP("0", "未签到"),
    SIGN_UP_ED("1", "已签到");

    @Getter
    private String code;
    @Getter
    private String desc;


    SignUpStatusEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static String explain(String code) {
        for (SignUpStatusEnum signUpStatusEnum : SignUpStatusEnum.values()) {
            if (Objects.equal(code, signUpStatusEnum.code)) {
                return signUpStatusEnum.desc;
            }
        }
        return "";
    }
}
