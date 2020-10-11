package com.meifute.nm.others.business.training.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.meifute.nm.others.business.training.entity.TrainMeetingEnroll;
import com.meifute.nm.othersserver.domain.dto.MyMeetingAffairsDto;
import com.meifute.nm.othersserver.domain.dto.TrainMeetingAffairsDto;
import com.meifute.nm.othersserver.domain.dto.TrainMeetingEnrollDto;
import com.meifute.nm.othersserver.domain.vo.QueryMeetingParam;
import com.meifute.nm.others.business.training.entity.TrainMeetingAffairs;

import java.util.List;

/**
 * @Classname TrainMeetingService
 * @Description TODO
 * @Date 2020-07-06 17:26
 * @Created by MR. Xb.Wu
 */
public interface TrainMeetingAffairsService {

    /**
     * 创建会务活动
     * @param trainMeetingAffairsDto
     */
    void createMeeting(TrainMeetingAffairsDto trainMeetingAffairsDto);

    /**
     * 更新会务活动
     * @param trainMeetingAffairsDto
     * @return
     */
    boolean updateMeeting(TrainMeetingAffairsDto trainMeetingAffairsDto);

    /**
     * 查询会务活动列表
     * @param queryMeetingParam
     * @return
     */
    Page<TrainMeetingAffairs> queryMeetingPage(QueryMeetingParam queryMeetingParam);

    /**
     * 发布
     * @param id
     * @param status
     */
    boolean releaseMeeting(String id, String status);

    /**
     *  取消发布
     * @param affairs
     * @param status
     * @param dto
     * @return
     */
    boolean closeMeeting(TrainMeetingAffairs affairs, String status, List<TrainMeetingEnroll> dto);

    /**
     * 根据id查询会务活动
     * @param id
     * @return
     */
    TrainMeetingAffairs queryById(String id);

    /**
     * 查询我的会务活动列表 （app使用）
     * @param queryMeetingParam
     * @return
     */
    Page<MyMeetingAffairsDto> queryMeetingPageToApp(QueryMeetingParam queryMeetingParam);

    /**
     * 根据会务名称模糊查询
     * @param meetingName
     * @return
     */
    List<TrainMeetingAffairs> queryLikeByName(String meetingName);

    /**
     * 查询会务详情
     * @param meetingId
     * @return
     */
    MyMeetingAffairsDto queryMeetingDetail(String meetingId);

    /**
     * 发送退款消息到MQ
     * @param ids
     */
    void sendCostEnrollDelayMQ(List<String> ids);

    /**
     * 重试发送退款消息到MQ
     * @param ids
     */
    void retrySendCostEnrollDelayMQ(List<String> ids);

    /**
     * 结束活动
     */
    void endMeeting();

}
