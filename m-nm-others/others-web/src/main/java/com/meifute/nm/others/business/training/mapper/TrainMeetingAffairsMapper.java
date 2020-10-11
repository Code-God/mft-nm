package com.meifute.nm.others.business.training.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.meifute.nm.othersserver.domain.vo.QueryMeetingParam;
import com.meifute.nm.others.business.training.entity.TrainMeetingAffairs;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * @Classname TrainMeetingAffairsMapper
 * @Description TODO
 * @Date 2020-07-06 17:54
 * @Created by MR. Xb.Wu
 */
@Repository
public interface TrainMeetingAffairsMapper extends BaseMapper<TrainMeetingAffairs> {

    Page<TrainMeetingAffairs> queryMeetingPage(Page<TrainMeetingAffairs> page, @Param("param") QueryMeetingParam queryMeetingParam);
}
