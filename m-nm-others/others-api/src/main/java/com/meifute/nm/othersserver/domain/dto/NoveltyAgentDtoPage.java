package com.meifute.nm.othersserver.domain.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Classname NoveltyAgentDtoPage
 * @Description
 * @Date 2020-08-20 18:02
 * @Created by MR. Xb.Wu
 */
@Data
public class NoveltyAgentDtoPage implements Serializable {

    private List<NoveltyAgentDto> records;

    private Integer total;

    private Integer pageCurrent;

    private Integer pageSize;
}
