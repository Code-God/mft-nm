package com.meifute.nm.others.business.overseasstatistics.enums;

import com.google.common.base.Objects;
import lombok.Getter;

/**
 * @Classname AgentRangeEnum
 * @Description
 * @Date 2020-07-08 17:16
 * @Created by MR. Xb.Wu
 */
public enum ActivitiesQuotaEnum {

    PARTICIPANTS(0, "0参会人"),
    QUALIFIED_PERSON(1, "1达标人"),
    RETROGRESSION(2, "2达标退代"),
    DEDUCTED_PERSON(3, "3其他活动抵扣名额");


    @Getter
    private int code;
    @Getter
    private String desc;


    ActivitiesQuotaEnum(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static String explain(int code) {
        for (ActivitiesQuotaEnum activitiesQuotaEnum : ActivitiesQuotaEnum.values()) {
            if (Objects.equal(code, activitiesQuotaEnum.code)) {
                return activitiesQuotaEnum.desc;
            }
        }
        return "";
    }
}
