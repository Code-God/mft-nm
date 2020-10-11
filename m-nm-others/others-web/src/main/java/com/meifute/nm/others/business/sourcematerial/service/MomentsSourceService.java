package com.meifute.nm.others.business.sourcematerial.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.meifute.nm.others.business.sourcematerial.entity.MomentsSource;
import com.meifute.nm.othersserver.domain.vo.moments.*;

import java.util.List;

/**
 * @Classname MomentsSourceService
 * @Description
 * @Date 2020-08-13 11:00
 * @Created by MR. Xb.Wu
 */
public interface MomentsSourceService {

    boolean createSource(MomentsSourceVO momentsSourceVO);

    boolean editSource(MomentsSourceVO momentsSourceVO);

    boolean deleteSource(Long id);

    boolean releaseSource(Long id);

    Page<QueryMomentsSourceDto> querySource(QueryAdminMomentsSource queryAdminMomentsSource);

    Page<QueryMomentsSourceDto> querySource(QueryAppMomentsSource queryAppMomentsSource);

    Integer switchMoments(Integer onOff);

    TodaySourceNum getTodayReleasedSourceNum();

    List<MomentsSource> getMomentSourceByClassifyId(Long classifyId);

}
