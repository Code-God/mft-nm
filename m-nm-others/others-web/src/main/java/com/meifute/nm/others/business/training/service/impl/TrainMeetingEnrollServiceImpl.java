package com.meifute.nm.others.business.training.service.impl;

import com.alibaba.excel.support.ExcelTypeEnum;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.meifute.nm.others.business.training.entity.ExportMeetingEnroll;
import com.meifute.nm.others.business.training.entity.TrainMeetingAffairs;
import com.meifute.nm.others.business.training.enums.*;
import com.meifute.nm.others.business.training.service.TrainMeetingAffairsService;
import com.meifute.nm.others.enums.*;
import com.meifute.nm.others.feignclient.AdminFeignClient;
import com.meifute.nm.others.feignclient.PayFeignClient;
import com.meifute.nm.others.feignclient.UserFeignClient;
import com.meifute.nm.others.utils.BeanCopyUtil;
import com.meifute.nm.others.utils.CurrentUserService;
import com.meifute.nm.others.business.training.entity.TrainMeetingEnroll;
import com.meifute.nm.others.business.training.mapper.TrainMeetingEnrollMapper;
import com.meifute.nm.others.business.training.service.TrainMeetingEnrollService;
import com.meifute.nm.otherscommon.enums.*;
import com.meifute.nm.otherscommon.enums.MallPayStatusEnum;
import com.meifute.nm.otherscommon.exception.BusinessException;
import com.meifute.nm.otherscommon.utils.IDUtils;
import com.meifute.nm.otherscommon.utils.redislock.RedisLock;
import com.meifute.nm.othersserver.domain.dto.*;
import com.meifute.nm.othersserver.domain.vo.QueryMeetingEnrollParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import xiong.exception.ExcelException;
import xiong.utils.ExcelUtil;

import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Classname TrainMeetingEnrollServiceImpl
 * @Description TODO
 * @Date 2020-07-07 10:30
 * @Created by MR. Xb.Wu
 */
@Slf4j
@Service
public class TrainMeetingEnrollServiceImpl extends ServiceImpl<TrainMeetingEnrollMapper, TrainMeetingEnroll> implements TrainMeetingEnrollService {

    @Autowired
    private UserFeignClient userFeignClient;
    @Autowired
    private AdminFeignClient adminFeignClient;
    @Autowired
    private PayFeignClient payFeignClient;
    @Autowired
    private TrainMeetingAffairsService trainMeetingAffairsService;
    @Autowired
    private TrainMeetingEnrollMapper trainMeetingEnrollMapper;
    @Autowired
    private CurrentUserService currentUserService;

    /**
     * 报名
     *
     * @param currentUser
     * @return
     */
    @Override
    @RedisLock(key = "id")
    @Transactional
    public TrainMeetingEnroll createMeetingEnroll(MallUser currentUser, String meetingId) {
        //查询是否有报过数据
        List<TrainMeetingEnroll> list = this.list(new QueryWrapper<TrainMeetingEnroll>()
                .eq("user_id", currentUser.getId())
                .eq("close_status", CloseStatusEnum.NORMAL.getCode())
                .eq("meeting_id", meetingId));
        if (!CollectionUtils.isEmpty(list)) {
            list.forEach(p -> {
                if (EnrollStatusEnum.ENROLL_SUCCESS.getCode().equals(p.getEnrollStatus())) {
                    throw new BusinessException(SysErrorEnums.ENROLLED);
                }
            });
        }
        TrainMeetingAffairs affairs = trainMeetingAffairsService.queryById(meetingId);

        currentUser = userFeignClient.getUserById(currentUser.getId());

        String enrollId = IDUtils.genId();
        TrainMeetingEnroll trainMeetingEnroll = new TrainMeetingEnroll();
        trainMeetingEnroll.setId(enrollId);
        trainMeetingEnroll.setCity(currentUser.getCity());
        trainMeetingEnroll.setUserId(currentUser.getId());
        trainMeetingEnroll.setMeetingId(meetingId);

        // 校验资格和规则
        checkQualifications(trainMeetingEnroll, affairs, currentUser);

        if (CollectionUtils.isEmpty(list)) {
            trainMeetingEnroll.setAdminCode(adminFeignClient.getAdminCodeByUserId(trainMeetingEnroll.getUserId()));
            trainMeetingEnroll.setLeaderUserId(currentUser.getReferrerId());
            trainMeetingEnroll.setCost(affairs.getCost());
            this.save(trainMeetingEnroll);
        } else {
            TrainMeetingEnroll enroll = list.get(0);
            enrollId = enroll.getId();
            if (affairs.getCost().compareTo(BigDecimal.ZERO) <= 0) {
                enroll.setEnrollStatus(EnrollStatusEnum.ENROLL_SUCCESS.getCode());
                enroll.setEnrollTime(LocalDateTime.now());
            }
            enroll.setCreateTime(LocalDateTime.now());
            enroll.setCost(affairs.getCost());
            this.updateById(enroll);
        }

        TrainMeetingEnroll enroll = this.getById(enrollId);
        if (affairs.getCost().compareTo(BigDecimal.ZERO) > 0) {
            //支付方式
            List<Integer> payTypes = payFeignClient.getPayTypeByUserId(CostTypeEnum.RMB.getCode(), currentUser.getId());
            enroll.setPayTypes(payTypes);
        }
        return enroll;
    }

    /**
     * 校验是否具有报名资格
     *
     * @param trainMeetingEnroll
     * @param affairs
     * @param currentUser
     */
    private void checkQualifications(TrainMeetingEnroll trainMeetingEnroll, TrainMeetingAffairs affairs, MallUser currentUser) {
        //资格校验
        if (!AgentRangeEnum.haveQualifications(currentUser.getRoleId(), affairs.getEnrollCondition())) {
            throw new BusinessException(SysErrorEnums.NOT_QUALIFICATIONS);
        }
        //活动未开始
        if (MeetingStatusEnum.READY_RELEASE.getCode().equals(affairs.getStatus())) {
            throw new BusinessException(SysErrorEnums.AC_NOT_START);
        }
        //活动已取消
        if (MeetingStatusEnum.AC_CLOSED.getCode().equals(affairs.getStatus())) {
            throw new BusinessException(SysErrorEnums.AC_CLOSED);
        }
        //活动已过期
        if (affairs.getMeetingEndTime().isBefore(LocalDateTime.now())) {
            throw new BusinessException(SysErrorEnums.AC_EXPIRE);
        }
        //活动报名截止
        if (affairs.getEnrollEndTime().isBefore(LocalDateTime.now())) {
            throw new BusinessException(SysErrorEnums.ENRROL_END);
        }
        //判断是否需要收费 需要支付
        if (affairs.getCost().compareTo(BigDecimal.ZERO) > 0) {
            trainMeetingEnroll.setEnrollStatus(EnrollStatusEnum.ENROLL_FAIL.getCode());
        } else { //直接报名成功
            trainMeetingEnroll.setEnrollStatus(EnrollStatusEnum.ENROLL_SUCCESS.getCode());
            trainMeetingEnroll.setEnrollTime(LocalDateTime.now());
        }
    }

    /**
     * 根据报名数据
     *
     * @param trainMeetingEnrollDto
     * @return
     */
    @Override
    @Transactional
    public boolean updateMeetingEnroll(TrainMeetingEnrollDto trainMeetingEnrollDto) {
        TrainMeetingEnroll trainMeetingEnroll = new TrainMeetingEnroll();
        BeanUtils.copyProperties(trainMeetingEnrollDto, trainMeetingEnroll);
        return this.updateById(trainMeetingEnroll);
    }

    /**
     * 查询报名数据列表
     *
     * @param param
     * @return
     */
    @Override
    public Page<TrainMeetingEnroll> queryMeetingEnrollPage(QueryMeetingEnrollParam param) {
        String userId = null;
        if (!StringUtils.isEmpty(param.getPhone())) {
            MallUser user = userFeignClient.getUserByPhone(param.getPhone());
            if (user != null) {
                userId = user.getId();
            }
        }
        Page<TrainMeetingEnroll> page = new Page<>(param.getPageCurrent(), param.getPageSize());
        Page<TrainMeetingEnroll> enrollPage = this.page(page, new QueryWrapper<TrainMeetingEnroll>()
                .eq("meeting_id", param.getMeetingId())
                .eq(!StringUtils.isEmpty(param.getPhone()), "user_id", userId)
                .eq(!StringUtils.isEmpty(param.getAdminCode()), "admin_code", param.getAdminCode())
                .eq(!StringUtils.isEmpty(param.getStatus()), "status", param.getStatus())
                .eq("enroll_status", EnrollStatusEnum.ENROLL_SUCCESS.getCode())
                .eq("deleted", DeletedCodeEnum.NOT_DELETE.getCode())
                .orderByDesc("enroll_time"));
        if (CollectionUtils.isEmpty(enrollPage.getRecords())) {
            return null;
        }
        enrollPage.getRecords().forEach(p -> {
            p.setUser(userFeignClient.getUserById(p.getUserId()));
            p.setLeaderUser(userFeignClient.getUserById(p.getLeaderUserId()));
        });
        return enrollPage;
    }

    /**
     * 根据id查询报名数据
     *
     * @param id
     * @return
     */
    @Override
    public TrainMeetingEnrollDto queryByEnrollId(String id) {
        TrainMeetingEnroll meetingEnroll = this.getById(id);
        if (meetingEnroll == null) {
            return null;
        }
        TrainMeetingEnrollDto dto = new TrainMeetingEnrollDto();
        BeanUtils.copyProperties(meetingEnroll, dto);
        return dto;
    }

    /**
     * 根据用户id和会务id查询报名信息
     *
     * @param userId
     * @param meetingId
     * @return
     */
    @Override
    public TrainMeetingEnrollDto queryByUserIdAndMeetingId(String userId, String meetingId) {
        List<TrainMeetingEnroll> enrolls = this.list(new QueryWrapper<TrainMeetingEnroll>()
                .eq("user_id", userId)
                .eq("enroll_status", EnrollStatusEnum.ENROLL_SUCCESS.getCode())
                .eq("close_status", CloseStatusEnum.NORMAL.getCode())
                .eq("meeting_id", meetingId));
        if (CollectionUtils.isEmpty(enrolls)) {
            return null;
        }
        TrainMeetingEnroll meetingEnroll = enrolls.get(0);
        TrainMeetingEnrollDto dto = new TrainMeetingEnrollDto();
        BeanUtils.copyProperties(meetingEnroll, dto);
        return dto;
    }

    /**
     * 查询我的报名列表
     *
     * @param param
     * @return
     */
    @Override
    public Page<MyMeetingEnrollDto> queryMyMeetingEnrollPage(QueryMeetingEnrollParam param) {
        Page<TrainMeetingEnroll> page = new Page<>(param.getPageCurrent(), param.getPageSize());
        MallUser currentUser = currentUserService.getCurrentUser();

        List<String> meetingIds = null;
        if (!StringUtils.isEmpty(param.getMeetingName())) {
            List<TrainMeetingAffairs> meetingAffairs = trainMeetingAffairsService.queryLikeByName(param.getMeetingName());
            if (!CollectionUtils.isEmpty(meetingAffairs)) {
                meetingIds = meetingAffairs.stream().map(TrainMeetingAffairs::getId).collect(Collectors.toList());
            }
        }
        Page<TrainMeetingEnroll> enrollPage = this.page(page, new QueryWrapper<TrainMeetingEnroll>()
                .eq("user_id", currentUser.getId())
                .in(!StringUtils.isEmpty(param.getMeetingName()), "meeting_id", meetingIds)
                .eq("enroll_status", EnrollStatusEnum.ENROLL_SUCCESS.getCode())
                .eq("close_status", CloseStatusEnum.NORMAL.getCode())
                .eq("deleted", DeletedCodeEnum.NOT_DELETE.getCode())
                .orderByDesc("enroll_time"));

        Page<MyMeetingEnrollDto> r = new Page<>();
        List<MyMeetingEnrollDto> l = new ArrayList<>();
        if (!CollectionUtils.isEmpty(enrollPage.getRecords())) {
            enrollPage.getRecords().forEach(p -> {
                TrainMeetingAffairs affairs = trainMeetingAffairsService.queryById(p.getMeetingId());
                MyMeetingEnrollDto dto = queryDetailMeetingDto(affairs, p);
                dto.setId(p.getId());
                dto.setSingUpStatus(p.getStatus());
                l.add(dto);
            });
        }
        BeanUtils.copyProperties(enrollPage, r, "records");
        r.setRecords(l);
        return r;
    }


    private MyMeetingEnrollDto queryDetailMeetingDto(TrainMeetingAffairs affairs, TrainMeetingEnroll enroll) {
        MyMeetingEnrollDto dto = new MyMeetingEnrollDto();
        BeanUtils.copyProperties(affairs, dto);
        dto.setMeetingId(affairs.getId());
        dto.setEnrollTime(enroll.getEnrollTime());

        if (MeetingStatusEnum.AC_CLOSED.getCode().equals(affairs.getStatus())) {
            dto.setStatus(MeetingEnrollStatusEnum.AC_CLOSE.getCode()); //活动已取消
            return dto;
        }

        if (SignUpStatusEnum.NOT_SIGN_UP.getCode().equals(enroll.getStatus())) {
            dto.setStatus(MeetingEnrollStatusEnum.ENROLLED.getCode()); //已报名
            return dto;
        }

        if (MeetingStatusEnum.AC_RELEASED.getCode().equals(affairs.getStatus())) {

            if (SignUpStatusEnum.SIGN_UP_ED.getCode().equals(enroll.getStatus())) {

                if (CostBackStatusEnum.COST_BACKED.getCode().equals(enroll.getCostBacked())) {
                    dto.setStatus(MeetingEnrollStatusEnum.ENROLLED_BACKED.getCode()); //已签到报名费已退还
                    return dto;
                }

                if (CostBackStatusEnum.COST_BACKING.getCode().equals(enroll.getCostBacked())) {
                    dto.setStatus(MeetingEnrollStatusEnum.ENROLLED_BACKING.getCode()); //已签到报名费退还中
                    return dto;
                }

                dto.setStatus(MeetingEnrollStatusEnum.SING_UP_ED.getCode()); //已签到
                return dto;
            }
        }

        if (affairs.getMeetingEndTime().isBefore(LocalDateTime.now())) {
            dto.setStatus(MeetingEnrollStatusEnum.AC_END.getCode()); //活动结束
            return dto;
        }

        if (MeetingStatusEnum.AC_END.getCode().equals(affairs.getStatus())) {
            dto.setStatus(MeetingEnrollStatusEnum.AC_END.getCode()); //活动已结束
            return dto;
        }
        return dto;
    }

    /**
     * 签到
     *
     * @param meetingId
     * @return
     */
    @Override
    @RedisLock(key = "meetingId")
    @Transactional
    public TrainMeetingEnrollDto signUp(String meetingId) {
        MallUser currentUser = currentUserService.getCurrentUser();
        TrainMeetingEnrollDto enrollDto = queryByUserIdAndMeetingId(currentUser.getId(), meetingId);
        if (enrollDto == null) {
            throw new BusinessException(SysErrorEnums.NOT_ENROLLED);
        }

        TrainMeetingEnroll trainMeetingEnroll = new TrainMeetingEnroll();

        if (!CostBackStatusEnum.COST_BACKED.getCode().equals(enrollDto.getCostBacked())) {
            TrainMeetingAffairs affairs = trainMeetingAffairsService.queryById(enrollDto.getMeetingId());
            if (TrainBackFlagEnum.CAN_BACK.getCode() == affairs.getCostBack()) {
                if (MallPayStatusEnum.PAY_STATUS_001.getCode().equals(enrollDto.getPayStatus())) {
                    trainMeetingEnroll.setCostBacked(CostBackStatusEnum.COST_BACKING.getCode()); //退钱中
                }
                if (enrollDto.getCost().compareTo(BigDecimal.ZERO) == 0) {
                    trainMeetingEnroll.setCostBacked(CostBackStatusEnum.COST_BACKED.getCode()); //已退钱
                }
            }
        }

        trainMeetingEnroll.setId(enrollDto.getId());
        trainMeetingEnroll.setStatus(SignUpStatusEnum.SIGN_UP_ED.getCode());

        this.updateById(trainMeetingEnroll);
        return this.queryByEnrollId(trainMeetingEnroll.getId());
    }

    /**
     * 查询未退款的报名数据
     *
     * @param meetingId
     * @return
     */
    @Override
    public List<TrainMeetingEnroll> queryNoBackList(String meetingId) {
        List<TrainMeetingEnroll> list = this.list(new QueryWrapper<TrainMeetingEnroll>()
                .eq("meeting_id", meetingId)
                .eq("enroll_status", EnrollStatusEnum.ENROLL_SUCCESS.getCode())
//                .eq("pay_status", MallPayStatusEnum.PAY_STATUS_001.getCode())
                .eq("cost_backed", CostBackStatusEnum.COST_NOT_BACKED.getCode())
                .eq("deleted", DeletedCodeEnum.NOT_DELETE.getCode()));
        if (CollectionUtils.isEmpty(list)) {
            return null;
        }
        return list;
    }

    /**
     * 根据id批量更新报名数据
     *
     * @param trainMeetingEnrolls
     * @return
     */
    @Override
    public boolean updateMeetingBatchById(List<TrainMeetingEnroll> trainMeetingEnrolls) {
        List<List<TrainMeetingEnroll>> subLists = getSubLists(trainMeetingEnrolls, OthersConst.SIZE);
        subLists.forEach(this::updateBatchById);
        return true;
    }

    /**
     * 按照size大小拆成多份
     *
     * @param allData
     * @param size
     * @return
     */
    public List<List<TrainMeetingEnroll>> getSubLists(List<TrainMeetingEnroll> allData, int size) {
        List<List<TrainMeetingEnroll>> result = new ArrayList<>();
        for (int begin = 0; begin < allData.size(); begin = begin + size) {
            int end = Math.min(begin + size, allData.size());
            result.add(allData.subList(begin, end));
        }
        return result;
    }

    /**
     * 导出报名列表
     *
     * @param param
     * @param response
     */
    @Override
    public void exportMeetingEnroll(QueryMeetingEnrollParam param, HttpServletResponse response) {
        String userId = null;
        if (!StringUtils.isEmpty(param.getPhone())) {
            MallUser user = userFeignClient.getUserByPhone(param.getPhone());
            if (user != null) {
                userId = user.getId();
            }
        }
        List<TrainMeetingEnroll> list = this.list(new QueryWrapper<TrainMeetingEnroll>()
                .eq("meeting_id", param.getMeetingId())
                .eq(!StringUtils.isEmpty(param.getPhone()), "user_id", userId)
                .eq(!StringUtils.isEmpty(param.getAdminCode()), "admin_code", param.getAdminCode())
                .eq(!StringUtils.isEmpty(param.getStatus()), "status", param.getStatus())
                .eq("enroll_status", EnrollStatusEnum.ENROLL_SUCCESS.getCode())
                .eq("deleted", DeletedCodeEnum.NOT_DELETE.getCode()));
        if (CollectionUtils.isEmpty(list)) {
            try {
                ExcelUtil.writeExcel(response, new ArrayList<>(), "会务报名数据", "会务报名数据", ExcelTypeEnum.XLSX, ExportMeetingEnroll.class);
            } catch (Exception e) {
                log.error("导出失败...");
            }
            return;
        }

        List<ExportMeetingEnroll> exports = BeanCopyUtil.copyListProperties(list, ExportMeetingEnroll::new);

        TrainMeetingAffairs affairs = trainMeetingAffairsService.queryById(param.getMeetingId());

        List<String> userIds = exports.stream().map(ExportMeetingEnroll::getUserId).collect(Collectors.toList());
        List<String> LeaderUserIds = exports.stream().map(ExportMeetingEnroll::getLeaderUserId).collect(Collectors.toList());

        List<MallUser> users = userFeignClient.queryUsersByIds(userIds);

        List<MallUser> leaderUsers = userFeignClient.queryUsersByIds(LeaderUserIds);

        List<AgentNumber> agentNumbers = userFeignClient.queryNextSuperAgents(userIds);

        log.info("--------------->:{}", agentNumbers);

        exports.forEach(p -> {

            List<MallUser> mallUsers = users.stream().filter(f -> f.getId().equals(p.getUserId())).collect(Collectors.toList());
            MallUser user = mallUsers.get(0);
            if (user != null) {
                p.setName(user.getName());
                p.setPhone(user.getPhone());
                p.setAgentLevel(AgentLevelEnum.explain(user.getRoleId()));
                String province = user.getProvince() == null ? "" : user.getProvince();
                String city = user.getCity() == null ? "": user.getCity();
                String cityArea = user.getCityArea() == null ? "": user.getCityArea();
                p.setCity(province + " " + city+ " " + cityArea);
            }

            List<MallUser> mallLeaderUsers = leaderUsers.stream().filter(f -> f.getId().equals(p.getLeaderUserId())).collect(Collectors.toList());
            MallUser leaderUser = mallLeaderUsers.get(0);
            if (leaderUser != null) {
                p.setLeaderName(leaderUser.getName());
                p.setLeaderPhone(leaderUser.getPhone());
                p.setLeaderAgentLevel(AgentLevelEnum.explain(leaderUser.getRoleId()));
            }

            p.setStatus(SignUpStatusEnum.explain(p.getStatus()));
            p.setCostBacked(CostBackStatusEnum.explain(p.getCostBacked()));


            List<AgentNumber> numbers = agentNumbers.stream().filter(f -> f.getUserId().trim().equals(p.getUserId().trim())).collect(Collectors.toList());

            AgentNumber number = null;
            if (!CollectionUtils.isEmpty(numbers)) {
                number = numbers.get(0);
            }
            if (number != null) {
                p.setNumber(number.getNumber() == null ? 0 : number.getNumber());
            }else {
                p.setNumber(0);
            }
        });

        try {
            ExcelUtil.writeExcel(response, exports, affairs.getMeetingName(), affairs.getMeetingName(), ExcelTypeEnum.XLSX, ExportMeetingEnroll.class);
        } catch (ExcelException e) {
            log.error("导出失败...");
        }

    }

    @Override
    public List<TrainMeetingEnroll> regularNoBackList() {
        TrainMeetingEnroll enroll = new TrainMeetingEnroll();
        enroll.setEnrollStatus(EnrollStatusEnum.ENROLL_SUCCESS.getCode());
        enroll.setPayStatus(MallPayStatusEnum.PAY_STATUS_001.getCode());
        enroll.setCostBacked(CostBackStatusEnum.COST_BACKING.getCode());
        enroll.setDeleted(DeletedCodeEnum.NOT_DELETE.getCode());

        List<TrainMeetingEnroll> list = trainMeetingEnrollMapper.regularNoBackList(enroll);
        if (CollectionUtils.isEmpty(list)) {
            return null;
        }
        return list;
    }

}
