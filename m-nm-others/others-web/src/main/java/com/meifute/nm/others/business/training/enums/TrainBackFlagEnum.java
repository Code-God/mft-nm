package com.meifute.nm.others.business.training.enums;

import com.google.common.base.Objects;
import lombok.Getter;

/**
 * @Classname TrainBackFlagEnum
 * @Description
 * @Date 2020-07-09 12:52
 * @Created by MR. Xb.Wu
 */
public enum TrainBackFlagEnum {

    CAN_BACK(0, "签到后返回账户"),
    DO_NOT_BACK(1, "不退");


    @Getter
    private int code;
    @Getter
    private String desc;


    TrainBackFlagEnum(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static String explain(int code) {
        for (TrainBackFlagEnum trainBackFlagEnum : TrainBackFlagEnum.values()) {
            if (Objects.equal(code, trainBackFlagEnum.code)) {
                return trainBackFlagEnum.desc;
            }
        }
        return "";
    }
}
