package com.meifute.nm.others.business.training.service.impl;

import com.aliyun.openservices.ons.api.Message;
import com.aliyun.openservices.ons.api.Producer;
import com.aliyun.openservices.ons.api.SendResult;
import com.aliyun.openservices.ons.api.exception.ONSClientException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.meifute.nm.others.business.training.entity.TrainMeetingEnroll;
import com.meifute.nm.others.business.training.enums.*;
import com.meifute.nm.others.business.training.service.TrainMeetingEnrollService;
import com.meifute.nm.others.config.rocketmq.RocketMQConfig;
import com.meifute.nm.others.enums.*;
import com.meifute.nm.others.utils.AliyunOss;
import com.meifute.nm.others.utils.CurrentUserService;
import com.meifute.nm.others.business.training.entity.TrainMeetingAffairs;
import com.meifute.nm.others.business.training.mapper.TrainMeetingAffairsMapper;
import com.meifute.nm.others.business.training.service.TrainMeetingAffairsService;
import com.meifute.nm.otherscommon.enums.DeletedCodeEnum;
import com.meifute.nm.otherscommon.enums.SysErrorEnums;
import com.meifute.nm.otherscommon.exception.BusinessException;
import com.meifute.nm.otherscommon.utils.redislock.RedisLock;
import com.meifute.nm.othersserver.domain.dto.MallUser;
import com.meifute.nm.othersserver.domain.dto.MyMeetingAffairsDto;
import com.meifute.nm.othersserver.domain.dto.TrainMeetingAffairsDto;
import com.meifute.nm.othersserver.domain.dto.TrainMeetingEnrollDto;
import com.meifute.nm.othersserver.domain.vo.QueryMeetingParam;
import com.meifute.nm.otherscommon.utils.IDUtils;
import com.meifute.nm.others.utils.QrCode;
import com.meifute.nm.othersserver.domain.vo.QueueParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @Classname TrainMeetingServiceImpl
 * @Description 会务活动
 * @Date 2020-07-06 17:53
 * @Created by MR. Xb.Wu
 */
@Slf4j
@Service
public class TrainMeetingAffairsServiceImpl extends ServiceImpl<TrainMeetingAffairsMapper, TrainMeetingAffairs> implements TrainMeetingAffairsService {

    @Value("${base-url}")
    private String baseUrl;
    @Autowired
    private AliyunOss oss;
    @Autowired
    private RocketMQConfig rocketMQConfig;
    @Autowired
    private CurrentUserService currentUserService;
    @Autowired
    private TrainMeetingAffairsMapper trainMeetingAffairsMapper;
    @Autowired
    private TrainMeetingEnrollService trainMeetingEnrollService;


    /**
     * 创建会务活动
     *
     * @param trainMeetingAffairsDto
     */
    @Override
    @Transactional
    public void createMeeting(TrainMeetingAffairsDto trainMeetingAffairsDto) {
        TrainMeetingAffairs trainMeetingAffairs = new TrainMeetingAffairs();
        BeanUtils.copyProperties(trainMeetingAffairsDto, trainMeetingAffairs);
        trainMeetingAffairs.setId(IDUtils.genId());
        QrCode qrCode = new QrCode();
        String key = baseUrl + "/nm-others//bgw/training/enroll/sign/up?meetingId=";
        String codeQrCode = qrCode.getQrCode(key + trainMeetingAffairs.getId(), oss);
        trainMeetingAffairs.setQrCode(codeQrCode);
        this.save(trainMeetingAffairs);
    }

    /**
     * 更新会务活动
     *
     * @param trainMeetingAffairsDto
     * @return
     */
    @Override
    @Transactional
    public boolean updateMeeting(TrainMeetingAffairsDto trainMeetingAffairsDto) {
        TrainMeetingAffairs trainMeetingAffairs = new TrainMeetingAffairs();
        BeanUtils.copyProperties(trainMeetingAffairsDto, trainMeetingAffairs);
        return this.updateById(trainMeetingAffairs);
    }

    /**
     * 查询会务列表
     *
     * @param param
     * @return
     */
    @Override
    public Page<TrainMeetingAffairs> queryMeetingPage(QueryMeetingParam param) {
        Page<TrainMeetingAffairs> page = new Page<>(param.getPageCurrent(), param.getPageSize());
        return trainMeetingAffairsMapper.queryMeetingPage(page, param);
    }

    /**
     * 发布
     *
     * @param id
     * @param status
     */
    @Override
    @Transactional
    @RedisLock(key = "id")
    public boolean releaseMeeting(String id, String status) {
        TrainMeetingAffairs affairs = this.getById(id);
        if (status.equals(affairs.getStatus())) {
            return true;
        }
        //活动结束的或者报名时间已结束的不能发布
        if (LocalDateTime.now().isAfter(affairs.getMeetingEndTime())) {
            throw new BusinessException(SysErrorEnums.AC_EXPIRE);
        }
        if (LocalDateTime.now().isAfter(affairs.getEnrollEndTime())) {
            throw new BusinessException(SysErrorEnums.AC_ENROLL_EXPIRE);
        }
        TrainMeetingAffairs trainMeetingAffairs = new TrainMeetingAffairs();
        trainMeetingAffairs.setReleaseTime(LocalDateTime.now());
        trainMeetingAffairs.setId(id);
        trainMeetingAffairs.setStatus(status);
        return this.updateById(trainMeetingAffairs);
    }

    /**
     * 取消发布
     *
     * @param affairs
     * @param status
     */
    @Override
    @Transactional
    @RedisLock(key = "id")
    public boolean closeMeeting(TrainMeetingAffairs affairs, String status, List<TrainMeetingEnroll> dto) {
        if (status.equals(affairs.getStatus())) {
            return false;
        }
        TrainMeetingAffairs trainMeetingAffairs = new TrainMeetingAffairs();

        if (dto != null && TrainBackFlagEnum.CAN_BACK.getCode() == affairs.getCostBack()) {
            dto.forEach(p -> {
                p.setCostBacked(CostBackStatusEnum.COST_BACKING.getCode()); // 退钱中
                if (p.getCost().compareTo(BigDecimal.ZERO) == 0) {
                    p.setCostBacked(CostBackStatusEnum.COST_BACKED.getCode()); // 已退钱
                }
                p.setCloseStatus(CloseStatusEnum.CLOSED.getCode());
            });
            trainMeetingEnrollService.updateMeetingBatchById(dto);
        }
        trainMeetingAffairs.setId(affairs.getId());
        trainMeetingAffairs.setStatus(status);
        return this.updateById(trainMeetingAffairs);
    }

    /**
     * 根据id查询会务活动
     *
     * @param id
     * @return
     */
    @Override
    public TrainMeetingAffairs queryById(String id) {
        return this.getById(id);
    }

    /**
     * 查询我的会务列表
     *
     * @param param
     * @return
     */
    @Override
    public Page<MyMeetingAffairsDto> queryMeetingPageToApp(QueryMeetingParam param) {
        Page<TrainMeetingAffairs> page = new Page<>(param.getPageCurrent(), param.getPageSize());
        MallUser currentUser = currentUserService.getCurrentUser();
        List<String> status = Arrays.asList(
                MeetingStatusEnum.AC_RELEASED.getCode(),
                MeetingStatusEnum.AC_END.getCode());
        Page<TrainMeetingAffairs> affairsPage = this.page(page, new QueryWrapper<TrainMeetingAffairs>()
                .eq("deleted", DeletedCodeEnum.NOT_DELETE.getCode())
                .like(!StringUtils.isEmpty(param.getMeetingName()), "meeting_name", param.getMeetingName())
                .in("status", status)
                .orderByDesc("release_time"));
        if (CollectionUtils.isEmpty(affairsPage.getRecords())) {
            return null;
        }
        Page<MyMeetingAffairsDto> r = new Page<>();
        List<MyMeetingAffairsDto> list = new ArrayList<>();

        affairsPage.getRecords().forEach(p -> {
            list.add(queryDetailMeetingDto(p, currentUser));
        });
        BeanUtils.copyProperties(affairsPage, r, "records");
        r.setRecords(list);
        return r;
    }

    /**
     * 根据会务名称模糊查询会务活动
     *
     * @param meetingName
     * @return
     */
    @Override
    public List<TrainMeetingAffairs> queryLikeByName(String meetingName) {
        return this.list(new QueryWrapper<TrainMeetingAffairs>()
                .like("meeting_name", meetingName)
                .eq("deleted", DeletedCodeEnum.NOT_DELETE.getCode()));
    }

    @Override
    public MyMeetingAffairsDto queryMeetingDetail(String meetingId) {
        TrainMeetingAffairs affairs = this.queryById(meetingId);
        MallUser currentUser = currentUserService.getCurrentUser();
        return queryDetailMeetingDto(affairs, currentUser);
    }

    @Override
    public void sendCostEnrollDelayMQ(List<String> ids) {
        if (ids != null) {
            ids.forEach(id -> {
                sendCostEnrollDelayMQ(QueueParam.builder().message(id).times(OthersConst.DELAY_TIME).build());
            });
        }
    }

    @Override
    public void retrySendCostEnrollDelayMQ(List<String> ids) {
        if (ids != null) {
            ids.forEach(id -> {
                sendCostEnrollDelayMQ(QueueParam.builder().message(id).times(OthersConst.DELAY_TIME).build());
            });
        }
    }

    @Override
    public void endMeeting() {
        List<TrainMeetingAffairs> list = this.list(new QueryWrapper<TrainMeetingAffairs>()
                .eq("deleted", DeletedCodeEnum.NOT_DELETE)
                .eq("status", MeetingStatusEnum.AC_RELEASED.getCode()));
        if (CollectionUtils.isEmpty(list)) {
            return;
        }
        list.forEach(p ->{
            if (LocalDateTime.now().isAfter(p.getMeetingEndTime())) {
                p.setStatus(MeetingStatusEnum.AC_END.getCode());
                this.updateById(p);
            }
        });
    }


    private MyMeetingAffairsDto queryDetailMeetingDto(TrainMeetingAffairs affairs, MallUser currentUser) {
        MyMeetingAffairsDto dto = new MyMeetingAffairsDto();
        BeanUtils.copyProperties(affairs, dto);
        TrainMeetingEnrollDto enrollDto = trainMeetingEnrollService.queryByUserIdAndMeetingId(currentUser.getId(), affairs.getId());

        if (MeetingStatusEnum.AC_CLOSED.getCode().equals(affairs.getStatus())) {
            dto.setStatus(MeetingEnrollStatusEnum.AC_CLOSE.getCode()); //活动已取消
        }

        if (enrollDto != null ) {
            if (EnrollStatusEnum.ENROLL_SUCCESS.getCode().equals(enrollDto.getEnrollStatus()) &&
                    SignUpStatusEnum.NOT_SIGN_UP.getCode().equals(enrollDto.getStatus())) {
                dto.setStatus(MeetingEnrollStatusEnum.ENROLLED.getCode()); //已报名
                return dto;
            }
        }

        if (affairs.getMeetingEndTime().isBefore(LocalDateTime.now())) {
            dto.setStatus(MeetingEnrollStatusEnum.AC_END.getCode()); //活动结束
            return dto;
        }

        if (MeetingStatusEnum.AC_RELEASED.getCode().equals(affairs.getStatus())) {
            if (enrollDto == null) {
                if (affairs.getEnrollEndTime().isBefore(LocalDateTime.now())) {
                    dto.setStatus(MeetingEnrollStatusEnum.ENROLL_END.getCode()); //报名时间已截止
                    return dto;
                }

                dto.setStatus(MeetingEnrollStatusEnum.NOT_ENROLL_STATUS.getCode()); //未报名
                return dto;
            }

            if (SignUpStatusEnum.SIGN_UP_ED.getCode().equals(enrollDto.getStatus())) {

                if (CostBackStatusEnum.COST_BACKED.getCode().equals(enrollDto.getCostBacked())) {
                    dto.setStatus(MeetingEnrollStatusEnum.ENROLLED_BACKED.getCode()); //已签到报名费已退还
                    return dto;
                }

                if (CostBackStatusEnum.COST_BACKING.getCode().equals(enrollDto.getCostBacked())) {
                    dto.setStatus(MeetingEnrollStatusEnum.ENROLLED_BACKING.getCode()); //已签到报名费退还中
                    return dto;
                }

                dto.setStatus(MeetingEnrollStatusEnum.SING_UP_ED.getCode()); //已签到
                return dto;
            }
        }

        if (MeetingStatusEnum.AC_END.getCode().equals(affairs.getStatus())) {
            dto.setStatus(MeetingEnrollStatusEnum.AC_END.getCode()); //活动已结束
            return dto;
        }

        return dto;
    }

    private void sendCostEnrollDelayMQ(QueueParam queueParam) {
        log.info("开始发送活动退费数据:{}", queueParam.getMessage());
        Producer producer = rocketMQConfig.trainingEnrollProducer();
        Message msg = new Message(rocketMQConfig.getTrainingEnrollTopic(), RocketMQConfig.TRAINING_ENROLL, queueParam.getMessage().getBytes());
        try {
            long delayTime = System.currentTimeMillis() + queueParam.getTimes();
            msg.setStartDeliverTime(delayTime);
            SendResult sendResult = producer.send(msg);
            if (sendResult != null) {
                log.info("消息发送成功：" + sendResult.toString());
            }
        } catch (ONSClientException e) {
            log.info("消息发送失败：", e);
        }
    }



}
