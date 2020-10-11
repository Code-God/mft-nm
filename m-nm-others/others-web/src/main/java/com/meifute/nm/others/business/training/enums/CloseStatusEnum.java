package com.meifute.nm.others.business.training.enums;

import com.google.common.base.Objects;
import lombok.Getter;

/**
 * @Classname CloseStatusEnum
 * @Description
 * @Date 2020-07-20 11:01
 * @Created by MR. Xb.Wu
 */
public enum  CloseStatusEnum {

    NORMAL("0", "正常"),
    CLOSED("1", "已取消");


    @Getter
    private String code;
    @Getter
    private String desc;


    CloseStatusEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static String explain(String code) {
        for (CloseStatusEnum closeStatusEnum : CloseStatusEnum.values()) {
            if (Objects.equal(code, closeStatusEnum.code)) {
                return closeStatusEnum.desc;
            }
        }
        return "";
    }
}
