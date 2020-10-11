package com.meifute.nm.others.business.training.enums;

import com.google.common.base.Objects;
import lombok.Getter;

/**
 * @Classname CostBackStatusEnum
 * @Description
 * @Date 2020-07-08 16:49
 * @Created by MR. Xb.Wu
 */
public enum CostBackStatusEnum {

    COST_NOT_BACKED("0","未退款"),
    COST_BACKING("1","退款中"),
    COST_BACKED("2","已退款");


    @Getter
    private String code;
    @Getter
    private String desc;


    CostBackStatusEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static String explain(String code) {
        for (CostBackStatusEnum backStatusEnum : CostBackStatusEnum.values()) {
            if (Objects.equal(code, backStatusEnum.code)) {
                return backStatusEnum.desc;
            }
        }
        return "";
    }
}
