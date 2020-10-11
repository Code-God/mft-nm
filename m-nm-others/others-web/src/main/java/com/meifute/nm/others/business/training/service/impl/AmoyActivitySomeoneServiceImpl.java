package com.meifute.nm.others.business.training.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.meifute.nm.others.business.training.entity.AmoyActivitySomeone;
import com.meifute.nm.others.business.training.mapper.AmoyActivitySomeoneMapper;
import com.meifute.nm.others.business.training.service.AmoyActivitySomeoneService;
import com.meifute.nm.otherscommon.enums.DeletedCodeEnum;
import com.meifute.nm.othersserver.domain.vo.amoy.QueryAgent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * @Classname AmoyActivitySomeoneServiceImpl
 * @Description
 * @Date 2020-08-19 18:15
 * @Created by MR. Xb.Wu
 */
@Slf4j
@Service
public class AmoyActivitySomeoneServiceImpl extends ServiceImpl<AmoyActivitySomeoneMapper, AmoyActivitySomeone> implements AmoyActivitySomeoneService {


    @Override
    public boolean insertAmoyActivitySomeone(AmoyActivitySomeone amoyActivitySomeone) {
        return this.save(amoyActivitySomeone);
    }

    @Override
    public List<AmoyActivitySomeone> getByEnrollId(String enrollId) {
        return this.list(new QueryWrapper<AmoyActivitySomeone>()
                .eq("enroll_id", enrollId)
                .eq("deleted", DeletedCodeEnum.NOT_DELETE.getCode()));
    }

    @Override
    public Page<AmoyActivitySomeone> getSomeOnePage(QueryAgent queryAgent) {
        Page<AmoyActivitySomeone> page = new Page<>(queryAgent.getPageCurrent(),queryAgent.getPageSize());
        return this.page(page, new QueryWrapper<AmoyActivitySomeone>()
                .eq("enroll_id", queryAgent.getEnrollId())
                .eq("deleted", DeletedCodeEnum.NOT_DELETE.getCode())
                .eq(!StringUtils.isEmpty(queryAgent.getName()), "name", queryAgent.getName())
                .eq(!StringUtils.isEmpty(queryAgent.getPhone()), "phone", queryAgent.getPhone()));
    }

    @Override
    public List<AmoyActivitySomeone> getSomeOneByPhones(List<String> phones) {
        List<AmoyActivitySomeone> someones = this.list(new QueryWrapper<AmoyActivitySomeone>()
                .in("phone", phones)
                .eq("status", 0)
                .eq("deleted", DeletedCodeEnum.NOT_DELETE.getCode()));
        if (CollectionUtils.isEmpty(someones)) {
            return null;
        }
        return someones;
    }

    @Override
    public boolean updateSomeOneById(AmoyActivitySomeone amoyActivitySomeone) {
        return this.updateById(amoyActivitySomeone);
    }
}
