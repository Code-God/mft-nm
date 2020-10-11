package com.meifute.nm.others.business.training.service;

import com.meifute.nm.others.business.training.entity.TrainCostBack;

/**
 * @Classname TrainCostBackService
 * @Description
 * @Date 2020-07-08 18:09
 * @Created by MR. Xb.Wu
 */
public interface TrainCostBackService {

    /**
     * 退费
     * @param enrollId
     * @param remark
     */
    void backCostOne(String enrollId, String remark);

    /**
     * 查询退费记录
     * @param enrollId
     * @return
     */
    TrainCostBack queryBackCostRecordByEnrollId(String enrollId);
}
