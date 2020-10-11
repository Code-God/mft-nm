package com.meifute.nm.otherscommon.enums;

import com.google.common.base.Objects;
import lombok.Getter;

/**
 * @Classname AgentLevelEnum
 * @Description
 * @Date 2020-07-08 17:24
 * @Created by MR. Xb.Wu
 */
public enum AgentLevelEnum {

    SUPER("4","总代理"),
    ONE("1", "一级代理"),
    VIP("3","vip客户"),
    ORDINARY("0", "普通用户");

    @Getter
    private String code;
    @Getter
    private String desc;


    AgentLevelEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static String explain(String code) {
        for (AgentLevelEnum agentLevelEnum : AgentLevelEnum.values()) {
            if (Objects.equal(code, agentLevelEnum.code)) {
                return agentLevelEnum.desc;
            }
        }
        return "";
    }
}
