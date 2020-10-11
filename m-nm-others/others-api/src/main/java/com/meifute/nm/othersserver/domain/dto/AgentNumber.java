package com.meifute.nm.othersserver.domain.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @Classname AgentNumber
 * @Description
 * @Date 2020-07-13 15:28
 * @Created by MR. Xb.Wu
 */
@Data
public class AgentNumber implements Serializable {

    private String userId;

    private Integer number;
}
