package com.meifute.nm.others.business.sourcematerial.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.meifute.nm.others.business.sourcematerial.entity.MomentOperation;
import com.meifute.nm.others.business.sourcematerial.mapper.MomentsOperationMapper;
import com.meifute.nm.others.business.sourcematerial.service.MomentsOperationService;
import com.meifute.nm.others.utils.CurrentUserService;
import com.meifute.nm.others.utils.MftUser;
import com.meifute.nm.otherscommon.utils.IDUtils;
import com.meifute.nm.othersserver.domain.vo.moments.GiveUpParam;
import com.meifute.nm.othersserver.domain.vo.moments.MomentOperationVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @Classname MomentsOperationServiceImpl
 * @Description
 * @Date 2020-08-13 12:22
 * @Created by MR. Xb.Wu
 */
@Slf4j
@Service
public class MomentsOperationServiceImpl extends ServiceImpl<MomentsOperationMapper, MomentOperation> implements MomentsOperationService {


    @Override
    public boolean recordMoment(MomentOperationVO momentOperationVO) {
        MftUser mftUser = CurrentUserService.getCurrentMftUser();
        Long userId = mftUser.getId();
        MomentOperation momentOperation = new MomentOperation();
        BeanUtils.copyProperties(momentOperationVO, momentOperation);
        momentOperation.setId(IDUtils.genLongId());
        momentOperation.setUserId(userId);
        momentOperation.setType("1");
        return this.save(momentOperation);
    }

    @Override
    public boolean giveUpMoment(GiveUpParam giveUpParam) {
        MftUser mftUser = CurrentUserService.getCurrentMftUser();
        Long userId = mftUser.getId();
        List<MomentOperation> operations = this.list(new QueryWrapper<MomentOperation>()
                .eq("user_id", userId)
                .eq("moment_id", giveUpParam.getMomentId())
                .eq("type", "0"));
        if (0 == giveUpParam.getFlag()) {
            if (!CollectionUtils.isEmpty(operations)) {
                return true;
            }
            MomentOperation momentOperation = new MomentOperation();
            momentOperation.setId(IDUtils.genLongId());
            momentOperation.setUserId(userId);
            momentOperation.setMomentId(giveUpParam.getMomentId());
            momentOperation.setType("0");
            this.save(momentOperation);
        }
        if (1 == giveUpParam.getFlag()) {
            if (CollectionUtils.isEmpty(operations)) {
                return true;
            }
            List<Long> ids = operations.stream().map(MomentOperation::getId).collect(Collectors.toList());
            this.removeByIds(ids);
        }
        return true;
    }

    @Override
    public Integer getRecordMomentNumber(Long momentId) {
        List<MomentOperation> operations = this.list(new QueryWrapper<MomentOperation>()
                .eq("moment_id", momentId)
                .eq("type", "1"));
        if (CollectionUtils.isEmpty(operations)) {
            return 0;
        }
        return operations.size();
    }

    @Override
    public Integer getGiveUpMomentNumber(Long momentId) {
        List<MomentOperation> operations = this.list(new QueryWrapper<MomentOperation>()
                .eq("moment_id", momentId)
                .eq("type", "0"));
        if (CollectionUtils.isEmpty(operations)) {
            return 0;
        }
        return operations.size();
    }

    @Override
    public boolean checkIsGiveUp(Long userId, Long momentId) {
        List<MomentOperation> operations = this.list(new QueryWrapper<MomentOperation>()
                .eq("moment_id", momentId)
                .eq("user_id", userId)
                .eq("type", "0"));
        return !CollectionUtils.isEmpty(operations);
    }
}
