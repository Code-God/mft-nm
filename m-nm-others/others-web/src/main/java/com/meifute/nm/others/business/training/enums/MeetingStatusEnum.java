package com.meifute.nm.others.business.training.enums;

import com.google.common.base.Objects;
import lombok.Getter;

/**
 * @Classname MeetingStatusEnum
 * @Description
 * @Date 2020-07-08 16:31
 * @Created by MR. Xb.Wu
 */
public enum MeetingStatusEnum {

    //0待发布，1已发布，2已结束，3已取消
    READY_RELEASE("0", "待发布"),
    AC_RELEASED("1", "已发布"),
    AC_END("2", "已结束"),
    AC_CLOSED("3", "已取消");


    @Getter
    private String code;
    @Getter
    private String desc;


    MeetingStatusEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static String explain(String code) {
        for (MeetingStatusEnum meetingStatusEnum : MeetingStatusEnum.values()) {
            if (Objects.equal(code, meetingStatusEnum.code)) {
                return meetingStatusEnum.desc;
            }
        }
        return "";
    }
}
