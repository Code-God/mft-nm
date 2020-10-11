package com.meifute.nm.others.business.training.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.meifute.nm.others.business.training.entity.AmoyActivitySomeone;
import com.meifute.nm.othersserver.domain.vo.amoy.QueryAgent;

import java.util.List;

/**
 * @Classname AmoyActivitySomeoneService
 * @Description
 * @Date 2020-08-19 17:42
 * @Created by MR. Xb.Wu
 */
public interface AmoyActivitySomeoneService {

    boolean insertAmoyActivitySomeone(AmoyActivitySomeone amoyActivitySomeone);

    List<AmoyActivitySomeone> getByEnrollId(String enrollId);

    Page<AmoyActivitySomeone> getSomeOnePage(QueryAgent queryAgent);

    List<AmoyActivitySomeone> getSomeOneByPhones(List<String> phones);

    boolean updateSomeOneById(AmoyActivitySomeone amoyActivitySomeone);
}
