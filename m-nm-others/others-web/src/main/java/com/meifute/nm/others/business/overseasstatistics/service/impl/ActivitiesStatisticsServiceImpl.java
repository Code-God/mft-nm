package com.meifute.nm.others.business.overseasstatistics.service.impl;

import com.alibaba.excel.support.ExcelTypeEnum;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.meifute.nm.others.business.overseasstatistics.entity.ActivitiesQuota;
import com.meifute.nm.others.business.overseasstatistics.entity.ActivitiesStaticsModel;
import com.meifute.nm.others.business.overseasstatistics.entity.ActivitiesStaticsResultModel;
import com.meifute.nm.others.business.overseasstatistics.entity.ActivitiesStatistics;
import com.meifute.nm.others.business.overseasstatistics.enums.ActivitiesQuotaEnum;
import com.meifute.nm.others.business.overseasstatistics.enums.ActivitiesStatisticsEnum;
import com.meifute.nm.others.business.overseasstatistics.mapper.ActivitiesStatisticsMapper;
import com.meifute.nm.others.business.overseasstatistics.service.ActivitiesQuotaService;
import com.meifute.nm.others.business.overseasstatistics.service.ActivitiesStatisticsService;
import com.meifute.nm.others.feignclient.AgentFeignClient;
import com.meifute.nm.others.feignclient.UserFeignClient;
import com.meifute.nm.others.utils.RedisUtil;
import com.meifute.nm.otherscommon.enums.DeletedCodeEnum;
import com.meifute.nm.otherscommon.exception.BusinessException;
import com.meifute.nm.otherscommon.utils.IDUtils;
import com.meifute.nm.otherscommon.utils.JsonUtils;
import com.meifute.nm.othersserver.domain.dto.ActivitiesStatisticsDto;
import com.meifute.nm.othersserver.domain.dto.MallAgent;
import com.meifute.nm.othersserver.domain.dto.MallBackAgent;
import com.meifute.nm.othersserver.domain.dto.MallUser;
import com.meifute.nm.othersserver.domain.vo.ActivitiesQuotaVO;
import com.meifute.nm.othersserver.domain.vo.ActivitiesStatisticsVO;
import com.meifute.nm.othersserver.domain.vo.QueryActivitiesStatisticsParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import xiong.exception.ExcelException;
import xiong.utils.ExcelUtil;

import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * @Classname ActivitiesStatisticsServiceImpl
 * @Description
 * @Date 2020-08-06 11:24
 * @Created by MR. Xb.Wu
 */
@Slf4j
@Service
public class ActivitiesStatisticsServiceImpl extends ServiceImpl<ActivitiesStatisticsMapper, ActivitiesStatistics> implements ActivitiesStatisticsService {

    @Autowired
    private AgentFeignClient agentFeignClient;

    @Autowired
    private UserFeignClient userFeignClient;

    @Autowired
    private ActivitiesQuotaService activitiesQuotaService;

    @Override
    public Page<ActivitiesStatisticsDto> queryActivitiesStatistics(QueryActivitiesStatisticsParam queryParam) {
        QueryWrapper<ActivitiesStatistics> eq = new QueryWrapper<ActivitiesStatistics>()
                .like(!StringUtils.isEmpty(queryParam.getActivityName()), "activity_name", queryParam.getActivityName())
                .eq(!StringUtils.isEmpty(queryParam.getAgentPhone()), "agent_phone", queryParam.getAgentPhone())
                .like(!StringUtils.isEmpty(queryParam.getAgentName()), "agent_name", queryParam.getAgentName())
                .gt(queryParam.getActivityStartTime() != null, "activity_time", queryParam.getActivityStartTime())
                .lt(queryParam.getActivityEndTime() != null, "activity_end_time", queryParam.getActivityEndTime())
                .eq("deleted", DeletedCodeEnum.NOT_DELETE.getCode());
        if (queryParam.getIsPaymentAmount() != null) {
            if (ActivitiesStatisticsEnum.PAYMENT.getCode() == queryParam.getIsPaymentAmount()) {
                eq.gt("payment_amount", BigDecimal.ZERO);
            }
            if (ActivitiesStatisticsEnum.NO_PAYMENT.getCode() == queryParam.getIsPaymentAmount()) {
                eq.eq("payment_amount", BigDecimal.ZERO);
            }
        }
        eq.orderByDesc("activity_time");
        Page<ActivitiesStatistics> page = new Page<>(queryParam.getPageCurrent(), queryParam.getPageSize());
        Page<ActivitiesStatistics> statistics = this.page(page, eq);
        if (CollectionUtils.isEmpty(statistics.getRecords())) {
            return null;
        }

        List<String> userIds = statistics.getRecords().stream().map(ActivitiesStatistics::getUserId).collect(Collectors.toList());
        List<MallAgent> companyAndUpgradeDate = agentFeignClient.getCompanyAndUpgradeDate(userIds);
        List<ActivitiesStatisticsDto> dtoList = new ArrayList<>();
        statistics.getRecords().forEach(p -> {
            ActivitiesStatisticsDto dto = new ActivitiesStatisticsDto();
            BeanUtils.copyProperties(p, dto);
            dto.setId(String.valueOf(p.getId()));
            //查询商贸公司
            List<MallAgent> agents = companyAndUpgradeDate.stream().filter(c -> c.getUserId().equals(p.getUserId())).collect(Collectors.toList());
            if (!CollectionUtils.isEmpty(agents)) {
                String companyName = agents.get(0).getMyCompanyName();
                dto.setCompanyName(companyName);
            }
            List<ActivitiesQuota> activitiesQuotas = activitiesQuotaService.queryQuotaByStatisticsId(p.getId());
            if (!CollectionUtils.isEmpty(activitiesQuotas)) {
                //参会人员个数
                dto.setParticipants(getCount(activitiesQuotas, Collections.singletonList(ActivitiesQuotaEnum.PARTICIPANTS.getCode())));
                //达标人数
                dto.setQualifiedPerson(getCount(activitiesQuotas, Arrays.asList(ActivitiesQuotaEnum.QUALIFIED_PERSON.getCode(), ActivitiesQuotaEnum.RETROGRESSION.getCode())));
                //达标名额退代数
                dto.setRetrogression(getCount(activitiesQuotas, Collections.singletonList(ActivitiesQuotaEnum.RETROGRESSION.getCode())));
                //其他名额抵用数
                dto.setDeductedPerson(getCount(activitiesQuotas, Collections.singletonList(ActivitiesQuotaEnum.DEDUCTED_PERSON.getCode())));
            }
            dtoList.add(dto);
        });
        Page<ActivitiesStatisticsDto> dtoPage = new Page<>();
        BeanUtils.copyProperties(statistics, dtoPage, "records");
        dtoPage.setRecords(dtoList);
        return dtoPage;
    }

    private int getCount(List<ActivitiesQuota> activitiesQuotas, List<Integer> types) {
        List<ActivitiesQuota> quotas = activitiesQuotas.stream().filter(q -> types.contains(q.getType())).collect(Collectors.toList());
        //去重
        List<ActivitiesQuota> newQuotas = quotas.stream().collect(Collectors.collectingAndThen(Collectors.toCollection(
                () -> new TreeSet<>(Comparator.comparing(ActivitiesQuota::getAgentPhone))), ArrayList::new));
        return Integer.parseInt(String.valueOf(newQuotas.size()));
    }

    @Override
    @Transactional
    public boolean insertActivitiesStatistics(ActivitiesStatisticsVO statisticsVo) {
        if (StringUtils.isEmpty(statisticsVo.getAgentPhone())) {
            throw new BusinessException("10000", "代理手机号不能为空");
        }
        //插入主表
        ActivitiesStatistics activitiesStatistics = new ActivitiesStatistics();
        BeanUtils.copyProperties(statisticsVo, activitiesStatistics);
        activitiesStatistics.setId(IDUtils.genLongId());
        activitiesStatistics.setUserId(userFeignClient.getUserByPhone(statisticsVo.getAgentPhone()).getId());
        this.save(activitiesStatistics);

        //插入名额表
        List<ActivitiesQuota> activitiesQuotas = new ArrayList<>();
        //参会人
        List<ActivitiesQuotaVO> participants = statisticsVo.getParticipants();
        if (!CollectionUtils.isEmpty(participants)) {
            participants.forEach(p -> {
                encapsulation(activitiesQuotas, activitiesStatistics.getId(), ActivitiesQuotaEnum.PARTICIPANTS.getCode(), p, null);
            });
        }

        //达标总人数
        List<ActivitiesQuotaVO> qualifiedPersons = statisticsVo.getQualifiedPerson();
        if (!CollectionUtils.isEmpty(qualifiedPersons)) {
            //达标人数
            List<ActivitiesQuotaVO> qualifiedPerson = qualifiedPersons.stream().filter(p -> p.getType() == ActivitiesQuotaEnum.QUALIFIED_PERSON.getCode()).collect(Collectors.toList());
            //达标
            qualifiedPerson.forEach(p -> {
                encapsulation(activitiesQuotas, activitiesStatistics.getId(), ActivitiesQuotaEnum.QUALIFIED_PERSON.getCode(), p, null);
            });

            //达标退代人数
            List<ActivitiesQuotaVO> retrogression = qualifiedPersons.stream().filter(p -> p.getType() == ActivitiesQuotaEnum.RETROGRESSION.getCode()).collect(Collectors.toList());
            if (!CollectionUtils.isEmpty(retrogression)) {
                List<String> phones = retrogression.stream().map(ActivitiesQuotaVO::getAgentPhone).collect(Collectors.toList());
                //查询退代时间
                List<MallBackAgent> backAgentByPhones = agentFeignClient.getBackAgentByPhones(phones);
                //达标退代
                retrogression.forEach(p -> {
                    LocalDateTime associationTime = getBackAgentTime(backAgentByPhones, p.getAgentPhone());
                    encapsulation(activitiesQuotas, activitiesStatistics.getId(), ActivitiesQuotaEnum.RETROGRESSION.getCode(), p, associationTime);

                });
            }
        }

        List<ActivitiesQuotaVO> deductedPerson = statisticsVo.getDeductedPerson();
        if (!CollectionUtils.isEmpty(deductedPerson)) {
            List<String> deductedPhones = deductedPerson.stream().map(ActivitiesQuotaVO::getAgentPhone).collect(Collectors.toList());
            List<MallUser> deUsers = userFeignClient.queryUsersByPhones(deductedPhones);
            String msg = checkIsSuperAgent(deductedPhones, deUsers);
            if (!StringUtils.isEmpty(msg)) {
                throw new BusinessException("10000", "抵扣名额人手机号" + msg + "非总代理");
            }

            //查询是否已被抵用
            deductedPhones.forEach(phone -> {
                List<ActivitiesQuota> quotas = activitiesQuotaService.queryDeductedPersonByPhone(phone);
                if (quotas != null) {
                    List<ActivitiesQuota> collect = quotas.stream().filter(q -> !q.getStatisticsId().equals(activitiesStatistics.getId())).collect(Collectors.toList());
                    if (collect.size() != 0) {
                        throw new BusinessException("10000", "抵用名额" + "[" + collect.get(0).getAgentName() + "]" + phone + "已在其他活动中被抵扣");
                    }
                }
            });

            //查询升总代时间
            List<MallUser> deductedMallUsers = userFeignClient.queryUsersByPhones(deductedPhones);
            //抵扣名额
            deductedPerson.forEach(p -> {
                LocalDateTime associationTime = getUpgradeTime(deductedMallUsers, p.getAgentPhone());
                encapsulation(activitiesQuotas, activitiesStatistics.getId(), ActivitiesQuotaEnum.DEDUCTED_PERSON.getCode(), p, associationTime);
            });
        }
        activitiesQuotaService.batchInsertActivitiesQuota(activitiesQuotas);
        return true;
    }

    private void encapsulation(List<ActivitiesQuota> activitiesQuotas, Long statisticsId, int type, ActivitiesQuotaVO quotaVO, LocalDateTime associationTime) {
        ActivitiesQuota activitiesQuota = new ActivitiesQuota();
        activitiesQuota.setId(IDUtils.genLongId());
        activitiesQuota.setStatisticsId(statisticsId);
        activitiesQuota.setType(type);
        activitiesQuota.setAgentName(quotaVO.getAgentName());
        activitiesQuota.setAgentPhone(quotaVO.getAgentPhone());
        if (associationTime != null) {
            activitiesQuota.setAssociationTime(associationTime);
        }
        activitiesQuotas.add(activitiesQuota);
    }

    private LocalDateTime getBackAgentTime(List<MallBackAgent> backAgentByPhones, String agentPhone) {
        LocalDateTime associationTime = null;
        if (!CollectionUtils.isEmpty(backAgentByPhones)) {
            List<MallBackAgent> backAgents = backAgentByPhones.stream().filter(b -> agentPhone.equals(b.getPhone())).collect(Collectors.toList());
            if (!CollectionUtils.isEmpty(backAgents)) {
                associationTime = LocalDateTime.ofInstant(backAgents.get(0).getCreateDate().toInstant(), ZoneId.systemDefault());
            }
        }
        return associationTime;
    }

    private LocalDateTime getUpgradeTime(List<MallUser> deductedMallUsers, String agentPhone) {
        LocalDateTime associationTime = null; //升总代时间
        if (!CollectionUtils.isEmpty(deductedMallUsers)) {
            List<MallUser> users = deductedMallUsers.stream().filter(d -> agentPhone.equals(d.getPhone())).collect(Collectors.toList());
            if (!CollectionUtils.isEmpty(users)) {
                associationTime = LocalDateTime.ofInstant(users.get(0).getUpgradeDate().toInstant(), ZoneId.systemDefault());
            }
        }
        return associationTime;
    }

    @Override
    @Transactional
    public boolean editActivitiesStatistics(ActivitiesStatisticsVO statisticsVo) {
        if (StringUtils.isEmpty(statisticsVo.getAgentPhone())) {
            throw new BusinessException("10000", "代理手机号不能为空");
        }
        //更新主表
        ActivitiesStatistics activitiesStatistics = new ActivitiesStatistics();
        BeanUtils.copyProperties(statisticsVo, activitiesStatistics);
        activitiesStatistics.setId(Long.parseLong(statisticsVo.getId()));
        activitiesStatistics.setUserId(userFeignClient.getUserByPhone(statisticsVo.getAgentPhone()).getId());
        this.updateById(activitiesStatistics);

        //删除所有关联的名额
        activitiesQuotaService.deleteQuotaByStatisticsId(Long.parseLong(statisticsVo.getId()));

        List<ActivitiesQuota> insertActivitiesQuotas = new ArrayList<>();
        //参会人
        List<ActivitiesQuotaVO> participants = statisticsVo.getParticipants();
        if (!CollectionUtils.isEmpty(participants)) {
            participants.forEach(p -> {
                encapsulation(activitiesStatistics.getId(), ActivitiesQuotaEnum.PARTICIPANTS.getCode(), p, insertActivitiesQuotas, null);
            });
        }

        //达标总人数
        List<ActivitiesQuotaVO> qualifiedPersons = statisticsVo.getQualifiedPerson();
        if (!CollectionUtils.isEmpty(qualifiedPersons)) {
            //达标人数
            List<ActivitiesQuotaVO> qualifiedPerson = qualifiedPersons.stream().filter(p -> p.getType() == ActivitiesQuotaEnum.QUALIFIED_PERSON.getCode()).collect(Collectors.toList());
            //达标
            qualifiedPerson.forEach(p -> {
                encapsulation(activitiesStatistics.getId(), ActivitiesQuotaEnum.QUALIFIED_PERSON.getCode(), p, insertActivitiesQuotas, null);
            });

            //达标退代人数
            List<ActivitiesQuotaVO> retrogression = qualifiedPersons.stream().filter(p -> p.getType() == ActivitiesQuotaEnum.RETROGRESSION.getCode()).collect(Collectors.toList());
            if (!CollectionUtils.isEmpty(retrogression)) {
                List<String> phones = retrogression.stream().map(ActivitiesQuotaVO::getAgentPhone).collect(Collectors.toList());
                //查询退代时间
                List<MallBackAgent> backAgentByPhones = agentFeignClient.getBackAgentByPhones(phones);
                //达标退代
                retrogression.forEach(p -> {
                    LocalDateTime associationTime = getBackAgentTime(backAgentByPhones, p.getAgentPhone());
                    encapsulation(activitiesStatistics.getId(), ActivitiesQuotaEnum.RETROGRESSION.getCode(), p, insertActivitiesQuotas, associationTime);
                });
            }
        }

        //抵扣名额
        List<ActivitiesQuotaVO> deductedPerson = statisticsVo.getDeductedPerson();
        if (!CollectionUtils.isEmpty(deductedPerson)) {
            List<String> deductedPhones = deductedPerson.stream().map(ActivitiesQuotaVO::getAgentPhone).collect(Collectors.toList());
            List<MallUser> deUsers = userFeignClient.queryUsersByPhones(deductedPhones);
            String msg = checkIsSuperAgent(deductedPhones, deUsers);
            if (!StringUtils.isEmpty(msg)) {
                throw new BusinessException("10000", "抵扣名额人手机号" + msg + "非总代理");
            }

            //查询是否已被抵用
            deductedPhones.forEach(phone -> {
                List<ActivitiesQuota> quotas = activitiesQuotaService.queryDeductedPersonByPhone(phone);
                if (quotas != null) {
                    List<ActivitiesQuota> collect = quotas.stream().filter(q -> !q.getStatisticsId().equals(activitiesStatistics.getId())).collect(Collectors.toList());
                    if (collect.size() != 0) {
                        throw new BusinessException("10000", "抵用名额" + "[" + collect.get(0).getAgentName() + "]" + phone + "已在其他活动中被抵扣");
                    }
                }
            });
            //查询升总代时间
            List<MallUser> deductedMallUsers = userFeignClient.queryUsersByPhones(deductedPhones);
            deductedPerson.forEach(p -> {
                LocalDateTime associationTime = getUpgradeTime(deductedMallUsers, p.getAgentPhone());
                encapsulation(activitiesStatistics.getId(), ActivitiesQuotaEnum.DEDUCTED_PERSON.getCode(), p, insertActivitiesQuotas, associationTime);
            });
        }
        activitiesQuotaService.batchInsertActivitiesQuota(insertActivitiesQuotas);
        return true;
    }

    private void encapsulation(Long statisticsId, int type, ActivitiesQuotaVO quotaVO, List<ActivitiesQuota> insertActivitiesQuotas, LocalDateTime associationTime) {
        ActivitiesQuota activitiesQuota = new ActivitiesQuota();
        activitiesQuota.setId(IDUtils.genLongId());
        activitiesQuota.setStatisticsId(statisticsId);
        activitiesQuota.setType(type);
        activitiesQuota.setAgentName(quotaVO.getAgentName());
        activitiesQuota.setAgentPhone(quotaVO.getAgentPhone());
        if (associationTime != null) {
            activitiesQuota.setAssociationTime(associationTime);
        }
        insertActivitiesQuotas.add(activitiesQuota);
    }

    @Override
    @Transactional
    public boolean deleteActivitiesStatistics(Long id) {
        //删除主表
        ActivitiesStatistics statistics = new ActivitiesStatistics();
        statistics.setId(id);
        statistics.setDeleted(DeletedCodeEnum.DELETED.getCode());
        this.updateById(statistics);
        //删除
        activitiesQuotaService.deleteQuotaByStatisticsId(id);
        return true;
    }

    @Override
    public ActivitiesStatisticsVO queryEditActivitiesStatistics(Long id) {
        ActivitiesStatistics statistics = this.getById(id);
        ActivitiesStatisticsVO vo = new ActivitiesStatisticsVO();
        BeanUtils.copyProperties(statistics, vo);
        vo.setId(String.valueOf(statistics.getId()));

        List<ActivitiesQuota> activitiesQuotas = activitiesQuotaService.queryQuotaByStatisticsId(id);
        if (!CollectionUtils.isEmpty(activitiesQuotas)) {
            //参会人
            List<ActivitiesQuota> participants = activitiesQuotas.stream().filter(p -> p.getType() == ActivitiesQuotaEnum.PARTICIPANTS.getCode()).collect(Collectors.toList());
            if (!CollectionUtils.isEmpty(participants)) {
                vo.setParticipants(transferQuota(participants));
            }
            //达标人
            List<Integer> list = Arrays.asList(ActivitiesQuotaEnum.QUALIFIED_PERSON.getCode(), ActivitiesQuotaEnum.RETROGRESSION.getCode());
            List<ActivitiesQuota> qualifiedPerson = activitiesQuotas.stream().filter(p -> list.contains(p.getType())).collect(Collectors.toList());
            if (!CollectionUtils.isEmpty(qualifiedPerson)) {
                //如果达标和退代有相同的手机号，则只显示退代的手机号
                //找出相同的手机号
                List<String> phones = qualifiedPerson.stream().
                        collect(Collectors.groupingBy(ActivitiesQuota::getAgentPhone, Collectors.counting()))
                        .entrySet().stream()
                        .filter(entry -> entry.getValue() > 1)
                        .map(Map.Entry::getKey)
                        .collect(Collectors.toList());

                List<ActivitiesQuota> qualifiedPersons = new ArrayList<>();
                if (phones.size() > 0) {
                    List<ActivitiesQuota> validPerson = qualifiedPerson.stream().filter(
                            p -> p.getType().equals(ActivitiesQuotaEnum.QUALIFIED_PERSON.getCode()) && !phones.contains(p.getAgentPhone()))
                            .collect(Collectors.toList());
                    List<ActivitiesQuota> backPerson = qualifiedPerson.stream().filter(
                            p -> p.getType().equals(ActivitiesQuotaEnum.RETROGRESSION.getCode()))
                            .collect(Collectors.toList());

                    qualifiedPersons.addAll(validPerson);
                    qualifiedPersons.addAll(backPerson);
                } else {
                    qualifiedPersons.addAll(qualifiedPerson);
                }
                vo.setQualifiedPerson(transferQuota(qualifiedPersons));
            }
            //抵扣名额
            List<ActivitiesQuota> deductedPerson = activitiesQuotas.stream().filter(p -> p.getType() == ActivitiesQuotaEnum.DEDUCTED_PERSON.getCode()).collect(Collectors.toList());
            if (!CollectionUtils.isEmpty(deductedPerson)) {
                vo.setDeductedPerson(transferQuota(deductedPerson));
            }
        }
        return vo;
    }

    private List<ActivitiesQuotaVO> transferQuota(List<ActivitiesQuota> activitiesQuotas) {
        List<ActivitiesQuotaVO> activitiesQuotaVOS = new ArrayList<>();
        for (ActivitiesQuota quota : activitiesQuotas) {
            ActivitiesQuotaVO quotaVO = new ActivitiesQuotaVO();
            BeanUtils.copyProperties(quota, quotaVO);
            quotaVO.setId(String.valueOf(quota.getId()));
            activitiesQuotaVOS.add(quotaVO);
        }
        return activitiesQuotaVOS;
    }

    @Override
    public ActivitiesStatistics queryById(Long id) {
        return this.getById(id);
    }

    @Override
    @Transactional
    public String importActivities(MultipartFile file) {
        String key = "ActivitiesStatistics" + UUID.randomUUID().toString();
        List<ActivitiesStaticsResultModel> result = new ArrayList<>();
        List<ActivitiesStaticsModel> staticsModels = null;
        try {
            staticsModels = ExcelUtil.readExcel(file, ActivitiesStaticsModel.class);
        } catch (ExcelException e) {
            log.info("error 导入失败: {}", e.getMessage());
        }
        if (CollectionUtils.isEmpty(staticsModels)) {
            ActivitiesStaticsResultModel r = new ActivitiesStaticsResultModel();
            r.setResult("请导入正确格式的Excel");
            result.add(r);
        } else {
            importActivities(staticsModels, result);
        }
        if (result.size() == 0) {
            ActivitiesStaticsResultModel r = new ActivitiesStaticsResultModel();
            r.setResult("全部导入成功");
            result.add(r);
        }
        RedisUtil.set(key, JsonUtils.objectToJson(result), 5 * 60);
        return key;
    }

    private void importActivities(List<ActivitiesStaticsModel> staticsModels, List<ActivitiesStaticsResultModel> result) {
        staticsModels.forEach(p -> {
            //达标人
            List<MallUser> qualifiedPersons = queryUsersByPhoneStr(p.getQualifiedPerson());
            //校验
            String check = check(p, qualifiedPersons);
            if (!StringUtils.isEmpty(check)) {
                ActivitiesStaticsResultModel r = new ActivitiesStaticsResultModel();
                BeanUtils.copyProperties(p, r);
                r.setResult(check);
                result.add(r);
                return;
            }

            MallUser user = userFeignClient.getUserByPhone(p.getAgentPhone().trim());

            //插入主表
            ActivitiesStatistics statistics = new ActivitiesStatistics();
            statistics.setId(IDUtils.genLongId());
            statistics.setAgentName(user.getName());
            statistics.setAgentPhone(user.getPhone());
            statistics.setUserId(user.getId());
            statistics.setActivityName(p.getActivityName());
            statistics.setActivityTime(dateTransfer(p.getActivityDates()));
            statistics.setActivityEndTime(dateTransfer(p.getActivityEndDates()));
            statistics.setPaymentAmount(StringUtils.isEmpty(p.getPaymentAmount()) ? BigDecimal.ZERO : new BigDecimal(p.getPaymentAmount()));
            statistics.setDeductedAmount(StringUtils.isEmpty(p.getDeductedAmount()) ? BigDecimal.ZERO : new BigDecimal(p.getDeductedAmount()));
            statistics.setRemark(p.getRemark());
            this.save(statistics);

            //插入名额表
            List<ActivitiesQuota> activitiesQuotas = new ArrayList<>();

            //参会人
            List<MallUser> participants = queryUsersByPhoneStr(p.getParticipants());
            String[] participant = p.getParticipants().split(",");
            Arrays.asList(participant).forEach(s -> {
                encapsulation(activitiesQuotas, statistics.getId(), ActivitiesQuotaEnum.PARTICIPANTS.getCode(), s, participants, null);
            });

            //达标人
            String[] qualified = p.getQualifiedPerson().split(",");
            Arrays.asList(qualified).forEach(s -> {
                encapsulation(activitiesQuotas, statistics.getId(), ActivitiesQuotaEnum.QUALIFIED_PERSON.getCode(), s, qualifiedPersons, null);
            });

            //退代人
            List<MallUser> retrogressions = queryUsersByPhoneStr(p.getRetrogression());
            if (!CollectionUtils.isEmpty(retrogressions)) {
                //查询退代时间
                List<MallBackAgent> backAgentByPhones = getBackAgentByPhoneStr(p.getRetrogression());
                String[] retrogression = p.getRetrogression().split(",");
                Arrays.asList(retrogression).forEach(s -> {
                    LocalDateTime backAgentTime = getBackAgentTime(backAgentByPhones, s);
                    encapsulation(activitiesQuotas, statistics.getId(), ActivitiesQuotaEnum.RETROGRESSION.getCode(), s, retrogressions, backAgentTime);
                });
            }

            //抵扣人
            List<MallUser> deductedPersons = queryUsersByPhoneStr(p.getDeductedPerson());
            if (!CollectionUtils.isEmpty(deductedPersons)) {
                String[] deductedPerson = p.getDeductedPerson().split(",");
                Arrays.asList(deductedPerson).forEach(s -> {
                    LocalDateTime upgradeTime = getUpgradeTime(deductedPersons, s);
                    encapsulation(activitiesQuotas, statistics.getId(), ActivitiesQuotaEnum.DEDUCTED_PERSON.getCode(), s, deductedPersons, upgradeTime);
                });
            }
            activitiesQuotaService.batchInsertActivitiesQuota(activitiesQuotas);
        });
    }


    private void encapsulation(List<ActivitiesQuota> activitiesQuotas, Long statisticsId, int type, String phone, List<MallUser> users, LocalDateTime associationTime) {
        ActivitiesQuota activitiesQuota = new ActivitiesQuota();
        activitiesQuota.setId(IDUtils.genLongId());
        activitiesQuota.setStatisticsId(statisticsId);
        activitiesQuota.setType(type);
        if (!CollectionUtils.isEmpty(users)) {
            List<MallUser> userList = users.stream().filter(u -> phone.trim().equals(u.getPhone())).collect(Collectors.toList());
            if (!CollectionUtils.isEmpty(userList)) {
                activitiesQuota.setAgentName(userList.get(0).getName());
            }
        }
        activitiesQuota.setAgentPhone(phone);
        if (associationTime != null) {
            activitiesQuota.setAssociationTime(associationTime);
        }
        activitiesQuotas.add(activitiesQuota);
    }

    private LocalDateTime dateTransfer(Date time) {
        LocalDateTime localDateTime;
        try {
            localDateTime = LocalDateTime.ofInstant(time.toInstant(), ZoneId.systemDefault());
        } catch (Exception e) {
            return null;
        }
        return localDateTime;
    }

    //导入活动校验
    private String check(ActivitiesStaticsModel p, List<MallUser> users) {
        StringBuilder sb = new StringBuilder();

        if (StringUtils.isEmpty(p.getAgentPhone())) {
            sb.append("(错误)代理手机号为空;");
        }
        if (StringUtils.isEmpty(p.getActivityName())) {
            sb.append("(错误)活动名称为空;");
        }
        if (StringUtils.isEmpty(p.getActivityDates())) {
            sb.append("(错误)活动开始时间为空;");
        }
        if (StringUtils.isEmpty(p.getActivityEndDates())) {
            sb.append("(错误)活动结束时间为空;");
        }
        if (StringUtils.isEmpty(p.getParticipants())) {
            sb.append("(错误)参会人手机号为空;");
        }
        if (StringUtils.isEmpty(p.getQualifiedPerson())) {
            sb.append("(错误)达标人手机号为空;");
        }
        if (!StringUtils.isEmpty(p.getActivityDates()) && dateTransfer(p.getActivityDates()) == null) {
            sb.append("(错误)活动时间格式错误(正确示例:2020年08月01日);");
        }
        if (!StringUtils.isEmpty(p.getActivityEndDates()) && dateTransfer(p.getActivityEndDates()) == null) {
            sb.append("(错误)活动结束时间格式错误(正确示例:2020年08月01日);");
        }
        if (!StringUtils.isEmpty(p.getParticipants())) {//校验手机号
            String noPhone = getNoPhone(p.getParticipants());
            if (!StringUtils.isEmpty(noPhone)) {
                sb.append("(错误)参会人手机格式不正确").append(noPhone);
            }
        }
        if (!StringUtils.isEmpty(p.getQualifiedPerson())) {//校验手机号
            String noPhone = getNoPhone(p.getQualifiedPerson());
            if (!StringUtils.isEmpty(noPhone)) {
                sb.append("(错误)达标人手机格式不正确").append(noPhone);
            }
        }
        if (!StringUtils.isEmpty(p.getRetrogression())) {//校验手机号
            String noPhone = getNoPhone(p.getRetrogression());
            if (!StringUtils.isEmpty(noPhone)) {
                sb.append("(错误)达标名额退代手机格式不正确").append(noPhone);
            }
        }
        if (!StringUtils.isEmpty(p.getDeductedPerson())) {//校验手机号
            String noPhone = getNoPhone(p.getDeductedPerson());
            if (!StringUtils.isEmpty(noPhone)) {
                sb.append("(错误)抵扣名额人手机格式不正确").append(noPhone);
            }
        }
        if (!StringUtils.isEmpty(p.getPaymentAmount()) && !checkIsAmount(p.getPaymentAmount())) {//数字校验
            sb.append("(错误)付费金额非数字;");
        }
        if (!StringUtils.isEmpty(p.getDeductedAmount()) && !checkIsAmount(p.getDeductedAmount())) {//数字校验
            sb.append("(错误)扣除活动费用金额非数字;");
        }
        if (!StringUtils.isEmpty(p.getAgentPhone())) { //代理手机号校验
            MallUser user = userFeignClient.getUserByPhone(p.getAgentPhone().trim());
            if (user == null) {
                sb.append("(错误)代理手机号非代理;");
            }
        }
        if (!StringUtils.isEmpty(p.getQualifiedPerson())) { //达标人手机号校验
            String noPhone = getNoPhone(p.getQualifiedPerson());
            if (StringUtils.isEmpty(noPhone)) {
                String[] strings = p.getQualifiedPerson().split(",");
                String msg = checkIsAgent(Arrays.asList(strings), users);
                if (!StringUtils.isEmpty(msg)) {
                    sb.append("(错误)达标人手机号").append(msg).append("非代理;");
                }
            }
        }
        //参会人有数据重复校验
        if (!StringUtils.isEmpty(p.getParticipants())) {
            String repeatPhone = checkRepeatPhone(p.getParticipants());
            if (!StringUtils.isEmpty(repeatPhone)) {
                sb.append("(错误)参会人手机号重复").append(repeatPhone);
            }
        }
        //达标人有数据重复校验
        if (!StringUtils.isEmpty(p.getQualifiedPerson())) {
            String repeatPhone = checkRepeatPhone(p.getQualifiedPerson());
            if (!StringUtils.isEmpty(repeatPhone)) {
                sb.append("(错误)达标人手机号重复").append(repeatPhone);
            }
        }
        //达标退代有数据重复校验
        if (!StringUtils.isEmpty(p.getRetrogression())) {
            String repeatPhone = checkRepeatPhone(p.getRetrogression());
            if (!StringUtils.isEmpty(repeatPhone)) {
                sb.append("(错误)达标退代人手机号重复").append(repeatPhone);
            }
        }
        //抵用人重复抵用校验
        if (!StringUtils.isEmpty(p.getDeductedPerson())) {
            String repeatPhone = checkRepeatPhone(p.getDeductedPerson());
            if (!StringUtils.isEmpty(repeatPhone)) {
                sb.append("(错误)抵扣名额人手机号重复").append(repeatPhone);
            }
        }
        //校验抵扣名额手机号为总代，并且没有在其他活动中被抵扣
        if (!StringUtils.isEmpty(p.getDeductedPerson())) {
            String[] strings = p.getDeductedPerson().split(",");
            List<MallUser> deUsers = queryUsersByPhoneStr(p.getDeductedPerson());
            String msg = checkIsSuperAgent(Arrays.asList(strings), deUsers);
            if (!StringUtils.isEmpty(msg)) {
                sb.append("(错误)抵扣名额人手机号").append(msg).append("非总代理;");
            } else {
                msg = hasDeducted(Arrays.asList(strings));
                if (!StringUtils.isEmpty(msg)) {
                    sb.append("(错误)抵扣名额人手机号").append(msg).append("已被其他活动抵扣;");
                }
            }
        }
        //校验达标退代人是否为代理
        if (!StringUtils.isEmpty(p.getRetrogression())) {
            String noPhone = getNoPhone(p.getRetrogression());
            if (StringUtils.isEmpty(noPhone)) {
                String[] strings = p.getRetrogression().split(",");
                List<MallUser> reUsers = queryUsersByPhoneStr(p.getRetrogression());
                String msg = checkIsAgent(Arrays.asList(strings), reUsers);
                if (!StringUtils.isEmpty(msg)) {
                    sb.append("(错误)达标人退代手机号").append(msg).append("非代理;");
                }
            }
        }

        return sb.toString();
    }

    private String checkIsAgent(List<String> phones, List<MallUser> users) {
        StringBuilder sb = new StringBuilder();
        for (String phone : phones) {
            if (CollectionUtils.isEmpty(users)) {
                sb.append("[").append(phone).append("]");
            } else {
                List<MallUser> collect = users.stream().filter(u -> phone.trim().equals(u.getPhone())).collect(Collectors.toList());
                if (collect.size() == 0) {
                    sb.append("[").append(phone).append("]");
                }
            }
        }
        return sb.toString();
    }

    private String checkIsSuperAgent(List<String> phones, List<MallUser> users) {
        StringBuilder sb = new StringBuilder();
        List<MallUser> superAgents = null;
        if (!CollectionUtils.isEmpty(users)) {
            superAgents = users.stream().filter(u -> "4".equals(u.getRoleId())).collect(Collectors.toList());
        }
        for (String phone : phones) {
            if (CollectionUtils.isEmpty(superAgents)) {
                sb.append("[").append(phone).append("]");
            } else {
                List<MallUser> collect = superAgents.stream().filter(u -> phone.trim().equals(u.getPhone())).collect(Collectors.toList());
                if (collect.size() == 0) {
                    sb.append("[").append(phone).append("]");
                }
            }
        }
        return sb.toString();
    }

    private String hasDeducted(List<String> phones) {
        StringBuilder sb = new StringBuilder();
        List<ActivitiesQuota> quotas = activitiesQuotaService.queryDeductedPersonByPhones(phones);
        if (!CollectionUtils.isEmpty(quotas)) {
            for (String phone : phones) {
                List<ActivitiesQuota> list = quotas.stream().filter(q -> q.getAgentPhone().equals(phone.trim())).collect(Collectors.toList());
                if (list.size() != 0) {
                    sb.append("[").append(phone).append("]");
                }
            }
        }
        return sb.toString();
    }


    private String checkRepeatPhone(String phoneStr) {
        if (!StringUtils.isEmpty(phoneStr) && checkPhoneIsNumeric(phoneStr)) {
            StringBuilder sb = new StringBuilder();
            String[] strings = phoneStr.split(",");
            List<String> list = Arrays.asList(strings);
            for (String phone : strings) {
                List<String> l = list.stream().filter(p -> p.equals(phone)).collect(Collectors.toList());
                if (l.size() > 1) {
                    sb.append("[").append(phone).append("];");
                }
            }
            return sb.toString();
        }
        return null;
    }


    private String getNoPhone(String phoneStr) {
        StringBuilder sb = new StringBuilder();
        String[] strings = phoneStr.trim().split(",");
        for (String phone : strings) {
            if (phone.trim().length() != 11) {
                sb.append("[").append(phone).append("];");
            }
        }
        return sb.toString();
    }

    private Boolean checkIsAmount(String amount) {
        String regex = "^(([1-9][0-9]*)|(([0]\\.\\d{1,2}|[1-9][0-9]*\\.\\d{1,2})))$";
        Pattern p = Pattern.compile(regex);
        return p.matcher(amount).matches();
    }


    private List<MallBackAgent> getBackAgentByPhoneStr(String phoneStr) {
        if (!StringUtils.isEmpty(phoneStr) && checkPhoneIsNumeric(phoneStr)) {
            String[] strings = phoneStr.split(",");
            List<String> list = new ArrayList<>();
            for (String phone : strings) {
                list.add(phone.trim());
            }
            List<MallBackAgent> agentByPhones = agentFeignClient.getBackAgentByPhones(list);
            return agentByPhones;
        }
        return null;
    }

    private List<MallUser> queryUsersByPhoneStr(String phoneStr) {
        if (!StringUtils.isEmpty(phoneStr) && checkPhoneIsNumeric(phoneStr)) {
            String[] strings = phoneStr.split(",");
            List<String> list = new ArrayList<>();
            for (String phone : strings) {
                list.add(phone.trim());
            }
            List<MallUser> users = userFeignClient.queryUsersByPhones(list);
            return users;
        }
        return null;
    }

    private boolean checkPhoneIsNumeric(String numberStr) {
        Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");
        String[] strings = numberStr.split(",");
        for (String str : strings) {
            if (StringUtils.isEmpty(str)) {
                return false;
            }
            if (!pattern.matcher(str.trim()).matches()) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void getImportResult(String key, HttpServletResponse response) {
        String result = RedisUtil.get(key);
        List<ActivitiesStaticsResultModel> resultModels = JsonUtils.jsonToList(result, ActivitiesStaticsResultModel.class);
        try {
            ExcelUtil.writeExcel(response, resultModels, "活动统计导入结果", "活动统计导入结果", ExcelTypeEnum.XLSX, ActivitiesStaticsResultModel.class);
        } catch (ExcelException e) {
            log.error("getImportResult Error" + e);
        }
    }

    @Override
    public List<ActivitiesStatistics> queryByIds(List<Long> ids) {
        List<ActivitiesStatistics> list = this.list(new QueryWrapper<ActivitiesStatistics>()
                .in("id", ids).eq("deleted", DeletedCodeEnum.NOT_DELETE.getCode()));
        if (CollectionUtils.isEmpty(list)) {
            return null;
        }
        return list;
    }

}
