package com.meifute.nm.auth.enums;

import com.google.common.base.Objects;
import lombok.Getter;

/**
 * @ClassName:ResultEnum
 * @Description:
 * @Author:Chen
 * @Date:2020/8/20 15:19
 * @Version:1.0
 */
public enum ResultEnum {
    FAIL("fail", "失败"),
    SUCCESS("success", "成功");


    @Getter
    private String code;
    @Getter
    private String desc;


    ResultEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static String explain(String code) {
        for (ResultEnum enums : ResultEnum.values()) {
            if (Objects.equal(code, enums.code)) {
                return enums.desc;
            }
        }
        return "";
    }
}
