package com.meifute.nm.others.business.training.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.meifute.nm.others.business.training.entity.AmoyActivityEnroll;
import com.meifute.nm.othersserver.domain.dto.NoveltyAgentDto;
import com.meifute.nm.othersserver.domain.dto.PayParam;
import com.meifute.nm.othersserver.domain.dto.PayResult;
import com.meifute.nm.othersserver.domain.dto.UnifiedWeChatDto;
import com.meifute.nm.othersserver.domain.param.BaseParam;
import com.meifute.nm.othersserver.domain.vo.amoy.*;

import java.util.List;

/**
 * @Classname AmoyActivityEnrollService
 * @Description
 * @Date 2020-08-19 17:42
 * @Created by MR. Xb.Wu
 */
public interface AmoyActivityEnrollService {

    MyEnrollStatus getMyEnrollStatus();

    EnrollVo enroll(AmoyActivityEnrollParam activityEnrollParam);

    EnrollVo nowPay(String enrollId);

    Page<AmoyActivityEnrollAppVO> getMyEnrollPage(BaseParam baseParam);

    AmoyActivityEnroll getByEnrollId(String id);

    boolean cancelEnroll(String id, String remark);

    boolean balancePay(PayParam payParam);

    UnifiedWeChatDto wxUnifiedOrderNew(PayParam payParam);

    String aliUnifiedOrderNew(PayParam payParam);

    Page<QueryEnrollPage> queryEnrollPage(QueryEnrollVO queryEnrollVO);

    Page<NoveltyAgentDto> getNewNoveltyAgentsPage(QueryAgent queryAgent);

    Page<NoveltyAgentDto> getEndNoveltyAgentsPage(QueryAgent queryAgent);

    boolean createEnrollRecord(AmoyActivityEnrollParam activityEnrollParam);

    List<AmoyActivityEnroll> getRefunding();

    List<AmoyActivityEnroll> getPaying();

    boolean closeAllNotPay();

    boolean batchUpdateEnrollById(List<AmoyActivityEnroll> amoyActivityEnrolls);

    String successEnroll(PayResult payResult, Integer notifyOrigin);

    boolean adminCancelEnroll(String id, String remark);
}
