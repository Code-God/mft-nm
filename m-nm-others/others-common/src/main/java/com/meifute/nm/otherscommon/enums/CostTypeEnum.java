package com.meifute.nm.otherscommon.enums;

import com.google.common.base.Objects;
import lombok.Getter;

/**
 * @Classname CostTypeEnum
 * @Description
 * @Date 2020-07-08 19:17
 * @Created by MR. Xb.Wu
 */
public enum CostTypeEnum {

    RMB("0","人民币"),
    CREDIT("1", "积分");

    @Getter
    private String code;
    @Getter
    private String desc;


    CostTypeEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static String explain(String code) {
        for (CostTypeEnum costTypeEnum : CostTypeEnum.values()) {
            if (Objects.equal(code, costTypeEnum.code)) {
                return costTypeEnum.desc;
            }
        }
        return "";
    }

}
