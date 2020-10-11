package com.meifute.nm.others.enums;

import com.google.common.base.Objects;
import com.meifute.nm.otherscommon.enums.AgentLevelEnum;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;

/**
 * @Classname AgentRangeEnum
 * @Description
 * @Date 2020-07-08 17:16
 * @Created by MR. Xb.Wu
 */
public enum AgentRangeEnum {

    EVERYONE(0, "任何人"),
    SUPER_GRADE(1, "总代以上"),
    ONE_GRADE(2, "一级以上以上"),
    VIP_GRADE(3, "VIP以上");

    private static final String AGENT_LEVEL_SUPER = "4";

    @Getter
    private int code;
    @Getter
    private String desc;


    AgentRangeEnum(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static String explain(int code) {
        for (AgentRangeEnum agentRangeEnum : AgentRangeEnum.values()) {
            if (Objects.equal(code, agentRangeEnum.code)) {
                return agentRangeEnum.desc;
            }
        }
        return "";
    }

    public static boolean haveQualifications(String agentLevel, int code) {
        if (code == AgentRangeEnum.EVERYONE.getCode()) {
            return true;
        }
        if (code == AgentRangeEnum.SUPER_GRADE.getCode()) {
            return AgentLevelEnum.SUPER.getCode().equals(agentLevel);
        }
        if (code == AgentRangeEnum.ONE_GRADE.getCode()) {
            List<String> list = Arrays.asList(AgentLevelEnum.SUPER.getCode(), AgentLevelEnum.ONE.getCode());
            return list.contains(agentLevel);
        }
        if (code == AgentRangeEnum.VIP_GRADE.getCode()) {
            return !AgentLevelEnum.ORDINARY.getCode().equals(agentLevel);
        }
        return false;
    }
}
