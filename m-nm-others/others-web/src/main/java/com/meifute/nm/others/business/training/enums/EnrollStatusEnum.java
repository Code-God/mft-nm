package com.meifute.nm.others.business.training.enums;

import com.google.common.base.Objects;
import lombok.Getter;

/**
 * @Classname StatusCodeEnum
 * @Description
 * @Date 2020-07-07 15:37
 * @Created by MR. Xb.Wu
 */
public enum EnrollStatusEnum {

    ENROLL_FAIL("0", "报名成功未成功"),
    ENROLL_SUCCESS("1", "报名成功");


    @Getter
    private String code;
    @Getter
    private String desc;


    EnrollStatusEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static String explain(String code) {
        for (EnrollStatusEnum enrollStatusEnum : EnrollStatusEnum.values()) {
            if (Objects.equal(code, enrollStatusEnum.code)) {
                return enrollStatusEnum.desc;
            }
        }
        return "";
    }
}
