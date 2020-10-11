package com.meifute.nm.othersserver.domain.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @Classname QueryNoveltyAgent
 * @Description
 * @Date 2020-08-19 18:37
 * @Created by MR. Xb.Wu
 */
@Data
public class QueryNoveltyAgent implements Serializable {

    private String userId;

    private String nextUserId;

    protected Integer pageCurrent;

    protected Integer pageSize;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime startTime;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime endTime;

}
