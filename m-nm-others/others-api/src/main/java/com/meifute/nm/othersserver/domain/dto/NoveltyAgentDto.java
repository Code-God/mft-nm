package com.meifute.nm.othersserver.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @Classname NoveltyAgentDto
 * @Description
 * @Date 2020-08-19 19:23
 * @Created by MR. Xb.Wu
 */
@Data
public class NoveltyAgentDto implements Serializable {

    private String userId;

    private String name;

    private String phone;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime upgradeTime;
}
