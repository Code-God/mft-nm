package com.meifute.nm.othersserver.domain.vo;

import com.meifute.nm.othersserver.domain.param.BaseParam;
import lombok.Data;

/**
 * @Classname QueryMeetingEnrollParam
 * @Description TODO
 * @Date 2020-07-07 10:33
 * @Created by MR. Xb.Wu
 */
@Data
public class QueryMeetingEnrollParam extends BaseParam {

    private String adminCode;

    private String meetingId;

    private String status;

    private String phone;

    private String meetingName;

}
