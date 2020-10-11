package com.meifute.nm.others.business.training.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.meifute.nm.othersserver.domain.dto.MallUser;
import com.meifute.nm.othersserver.domain.dto.MyMeetingEnrollDto;
import com.meifute.nm.othersserver.domain.dto.TrainMeetingEnrollDto;
import com.meifute.nm.othersserver.domain.vo.QueryMeetingEnrollParam;
import com.meifute.nm.others.business.training.entity.TrainMeetingEnroll;

import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @Classname TrainMeetingEnrollService
 * @Description 报名数据接口
 * @Date 2020-07-07 10:28
 * @Created by MR. Xb.Wu
 */
public interface TrainMeetingEnrollService {

    /**
     * 报名
     * @param meetingId
     * @return
     */
    TrainMeetingEnroll createMeetingEnroll(MallUser currentUser, String meetingId);

    /**
     * 跟新报名信息
     * @param trainMeetingEnrollDto
     */
    boolean updateMeetingEnroll(TrainMeetingEnrollDto trainMeetingEnrollDto);

    /**
     * 查询报名列表
     * @param queryMeetingEnrollParam
     * @return
     */
    Page<TrainMeetingEnroll> queryMeetingEnrollPage(QueryMeetingEnrollParam queryMeetingEnrollParam);

    /**
     * 根据id查询报名信息
     * @param id
     * @return
     */
    TrainMeetingEnrollDto queryByEnrollId(String id);

    /**
     * 根据用户id和会务id查询报名记录
     * @param userId
     * @param meetingId
     * @return
     */
    TrainMeetingEnrollDto queryByUserIdAndMeetingId(String userId, String meetingId);

    /**
     * 查询我的报名记录列表
     * @param param
     * @return
     */
    Page<MyMeetingEnrollDto> queryMyMeetingEnrollPage(QueryMeetingEnrollParam param);

    /**
     * 签到
     * @param meetingId
     * @return
     */
    TrainMeetingEnrollDto signUp(String meetingId);

    /**
     * 查询未退款的报名数据
     * @param meetingId
     * @return
     */
    List<TrainMeetingEnroll> queryNoBackList(String meetingId);

    /**
     * 批量更新
     * @param trainMeetingEnrolls
     * @return
     */
    boolean updateMeetingBatchById(List<TrainMeetingEnroll> trainMeetingEnrolls);

    /**
     * 导出报名列表
     * @param queryMeetingEnrollParam
     * @param response
     */
    void exportMeetingEnroll(QueryMeetingEnrollParam queryMeetingEnrollParam, HttpServletResponse response);

    /**
     * 定期查询一直处于退款中的报名数据
     * @return
     */
    List<TrainMeetingEnroll> regularNoBackList();

}
