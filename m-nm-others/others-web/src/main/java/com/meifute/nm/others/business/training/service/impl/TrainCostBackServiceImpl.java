package com.meifute.nm.others.business.training.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.meifute.nm.others.business.training.entity.TrainCostBack;
import com.meifute.nm.others.business.training.mapper.TrainCostBackMapper;
import com.meifute.nm.others.business.training.service.TrainCostBackService;
import com.meifute.nm.others.business.training.service.TrainMeetingEnrollService;
import com.meifute.nm.others.business.training.enums.CostBackStatusEnum;
import com.meifute.nm.others.feignclient.PayFeignClient;
import com.meifute.nm.otherscommon.enums.DeletedCodeEnum;
import com.meifute.nm.otherscommon.enums.MallPayStatusEnum;
import com.meifute.nm.otherscommon.enums.SysErrorEnums;
import com.meifute.nm.otherscommon.exception.BusinessException;
import com.meifute.nm.otherscommon.utils.IDUtils;
import com.meifute.nm.otherscommon.utils.redislock.RedisLock;
import com.meifute.nm.othersserver.domain.dto.TrainMeetingEnrollDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @Classname TrainCostBackServiceImpl
 * @Description
 * @Date 2020-07-08 18:09
 * @Created by MR. Xb.Wu
 */
@Slf4j
@Service
public class TrainCostBackServiceImpl extends ServiceImpl<TrainCostBackMapper, TrainCostBack> implements TrainCostBackService {

    @Autowired
    private PayFeignClient payFeignClient;
    @Autowired
    private TrainMeetingEnrollService trainMeetingEnrollService;


    /**
     * 退费
     * @param enrollId
     * @param remark
     */
    @Override
    @RedisLock(key = "enrollId")
    @Transactional
    public void backCostOne(String enrollId, String remark) {
        TrainMeetingEnrollDto enrollDto = trainMeetingEnrollService.queryByEnrollId(enrollId);
        //判断是否需要退款
        if (enrollDto == null) {
            throw new BusinessException(SysErrorEnums.NOT_ENROLL);
        }
        if (CostBackStatusEnum.COST_BACKED.getCode().equals(enrollDto.getCostBacked())) {
            throw new BusinessException(SysErrorEnums.REFUNDED);
        }
        if (MallPayStatusEnum.PAY_STATUS_000.getCode().equals(enrollDto.getPayStatus())) {
            throw new BusinessException(SysErrorEnums.NOT_PAY);
        }
        if (CostBackStatusEnum.COST_BACKING.getCode().equals(enrollDto.getCostBacked())) {
            throw new BusinessException(SysErrorEnums.REFUNDING);
        }

        // 退费
        boolean b = payFeignClient.signUpBackCost(enrollId);
        if (b) {
            enrollDto.setCostBacked(CostBackStatusEnum.COST_BACKED.getCode());
            enrollDto.setCostBackedTime(LocalDateTime.now());
            trainMeetingEnrollService.updateMeetingEnroll(enrollDto);

            TrainCostBack costBack = queryBackCostRecordByEnrollId(enrollId);
            if (costBack != null) {
                costBack.setDeleted(DeletedCodeEnum.DELETED.getCode());
                this.updateById(costBack);
            }
            //插入退费记录
            TrainCostBack trainCostBack = new TrainCostBack();
            trainCostBack.setId(IDUtils.genId());
            trainCostBack.setEnrollId(enrollId);
            trainCostBack.setCost(enrollDto.getCost());
            trainCostBack.setBackTime(LocalDateTime.now());
            trainCostBack.setRemark(remark);
            this.save(trainCostBack);
        }
    }

    /**
     * 查询退费记录
     * @param enrollId
     * @return
     */
    @Override
    public TrainCostBack queryBackCostRecordByEnrollId(String enrollId) {
        List<TrainCostBack> list = this.list(new QueryWrapper<TrainCostBack>()
                .eq("enroll_id", enrollId)
                .eq("deleted", DeletedCodeEnum.NOT_DELETE.getCode()));
        if (CollectionUtils.isEmpty(list)) {
            return null;
        }
        return list.get(0);
    }
}
