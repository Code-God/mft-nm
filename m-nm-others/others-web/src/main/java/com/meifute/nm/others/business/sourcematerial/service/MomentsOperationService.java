package com.meifute.nm.others.business.sourcematerial.service;

import com.meifute.nm.othersserver.domain.vo.moments.GiveUpParam;
import com.meifute.nm.othersserver.domain.vo.moments.MomentOperationVO;

/**
 * @Classname MomentsOperationService
 * @Description
 * @Date 2020-08-13 11:07
 * @Created by MR. Xb.Wu
 */
public interface MomentsOperationService {

    boolean recordMoment(MomentOperationVO momentOperationVO);

    boolean giveUpMoment(GiveUpParam giveUpParam);

    Integer getRecordMomentNumber(Long momentId);

    Integer getGiveUpMomentNumber(Long momentId);

    boolean checkIsGiveUp(Long userId, Long momentId);
}
