package com.meifute.nm.othersserver.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @Classname QueryNoveltyAgent
 * @Description 查询新招总代
 * @Date 2020-08-19 19:18
 * @Created by MR. Xb.Wu
 */
@Data
public class QueryNoveltyAgentInput implements Serializable {

    private String userId;

    private List<String> nextUserId;

    private Integer pageCurrent;

    private Integer pageSize;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime startTime;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime endTime;

}
