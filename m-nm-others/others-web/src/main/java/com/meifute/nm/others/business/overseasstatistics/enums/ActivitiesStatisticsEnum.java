package com.meifute.nm.others.business.overseasstatistics.enums;

import com.google.common.base.Objects;
import lombok.Getter;

/**
 * @Classname AgentRangeEnum
 * @Description
 * @Date 2020-07-08 17:16
 * @Created by MR. Xb.Wu
 */
public enum ActivitiesStatisticsEnum {

    NO_PAYMENT(0, "未付费"),
    PAYMENT(1, "付费");


    @Getter
    private int code;
    @Getter
    private String desc;


    ActivitiesStatisticsEnum(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static String explain(int code) {
        for (ActivitiesStatisticsEnum agentStatisticsEnum : ActivitiesStatisticsEnum.values()) {
            if (Objects.equal(code, agentStatisticsEnum.code)) {
                return agentStatisticsEnum.desc;
            }
        }
        return "";
    }
}
