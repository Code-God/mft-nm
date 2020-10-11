package com.meifute.nm.othersserver.domain.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Classname QueryNoveltyAgentDto
 * @Description
 * @Date 2020-08-19 19:22
 * @Created by MR. Xb.Wu
 */
@Data
public class QueryNoveltyAgentDto implements Serializable {

    private String userId;

    private List<NoveltyAgentDto> noveltyAgents;

    @ApiModelProperty("是否已查到有新招总代")
    private boolean haveNoveltyAgent;
}
