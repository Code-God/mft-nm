package com.meifute.nm.others.business.training.enums;

import com.google.common.base.Objects;
import lombok.Getter;

/**
 * @Classname MeetingEnrollSatatusEnum
 * @Description
 * @Date 2020-07-08 16:22
 * @Created by MR. Xb.Wu
 */
public enum MeetingEnrollStatusEnum {

    NOT_ENROLL_STATUS("0", "未报名"),
    AC_END("1", "活动已结束"),
    ENROLL_END("2", "报名时间已截止"),
    AC_CLOSE("3", "活动已取消"),
    ENROLLED_BACKING("4", "已签到报名费退回中"),
    ENROLLED_BACKED("5", "已签到报名费已退还"),
    SING_UP_ED("6", "已签到"),
    ENROLLED("7","已报名");

    @Getter
    private String code;
    @Getter
    private String desc;


    MeetingEnrollStatusEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static String explain(String code) {
        for (MeetingEnrollStatusEnum meetingEnrollStatusEnum : MeetingEnrollStatusEnum.values()) {
            if (Objects.equal(code, meetingEnrollStatusEnum.code)) {
                return meetingEnrollStatusEnum.desc;
            }
        }
        return "";
    }
}
