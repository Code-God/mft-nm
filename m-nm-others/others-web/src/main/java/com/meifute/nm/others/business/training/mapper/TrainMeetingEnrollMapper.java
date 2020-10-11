package com.meifute.nm.others.business.training.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.meifute.nm.others.business.training.entity.TrainMeetingEnroll;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Classname TrainMeetingEnrollMapper
 * @Description
 * @Date 2020-07-07 10:34
 * @Created by MR. Xb.Wu
 */
@Repository
public interface TrainMeetingEnrollMapper extends BaseMapper<TrainMeetingEnroll> {

    List<TrainMeetingEnroll> regularNoBackList(@Param("param") TrainMeetingEnroll trainMeetingEnroll);
}
