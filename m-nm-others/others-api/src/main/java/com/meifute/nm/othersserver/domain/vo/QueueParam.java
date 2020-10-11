package com.meifute.nm.othersserver.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Auther: wxb
 * @Date: 2018/10/29 17:45
 * @Auto: I AM A CODE MAN -_-!
 * @Description:
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QueueParam implements Serializable {

    private String message;

    private long times;
}
