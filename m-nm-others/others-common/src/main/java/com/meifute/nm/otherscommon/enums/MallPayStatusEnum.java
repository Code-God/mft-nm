package com.meifute.nm.otherscommon.enums;

import com.google.common.base.Objects;
import lombok.Getter;

/**
 * @Auther: wxb
 * @Date: 2018/10/11 15:57
 * @Auto: I AM A CODE MAN -_-!
 * @Description:
 */
public enum MallPayStatusEnum {

    PAY_STATUS_000("0", "未支付"),
    PAY_STATUS_001("1", "已支付"),
    PAY_STATUS_002("2", "待退款"),
    PAY_STATUS_003("3", "已退款"),
    PAY_STATUS_004("4", "已过期"),
    PAY_STATUS_005("5", "已取消");


    @Getter
    private String code;
    @Getter
    private String desc;


    MallPayStatusEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static String explain(String code) {
        for (MallPayStatusEnum mallPayStatusEnum : MallPayStatusEnum.values()) {
            if (Objects.equal(code, mallPayStatusEnum.code)) {
                return mallPayStatusEnum.desc;
            }
        }
        return code;
    }
}
