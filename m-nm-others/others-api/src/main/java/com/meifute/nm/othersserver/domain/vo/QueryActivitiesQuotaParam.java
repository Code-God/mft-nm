package com.meifute.nm.othersserver.domain.vo;

import com.meifute.nm.othersserver.domain.param.BaseParam;
import lombok.Data;

/**
 * @Classname QueryActivitiesQuotaParam
 * @Description
 * @Date 2020-08-06 18:33
 * @Created by MR. Xb.Wu
 */
@Data
public class QueryActivitiesQuotaParam extends BaseParam {

    private String statisticsId;

    private String agentPhone;

    private String agentName;
}
