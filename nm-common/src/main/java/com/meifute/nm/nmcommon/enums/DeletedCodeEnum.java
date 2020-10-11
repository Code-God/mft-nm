package com.meifute.nm.nmcommon.enums;

import com.google.common.base.Objects;
import lombok.Getter;

/**
 * @Classname StatusCodeEnum
 * @Description
 * @Date 2020-07-07 15:37
 * @Created by MR. Xb.Wu
 */
public enum DeletedCodeEnum {

    NOT_DELETE(0, "未删除"),
    DELETED(1, "已删除");


    @Getter
    private int code;
    @Getter
    private String desc;


    DeletedCodeEnum(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static String explain(int code) {
        for (DeletedCodeEnum deletedCodeEnum : DeletedCodeEnum.values()) {
            if (Objects.equal(code, deletedCodeEnum.code)) {
                return deletedCodeEnum.desc;
            }
        }
        return "";
    }
}
