package com.meifute.nm.others.business.training.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.meifute.nm.others.business.training.entity.AmoyActivityEnroll;
import com.meifute.nm.others.business.training.entity.AmoyActivitySignin;
import com.meifute.nm.others.business.training.entity.AmoyActivitySomeone;
import com.meifute.nm.others.business.training.mapper.AmoyActivityEnrollMapper;
import com.meifute.nm.others.business.training.service.AmoyActivityEnrollService;
import com.meifute.nm.others.business.training.service.AmoyActivitySigninService;
import com.meifute.nm.others.business.training.service.AmoyActivitySomeoneService;
import com.meifute.nm.others.feignclient.AgentFeignClient;
import com.meifute.nm.others.feignclient.PayFeignClient;
import com.meifute.nm.others.feignclient.UserFeignClient;
import com.meifute.nm.others.utils.BeanCopyUtil;
import com.meifute.nm.others.utils.CurrentUserService;
import com.meifute.nm.otherscommon.enums.AgentLevelEnum;
import com.meifute.nm.otherscommon.enums.CostTypeEnum;
import com.meifute.nm.otherscommon.enums.DeletedCodeEnum;
import com.meifute.nm.otherscommon.enums.SysErrorEnums;
import com.meifute.nm.otherscommon.exception.BusinessException;
import com.meifute.nm.otherscommon.utils.IDUtils;
import com.meifute.nm.otherscommon.utils.redislock.RedisLock;
import com.meifute.nm.othersserver.domain.dto.*;
import com.meifute.nm.othersserver.domain.param.BaseParam;
import com.meifute.nm.othersserver.domain.param.MobileUnifiedOrderParam;
import com.meifute.nm.othersserver.domain.vo.QueryNoveltyAgent;
import com.meifute.nm.othersserver.domain.vo.amoy.*;
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
import java.util.*;
import java.util.stream.Collectors;

/**
 * @Classname AmoyActivityEnrollServiceImpl
 * @Description
 * @Date 2020-08-19 17:44
 * @Created by MR. Xb.Wu
 */
@Slf4j
@Service
public class AmoyActivityEnrollServiceImpl extends ServiceImpl<AmoyActivityEnrollMapper, AmoyActivityEnroll> implements AmoyActivityEnrollService {


    @Value("${amoyPayNotifyUrl}")
    private String amoyPayNotifyUrl;
    @Autowired
    private UserFeignClient userFeignClient;
    @Autowired
    private AgentFeignClient agentFeignClient;
    @Autowired
    private PayFeignClient payFeignClient;
    @Autowired
    private CurrentUserService currentUserService;
    @Autowired
    private AmoyActivitySomeoneService amoyActivitySomeoneService;
    @Autowired
    private AmoyActivitySigninService amoyActivitySigninService;


    @Override
    public MyEnrollStatus getMyEnrollStatus() {
        MallUser user = currentUserService.getCurrentUser();
        List<AmoyActivityEnroll> list = this.list(new QueryWrapper<AmoyActivityEnroll>()
                .eq("user_id", user.getId())
                .in("enroll_status", Arrays.asList(0, 1, 2))
                .eq("deleted", DeletedCodeEnum.NOT_DELETE.getCode()));

        MyEnrollStatus myEnrollStatus = new MyEnrollStatus();

        myEnrollStatus.setStatus(0); //未报名
        if (CollectionUtils.isEmpty(list)) {
            return myEnrollStatus;
        }

        if (0 == list.get(0).getEnrollStatus() || 1 == list.get(0).getEnrollStatus()) {
            myEnrollStatus.setStatus(2); //待付款
            myEnrollStatus.setEnrollId(list.get(0).getId());
        }
        if (2 == list.get(0).getEnrollStatus()) {
            myEnrollStatus.setStatus(1); //已报名
            myEnrollStatus.setEnrollId(list.get(0).getId());
        }
        return myEnrollStatus;
    }

    @RedisLock(key = "phone")
    @Transactional
    @Override
    public EnrollVo enroll(AmoyActivityEnrollParam activityEnrollParam) {
        MallUser currentUser = userFeignClient.getUserByPhone(activityEnrollParam.getPhone());
        if (currentUser == null) {
            throw new BusinessException("10002", "用户不存在");
        }
        MallAgent agent = agentFeignClient.getAgentByUserId(currentUser.getId());
        if (agent == null || !AgentLevelEnum.SUPER.getCode().equals(agent.getAgentLevel())) {
            throw new BusinessException(SysErrorEnums.NOT_QUALIFICATIONS);
        }

        List<AmoyActivityEnroll> list = this.list(new QueryWrapper<AmoyActivityEnroll>()
                .eq("user_id", currentUser.getId())
                .in("enroll_status", Arrays.asList(0, 1, 2))
                .eq("deleted", DeletedCodeEnum.NOT_DELETE.getCode()));

        if (!CollectionUtils.isEmpty(list)) {
            throw new BusinessException("10002", "您已有报名记录或有未付款报名记录");
        }
        //查询8月新招总代
        LocalDateTime startTime = LocalDateTime.of(2020, 8, 1, 0, 0, 0);
        LocalDateTime endTime = LocalDateTime.now();

        if (LocalDateTime.of(2020, 9, 1, 0, 0, 0).isBefore(LocalDateTime.now())) {
            throw new BusinessException(SysErrorEnums.AC_ENROLL_EXPIRE);
        }

        AmoyActivityEnroll amoyActivityEnroll = new AmoyActivityEnroll();
        BeanUtils.copyProperties(activityEnrollParam, amoyActivityEnroll);
        amoyActivityEnroll.setId(IDUtils.genId());
        amoyActivityEnroll.setUserId(currentUser.getId());
        amoyActivityEnroll.setEnrollTime(LocalDateTime.now());
        amoyActivityEnroll.setAmount(BigDecimal.ZERO);


        QueryNoveltyAgentDto noveltyAgentDto = getNoveltyAgents(currentUser.getId(), null, startTime, endTime);

        //同行人个数
        int number = Optional.ofNullable(activityEnrollParam.getSomeoneElseNumber()).orElse(0);


        //查询新升总代，根据邀请同行人数和达标人数，计算支付金额，每多要一个人需要增加2个名额，一个名额1500元
        if (noveltyAgentDto == null) {
            amoyActivityEnroll.setAmount(BigDecimal.valueOf((number + 1) * 3000)); //3000
        } else {
            if (noveltyAgentDto.getNoveltyAgents() != null) {
                int noveltyNumber = noveltyAgentDto.getNoveltyAgents().size();
                int n = (number + 1) * 2 - noveltyNumber;
                if (n > 0) {
                    amoyActivityEnroll.setAmount(BigDecimal.valueOf(n * 1500));//1500
                }
            }else {
                amoyActivityEnroll.setAmount(BigDecimal.valueOf((number + 1) * 3000)); //3000
            }
        }

        //保存同行人信息
        if (!CollectionUtils.isEmpty(activityEnrollParam.getSomeoneVOS())) {

            checkSomeOneElse(activityEnrollParam.getSomeoneVOS());

            activityEnrollParam.getSomeoneVOS().forEach(someone -> {
                AmoyActivitySomeone amoyActivitySomeone = new AmoyActivitySomeone();
                BeanUtils.copyProperties(someone, amoyActivitySomeone);
                amoyActivitySomeone.setUserId(currentUser.getId());
                amoyActivitySomeone.setId(IDUtils.genId());
                amoyActivitySomeone.setEnrollId(amoyActivityEnroll.getId());
                amoyActivitySomeoneService.insertAmoyActivitySomeone(amoyActivitySomeone);
            });
        }

        EnrollVo enrollVo = new EnrollVo();
        if (amoyActivityEnroll.getAmount().compareTo(BigDecimal.ZERO) > 0) {
            //支付方式
            List<Integer> payTypes = payFeignClient.getPayTypeByUserId(CostTypeEnum.RMB.getCode(), currentUser.getId());
            MallUserAccountDto accountInfo = userFeignClient.getAccountInfo(currentUser.getId());
            enrollVo.setBalance(accountInfo.getAmount());
            enrollVo.setPayTypes(payTypes);
            enrollVo.setEnrollResult(false);
        } else {
            amoyActivityEnroll.setEnrollStatus(2);
            enrollVo.setEnrollResult(true);
        }
        //保存报名信息
        this.save(amoyActivityEnroll);

        enrollVo.setOrderId(amoyActivityEnroll.getId());
        enrollVo.setAmount(amoyActivityEnroll.getAmount());
        return enrollVo;
    }

    @Override
    public EnrollVo nowPay(String enrollId) {
        if (LocalDateTime.of(2020, 9, 7, 0, 0, 0).isBefore(LocalDateTime.now())) {
            throw new BusinessException(SysErrorEnums.AC_ENROLL_EXPIRE);
        }

        AmoyActivityEnroll enroll = this.getById(enrollId);
        //支付方式
        List<Integer> payTypes = payFeignClient.getPayTypeByUserId(CostTypeEnum.RMB.getCode(), enroll.getUserId());
        MallUserAccountDto accountInfo = userFeignClient.getAccountInfo(enroll.getUserId());
        EnrollVo enrollVo = new EnrollVo();
        enrollVo.setBalance(accountInfo.getAmount());
        enrollVo.setPayTypes(payTypes);
        enrollVo.setEnrollResult(false);
        enrollVo.setOrderId(enroll.getId());
        enrollVo.setAmount(enroll.getAmount());
        return enrollVo;
    }

    @Override
    public Page<AmoyActivityEnrollAppVO> getMyEnrollPage(BaseParam baseParam) {
        Page<AmoyActivityEnroll> page = new Page<>(baseParam.getPageCurrent(), baseParam.getPageSize());
        MallUser currentUser = currentUserService.getCurrentUser();

        Page<AmoyActivityEnroll> enrollPage = this.page(page, new QueryWrapper<AmoyActivityEnroll>()
                .eq("deleted", DeletedCodeEnum.NOT_DELETE.getCode())
                .eq("user_id", currentUser.getId())
                .orderByDesc("enroll_time"));

        Page<AmoyActivityEnrollAppVO> pageVO = new Page<>();

        if (CollectionUtils.isEmpty(enrollPage.getRecords())) {
            return pageVO;
        }
        List<AmoyActivityEnrollAppVO> vos = new ArrayList<>();
        enrollPage.getRecords().forEach(p -> {
            AmoyActivityEnrollAppVO vo = new AmoyActivityEnrollAppVO();
            BeanUtils.copyProperties(p, vo);
            List<AmoyActivitySomeone> someones = amoyActivitySomeoneService.getByEnrollId(p.getId());
            if (!CollectionUtils.isEmpty(someones)) {
                List<AmoyActivitySomeoneVO> someoneVOS = BeanCopyUtil.copyListProperties(someones, AmoyActivitySomeoneVO::new);
                vo.setSomeoneVOS(someoneVOS);
            }
            vo.setName(currentUser.getName());
            vo.setPhone(currentUser.getPhone());
            vos.add(vo);
        });

        BeanUtils.copyProperties(enrollPage, pageVO, "records");
        pageVO.setRecords(vos);
        return pageVO;
    }

    @Override
    public AmoyActivityEnroll getByEnrollId(String id) {
        return this.getById(id);
    }

    @Override
    @Transactional
    @RedisLock(key = "id")
    public boolean cancelEnroll(String id, String remark) {
        if (LocalDateTime.of(2020, 8, 28, 0, 0, 0).isBefore(LocalDateTime.now())) {
            throw new BusinessException("10003", "当前时间不支持取消");
        }

        AmoyActivityEnroll enroll = this.getById(id);
        if (enroll.getEnrollStatus() == 3) {
            throw new BusinessException("10003", "您已经取消过了哦");
        }

        if (1 == enroll.getEnrollType()) {
            throw new BusinessException("10003", "该报名不支持取消哦");
        }

        List<AmoyActivitySomeone> someones = amoyActivitySomeoneService.getByEnrollId(id);
        if (!CollectionUtils.isEmpty(someones)) {
            someones.forEach(p ->{
                p.setStatus(1);
                amoyActivitySomeoneService.updateSomeOneById(p);
            });
        }

        if (enroll.getAmount().compareTo(BigDecimal.ZERO) > 0 && enroll.getEnrollStatus() == 2) {
            //退费至余额
            PayModel payModel = PayModel.builder()
                    .amount(enroll.getAmount())
                    .orderId(enroll.getId())
                    .type(4)
                    .userId(enroll.getUserId())
                    .payType(1)
                    .describe("厦门活动报名退费")
                    .tradeType(1)
                    .build();
            boolean refund = payFeignClient.refund(payModel);
            if (refund) {
                enroll.setRefund(2);
            }
        }
        enroll.setRemark(remark);
        enroll.setEnrollStatus(3);
        return this.updateById(enroll);
    }

    @Override
    @Transactional
    @RedisLock(key = "orderId")
    public boolean balancePay(PayParam payParam) {
        AmoyActivityEnroll enroll = this.getById(payParam.getOrderId());
        if (enroll.getEnrollStatus() == 2) {
            throw new BusinessException("10003", "您已经支付过了哦");
        }
        PayModel payModel = PayModel.builder()
                .amount(enroll.getAmount())
                .orderId(enroll.getId())
                .type(4)
                .userId(enroll.getUserId())
                .payType(1)
                .describe("厦门活动报名费用")
                .tradeType(0)
                .build();
        boolean result = payFeignClient.balancePay(payModel);
        if (result) {
            enroll.setEnrollStatus(2);
            enroll.setPayType("1");
            enroll.setPayTime(LocalDateTime.now());
            this.updateById(enroll);
        }
        return true;
    }

    @Override
    public UnifiedWeChatDto wxUnifiedOrderNew(PayParam payParam) {
        MobileUnifiedOrderParam unifiedOrderParam = unifiedOrderParam(payParam);
        unifiedOrderParam.setPayType("2");
        return payFeignClient.wxUnifiedOrderNew(unifiedOrderParam);
    }

    @Override
    public String aliUnifiedOrderNew(PayParam payParam) {
        MobileUnifiedOrderParam unifiedOrderParam = unifiedOrderParam(payParam);
        unifiedOrderParam.setPayType("3");
        return payFeignClient.aliUnifiedOrderNew(unifiedOrderParam);
    }

    private MobileUnifiedOrderParam unifiedOrderParam(PayParam payParam) {
        MobileUnifiedOrderParam unifiedOrderParam = new MobileUnifiedOrderParam();
        AmoyActivityEnroll enroll = this.getById(payParam.getOrderId());
        unifiedOrderParam.setAmount(enroll.getAmount());
        unifiedOrderParam.setDescribe("厦门活动报名费用");
        unifiedOrderParam.setOrderId(enroll.getId());
        unifiedOrderParam.setOrderType(4);
        unifiedOrderParam.setUserId(enroll.getUserId());
        unifiedOrderParam.setNotifyUrl(amoyPayNotifyUrl);
        return unifiedOrderParam;
    }

    @Override
    public Page<QueryEnrollPage> queryEnrollPage(QueryEnrollVO queryEnrollVO) {
        Page<AmoyActivityEnroll> page = new Page<>(queryEnrollVO.getPageCurrent(), queryEnrollVO.getPageSize());
        Page<AmoyActivityEnroll> enrollPage = this.page(page, new QueryWrapper<AmoyActivityEnroll>()
                .eq("deleted", DeletedCodeEnum.NOT_DELETE.getCode())
                .eq(!StringUtils.isEmpty(queryEnrollVO.getEnrollStatus()),"enroll_status", queryEnrollVO.getEnrollStatus())
                .like(!StringUtils.isEmpty(queryEnrollVO.getName()), "name", queryEnrollVO.getName())
                .like(!StringUtils.isEmpty(queryEnrollVO.getPhone()), "phone", queryEnrollVO.getPhone())
                .ge(queryEnrollVO.getBeginTime() != null, "enroll_time", queryEnrollVO.getBeginTime())
                .le(queryEnrollVO.getEndTime() != null, "enroll_time", queryEnrollVO.getEndTime())
                .orderByDesc("create_time"));

        Page<QueryEnrollPage> result = new Page<>();
        if (CollectionUtils.isEmpty(enrollPage.getRecords())) {
            return result;
        }
        List<String> userIds = enrollPage.getRecords().stream().map(AmoyActivityEnroll::getUserId).collect(Collectors.toList());
        List<MallBranchOffice> branchOffices = agentFeignClient.queryCompanyByUserIds(userIds);

        LocalDateTime startTime = LocalDateTime.of(2020, 8, 1, 0, 0, 0);
        LocalDateTime acEndTime = LocalDateTime.of(2020, 9, 1, 0, 0, 0);

        List<QueryEnrollPage> list = new ArrayList<>();
        enrollPage.getRecords().forEach(p -> {
            QueryEnrollPage queryEnrollPage = new QueryEnrollPage();
            //查询公司信息
            BeanUtils.copyProperties(p, queryEnrollPage);
            List<MallBranchOffice> offices = branchOffices.stream().filter(f -> f.getUserId().equals(p.getUserId())).collect(Collectors.toList());
            if (offices.size() > 0) {
                queryEnrollPage.setCompanyName(offices.get(0).getCompanyName());
            }

            //报名时达标名额
            LocalDateTime endTime = p.getEnrollTime();
            QueryNoveltyAgentDto noveltyAgents = getNoveltyAgents(p.getUserId(), null, startTime, endTime);
            if (noveltyAgents != null && !CollectionUtils.isEmpty(noveltyAgents.getNoveltyAgents())) {
                queryEnrollPage.setEnrollNextAgentNumber(noveltyAgents.getNoveltyAgents().size());
            } else {
                queryEnrollPage.setEnrollNextAgentNumber(0);
            }

            //活动结束时达标数
            QueryNoveltyAgentDto endNoveltyAgents = getNoveltyAgents(p.getUserId(), null, startTime, acEndTime);
            if (endNoveltyAgents != null && !CollectionUtils.isEmpty(endNoveltyAgents.getNoveltyAgents())) {
                queryEnrollPage.setActivityEndAgentNumber(endNoveltyAgents.getNoveltyAgents().size());
            } else {
                queryEnrollPage.setActivityEndAgentNumber(0);
            }

            //查询同行人记录
            List<AmoyActivitySomeone> someones = amoyActivitySomeoneService.getByEnrollId(p.getId());
            if (!CollectionUtils.isEmpty(someones)) {
                queryEnrollPage.setSomeoneNumber(someones.size());
            } else {
                queryEnrollPage.setSomeoneNumber(0);
            }
            //查询签到记录
            List<AmoyActivitySignin> activitySignins = amoyActivitySigninService.querySignByEnrollId(p.getId());
            if (!CollectionUtils.isEmpty(activitySignins)) {
                List<AmoySignin> amoySignins = BeanCopyUtil.copyListProperties(activitySignins, AmoySignin::new);
                queryEnrollPage.setAmoySignins(amoySignins);
            }
            list.add(queryEnrollPage);
        });

        BeanUtils.copyProperties(page, result, "records");
        result.setRecords(list);
        return result;
    }

    private QueryNoveltyAgentDto getNoveltyAgents(String userId, String nextUserId, LocalDateTime startTime, LocalDateTime endTime) {
        QueryNoveltyAgent queryNoveltyAgents = new QueryNoveltyAgent();
        queryNoveltyAgents.setUserId(userId);
        queryNoveltyAgents.setNextUserId(nextUserId);
        queryNoveltyAgents.setStartTime(startTime);
        queryNoveltyAgents.setEndTime(endTime);
        return agentFeignClient.getNoveltyAgents(queryNoveltyAgents);
    }

    @Override
    public Page<NoveltyAgentDto> getNewNoveltyAgentsPage(QueryAgent queryAgent) {
        List<String> nextUserIds = null;
        if (!StringUtils.isEmpty(queryAgent.getName()) || !StringUtils.isEmpty(queryAgent.getPhone())) {
            List<MallUser> users = userFeignClient.getUserByInput(null, queryAgent.getName(), queryAgent.getPhone(), null, null);
            if (CollectionUtils.isEmpty(users)) {
                return null;
            }
            nextUserIds = users.stream().map(MallUser::getId).collect(Collectors.toList());
        }
        QueryNoveltyAgentInput queryNoveltyAgentInput = new QueryNoveltyAgentInput();
        if (nextUserIds != null) {
            queryNoveltyAgentInput.setNextUserId(nextUserIds);
        }
        LocalDateTime startTime = LocalDateTime.of(2020, 8, 1, 0, 0, 0);

        AmoyActivityEnroll enroll = this.getById(queryAgent.getEnrollId());
        queryNoveltyAgentInput.setStartTime(startTime);
        queryNoveltyAgentInput.setEndTime(enroll.getEnrollTime());
        queryNoveltyAgentInput.setUserId(enroll.getUserId());
        queryNoveltyAgentInput.setPageCurrent(queryAgent.getPageCurrent());
        queryNoveltyAgentInput.setPageSize(queryAgent.getPageSize());
        NoveltyAgentDtoPage noveltyAgentsPage = agentFeignClient.getNoveltyAgentsPage(queryNoveltyAgentInput);
        Page<NoveltyAgentDto> page = new Page<>();
        if (noveltyAgentsPage != null) {
            page.setRecords(noveltyAgentsPage.getRecords());
            page.setTotal(noveltyAgentsPage.getTotal());
        } else {
            page.setTotal(0);
        }
        return page;
    }

    @Override
    public Page<NoveltyAgentDto> getEndNoveltyAgentsPage(QueryAgent queryAgent) {
        List<String> nextUserIds = null;
        if (!StringUtils.isEmpty(queryAgent.getName()) || !StringUtils.isEmpty(queryAgent.getPhone())) {
            List<MallUser> users = userFeignClient.getUserByInput(null, queryAgent.getName(), queryAgent.getPhone(), null, null);
            if (CollectionUtils.isEmpty(users)) {
                return null;
            }
            nextUserIds = users.stream().map(MallUser::getId).collect(Collectors.toList());
        }
        QueryNoveltyAgentInput queryNoveltyAgentInput = new QueryNoveltyAgentInput();
        if (nextUserIds != null) {
            queryNoveltyAgentInput.setNextUserId(nextUserIds);
        }
        AmoyActivityEnroll enroll = this.getById(queryAgent.getEnrollId());

        LocalDateTime startTime = LocalDateTime.of(2020, 8, 1, 0, 0, 0);
        LocalDateTime endTime = LocalDateTime.of(2020, 9, 1, 0, 0, 0);

        queryNoveltyAgentInput.setStartTime(startTime);
        queryNoveltyAgentInput.setEndTime(endTime);
        queryNoveltyAgentInput.setUserId(enroll.getUserId());
        queryNoveltyAgentInput.setPageCurrent(queryAgent.getPageCurrent());
        queryNoveltyAgentInput.setPageSize(queryAgent.getPageSize());
        NoveltyAgentDtoPage noveltyAgentsPage = agentFeignClient.getNoveltyAgentsPage(queryNoveltyAgentInput);

        Page<NoveltyAgentDto> page = new Page<>();
        if (noveltyAgentsPage != null) {
            page.setRecords(noveltyAgentsPage.getRecords());
            page.setTotal(noveltyAgentsPage.getTotal());
        }else {
            page.setTotal(0);
        }
        return page;
    }

    @RedisLock(key = "phone")
    @Transactional
    @Override
    public boolean createEnrollRecord(AmoyActivityEnrollParam activityEnrollParam) {

        MallUser currentUser = userFeignClient.getUserByPhone(activityEnrollParam.getPhone());

        if (!AgentLevelEnum.SUPER.getCode().equals(currentUser.getRoleId())) {
            throw new BusinessException(SysErrorEnums.NOT_QUALIFICATIONS);
        }

        List<AmoyActivityEnroll> list = this.list(new QueryWrapper<AmoyActivityEnroll>()
                .eq("user_id", currentUser.getId())
                .in("enroll_status", Arrays.asList(0, 1, 2))
                .eq("deleted", DeletedCodeEnum.NOT_DELETE.getCode()));

        if (!CollectionUtils.isEmpty(list)) {
            throw new BusinessException("10002", "您已有报名记录或有未付款报名记录");
        }
        //查询8月新招总代
        LocalDateTime startTime = LocalDateTime.of(2020, 8, 1, 0, 0, 0);
        LocalDateTime endTime = LocalDateTime.now();

        if (LocalDateTime.of(2020, 9, 1, 0, 0, 0).isBefore(LocalDateTime.now())) {
            throw new BusinessException(SysErrorEnums.AC_ENROLL_EXPIRE);
        }

        AmoyActivityEnroll amoyActivityEnroll = new AmoyActivityEnroll();
        BeanUtils.copyProperties(activityEnrollParam, amoyActivityEnroll);
        amoyActivityEnroll.setId(IDUtils.genId());
        amoyActivityEnroll.setUserId(currentUser.getId());
        amoyActivityEnroll.setEnrollTime(LocalDateTime.now());
        amoyActivityEnroll.setAmount(BigDecimal.ZERO);


        QueryNoveltyAgentDto noveltyAgentDto = getNoveltyAgents(currentUser.getId(), null, startTime, endTime);

        int number = 0;
        List<AmoyActivitySomeoneVO> someoneVOS = activityEnrollParam.getSomeoneVOS();
        if (!CollectionUtils.isEmpty(someoneVOS)) {
            number = someoneVOS.size();
        }
        amoyActivityEnroll.setSomeoneElseNumber(number);
        //同行人个数
//        int number = Optional.ofNullable(activityEnrollParam.getSomeoneElseNumber()).orElse(0);


        //查询新升总代，根据邀请同行人数和达标人数，计算支付金额，每多要一个人需要增加2个名额，一个名额1500元
        if (noveltyAgentDto == null) {
            amoyActivityEnroll.setAmount(BigDecimal.valueOf((number + 1) * 3000)); //3000
        } else {
            if (noveltyAgentDto.getNoveltyAgents() != null) {
                int noveltyNumber = noveltyAgentDto.getNoveltyAgents().size();
                int n = (number + 1) * 2 - noveltyNumber;
                if (n > 0) {
                    amoyActivityEnroll.setAmount(BigDecimal.valueOf(n * 1500));//1500
                }
            }else {
                amoyActivityEnroll.setAmount(BigDecimal.valueOf((number + 1) * 3000)); //3000
            }
        }

        //保存同行人信息
        if (!CollectionUtils.isEmpty(activityEnrollParam.getSomeoneVOS())) {

            checkSomeOneElse(activityEnrollParam.getSomeoneVOS());

            activityEnrollParam.getSomeoneVOS().forEach(someone -> {
                AmoyActivitySomeone amoyActivitySomeone = new AmoyActivitySomeone();
                BeanUtils.copyProperties(someone, amoyActivitySomeone);
                amoyActivitySomeone.setUserId(currentUser.getId());
                amoyActivitySomeone.setId(IDUtils.genId());
                amoyActivitySomeone.setEnrollId(amoyActivityEnroll.getId());
                amoyActivitySomeoneService.insertAmoyActivitySomeone(amoyActivitySomeone);
            });
        }
        amoyActivityEnroll.setEnrollStatus(2);
        amoyActivityEnroll.setEnrollType(1);
        //保存报名信息
        return this.save(amoyActivityEnroll);
    }

    private void checkSomeOneElse(List<AmoyActivitySomeoneVO> someoneVOS) {
        List<String> phones = someoneVOS.stream().map(AmoyActivitySomeoneVO::getPhone).collect(Collectors.toList());
        List<AmoyActivitySomeone> someOneByPhones = amoyActivitySomeoneService.getSomeOneByPhones(phones);
        if (someOneByPhones != null) {
            StringBuilder sb = new StringBuilder();
            someOneByPhones.forEach(p ->{
                sb.append("[(").append(p.getName()).append(")").append(p.getPhone()).append("];");
            });
            AmoyActivityEnroll enroll = this.getById(someOneByPhones.get(0).getEnrollId());
            if (enroll != null) {
                throw new BusinessException("1003", "同行人"+sb+"已经和【"+enroll.getName()+"】一起同行");
            } else {
                throw new BusinessException("1003", "同行人"+sb+"已被其他人携带");
            }
        }
    }

    @Override
    public List<AmoyActivityEnroll> getRefunding() {
        return this.list(new QueryWrapper<AmoyActivityEnroll>()
                .eq("deleted", DeletedCodeEnum.NOT_DELETE.getCode())
                .eq("refund", 1));
    }


    @Override
    public List<AmoyActivityEnroll> getPaying() {
        return this.list(new QueryWrapper<AmoyActivityEnroll>()
                .eq("deleted", DeletedCodeEnum.NOT_DELETE.getCode())
                .eq("enroll_status", 1)
                .lt("update_time", LocalDateTime.now().minusMinutes(3))); //更新时间超过3分钟
    }

    @Override
    public boolean closeAllNotPay() {
        List<AmoyActivityEnroll> list = this.list(new QueryWrapper<AmoyActivityEnroll>()
                .eq("deleted", DeletedCodeEnum.NOT_DELETE.getCode())
                .eq("enroll_status", 0));
        if (!CollectionUtils.isEmpty(list)) {
            list.forEach(p -> {
                p.setEnrollStatus(3);
            });
            return this.updateBatchById(list);
        }
        return true;
    }

    @Override
    public boolean batchUpdateEnrollById(List<AmoyActivityEnroll> amoyActivityEnrolls) {
        return this.updateBatchById(amoyActivityEnrolls);
    }

    @RedisLock(key = "orderId")
    @Override
    public String successEnroll(PayResult payResult, Integer notifyOrigin) {
        AmoyActivityEnroll enroll = this.getById(payResult.getOrderId());
        if (notifyOrigin == 1) { //移动端回调
            if (enroll.getEnrollStatus() == 0) {
                enroll.setEnrollStatus(1); //支付中
                this.updateById(enroll);
            }
            return "SUCCESS";
        }

        //pay模块回调
        if (enroll.getEnrollStatus() == 2 || enroll.getEnrollStatus() == 3) {
            return "SUCCESS";
        }
        AmoyActivityEnroll activityEnroll = new AmoyActivityEnroll();
        activityEnroll.setId(payResult.getOrderId());
        activityEnroll.setEnrollStatus(2); //已完成
        activityEnroll.setPayType(String.valueOf(payResult.getPayType()));
        activityEnroll.setPayTime(payResult.getPayTime());
        activityEnroll.setPayTradeNo(payResult.getOutTradeNo());
        this.updateById(activityEnroll);
        return "SUCCESS";
    }

    @Override
    @Transactional
    @RedisLock(key = "id")
    public boolean adminCancelEnroll(String id, String remark) {
        if (LocalDateTime.of(2020, 8, 28, 0, 0, 0).isBefore(LocalDateTime.now())) {
            throw new BusinessException("10003", "当前时间不支持取消");
        }

        AmoyActivityEnroll enroll = this.getById(id);
        if (enroll.getEnrollStatus() == 3) {
            throw new BusinessException("10003", "您已经取消过了哦");
        }

        List<AmoyActivitySomeone> someones = amoyActivitySomeoneService.getByEnrollId(id);
        if (!CollectionUtils.isEmpty(someones)) {
            someones.forEach(p ->{
                p.setStatus(1);
                amoyActivitySomeoneService.updateSomeOneById(p);
            });
        }

        if (enroll.getAmount().compareTo(BigDecimal.ZERO) > 0 && enroll.getEnrollStatus() == 2) {
            if (enroll.getEnrollType() == 0) {
                //退费至余额
                PayModel payModel = PayModel.builder()
                        .amount(enroll.getAmount())
                        .orderId(enroll.getId())
                        .type(4)
                        .userId(enroll.getUserId())
                        .payType(1)
                        .describe("厦门活动报名退费")
                        .tradeType(1)
                        .build();
                boolean refund = payFeignClient.refund(payModel);
                if (refund) {
                    enroll.setRefund(2);
                }
            }
        }
        enroll.setRemark(remark);
        enroll.setEnrollStatus(3);
        return this.updateById(enroll);
    }


}
