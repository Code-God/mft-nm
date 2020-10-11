package com.meifute.nm.others.business.overseasstatistics.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.base.Joiner;
import com.meifute.nm.others.business.overseasstatistics.entity.ActivitiesQuota;
import com.meifute.nm.others.business.overseasstatistics.entity.ActivitiesStatistics;
import com.meifute.nm.others.business.overseasstatistics.enums.ActivitiesQuotaEnum;
import com.meifute.nm.others.business.overseasstatistics.mapper.ActivitiesQuotaMapper;
import com.meifute.nm.others.business.overseasstatistics.service.ActivitiesQuotaService;
import com.meifute.nm.others.business.overseasstatistics.service.ActivitiesStatisticsService;
import com.meifute.nm.otherscommon.enums.DeletedCodeEnum;
import com.meifute.nm.othersserver.domain.dto.ActivitiesQuotaDto;
import com.meifute.nm.othersserver.domain.vo.QueryActivitiesQuotaParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Classname ActivitiesQuotaServiceImpl
 * @Description
 * @Date 2020-08-06 11:24
 * @Created by MR. Xb.Wu
 */
@Slf4j
@Service
public class ActivitiesQuotaServiceImpl extends ServiceImpl<ActivitiesQuotaMapper, ActivitiesQuota> implements ActivitiesQuotaService {

    @Autowired
    private ActivitiesStatisticsService activitiesStatisticsService;

    @Override
    public List<ActivitiesQuota> queryQuotaByStatisticsId(Long statisticsId) {
        List<ActivitiesQuota> activitiesQuotas = this.list(new QueryWrapper<ActivitiesQuota>()
                .eq("deleted", DeletedCodeEnum.NOT_DELETE.getCode())
                .eq("statistics_id", statisticsId));
        return activitiesQuotas;
    }

    @Override
    public boolean batchInsertActivitiesQuota(List<ActivitiesQuota> activitiesQuotas) {
        return this.saveBatch(activitiesQuotas);
    }

    @Override
    public boolean deleteQuotaByStatisticsId(Long statisticsId) {
        UpdateWrapper<ActivitiesQuota> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("statistics_id", statisticsId).set("deleted", DeletedCodeEnum.DELETED.getCode());
        return this.update(updateWrapper);
    }

    @Override
    public boolean batchUpdateQuotaById(List<ActivitiesQuota> activitiesQuotas) {
        return this.updateBatchById(activitiesQuotas);
    }

    @Override
    public Page<ActivitiesQuotaDto> queryActivitiesQualifiedPerson(QueryActivitiesQuotaParam queryParam) {
        Page<ActivitiesQuota> page = new Page<>(queryParam.getPageCurrent(), queryParam.getPageSize());
        Page<ActivitiesQuota> quotas = this.page(page, new QueryWrapper<ActivitiesQuota>()
                .eq("statistics_id", Long.parseLong(queryParam.getStatisticsId()))
                .like(!StringUtils.isEmpty(queryParam.getAgentName()), "agent_name", queryParam.getAgentName())
                .eq(!StringUtils.isEmpty(queryParam.getAgentPhone()), "agent_phone", queryParam.getAgentPhone())
                .in("type", Arrays.asList(ActivitiesQuotaEnum.QUALIFIED_PERSON.getCode(), ActivitiesQuotaEnum.RETROGRESSION.getCode()))
                .eq("deleted", DeletedCodeEnum.NOT_DELETE.getCode()));
        if (CollectionUtils.isEmpty(quotas.getRecords())) {
            return null;
        }
        Page<ActivitiesQuotaDto> r = new Page<>();
        List<ActivitiesQuotaDto> list = new ArrayList<>();

        List<ActivitiesQuota> listCount = this.list(new QueryWrapper<ActivitiesQuota>()
                .eq("statistics_id", Long.parseLong(queryParam.getStatisticsId()))
                .like(!StringUtils.isEmpty(queryParam.getAgentName()), "agent_name", queryParam.getAgentName())
                .eq(!StringUtils.isEmpty(queryParam.getAgentPhone()), "agent_phone", queryParam.getAgentPhone())
                .in("type", Arrays.asList(ActivitiesQuotaEnum.QUALIFIED_PERSON.getCode(), ActivitiesQuotaEnum.RETROGRESSION.getCode()))
                .eq("deleted", DeletedCodeEnum.NOT_DELETE.getCode()).groupBy("agent_phone"));

        //如果达标和退代有相同的手机号，则只显示退代的手机号
        //找出相同的手机号
        List<String> phones = quotas.getRecords().stream().
                collect(Collectors.groupingBy(ActivitiesQuota::getAgentPhone, Collectors.counting()))
                .entrySet().stream()
                .filter(entry->entry.getValue()>1)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());

        List<ActivitiesQuota> qualifiedPersons = new ArrayList<>();
        if (phones.size() > 0) {
            List<ActivitiesQuota> validPerson = quotas.getRecords().stream().filter(
                    v -> v.getType().equals(ActivitiesQuotaEnum.QUALIFIED_PERSON.getCode()) && !phones.contains(v.getAgentPhone()))
                    .collect(Collectors.toList());
            List<ActivitiesQuota> backPerson = quotas.getRecords().stream().filter(
                    b -> b.getType().equals(ActivitiesQuotaEnum.RETROGRESSION.getCode()))
                    .collect(Collectors.toList());

            qualifiedPersons.addAll(validPerson);
            qualifiedPersons.addAll(backPerson);
        } else {
            qualifiedPersons.addAll(quotas.getRecords());
        }


        qualifiedPersons.forEach(p -> {
            ActivitiesQuotaDto dto = new ActivitiesQuotaDto();
            BeanUtils.copyProperties(p, dto);
            dto.setId(String.valueOf(p.getId()));
            //查询是已经作为抵用名额使用
            List<ActivitiesQuota> quotaList = this.list(new QueryWrapper<ActivitiesQuota>()
                    .eq("deleted", DeletedCodeEnum.NOT_DELETE.getCode())
                    .eq("type", ActivitiesQuotaEnum.DEDUCTED_PERSON.getCode())
                    .eq("agent_phone", p.getAgentPhone()));
            if (!CollectionUtils.isEmpty(quotaList)) {
                List<Long> ids = quotaList.stream().map(ActivitiesQuota::getStatisticsId).collect(Collectors.toList());
                List<ActivitiesStatistics> statistics = activitiesStatisticsService.queryByIds(ids);
                if (statistics != null) {
                    List<String> activityNames = statistics.stream().map(ActivitiesStatistics::getActivityName).collect(Collectors.toList());
                    String activityName = Joiner.on(",").join(activityNames);
                    dto.setDeductedPersonAcName(activityName);
                }
            }
            list.add(dto);
        });
        BeanUtils.copyProperties(quotas, r, "records");
        r.setRecords(list);
        r.setTotal(listCount.size());
        return r;
    }

    @Override
    public Page<ActivitiesQuotaDto> queryActivitiesRetrogression(QueryActivitiesQuotaParam queryParam) {
        Page<ActivitiesQuota> page = new Page<>(queryParam.getPageCurrent(), queryParam.getPageSize());
        Page<ActivitiesQuota> quotas = this.page(page, new QueryWrapper<ActivitiesQuota>()
                .eq("statistics_id", Long.parseLong(queryParam.getStatisticsId()))
                .like(!StringUtils.isEmpty(queryParam.getAgentName()), "agent_name", queryParam.getAgentName())
                .eq(!StringUtils.isEmpty(queryParam.getAgentPhone()), "agent_phone", queryParam.getAgentPhone())
                .eq("type", ActivitiesQuotaEnum.RETROGRESSION.getCode())
                .eq("deleted", DeletedCodeEnum.NOT_DELETE.getCode()));
        if (CollectionUtils.isEmpty(quotas.getRecords())) {
            return null;
        }
        Page<ActivitiesQuotaDto> r = new Page<>();
        List<ActivitiesQuotaDto> list = new ArrayList<>();
        quotas.getRecords().forEach(p -> {
            ActivitiesQuotaDto dto = new ActivitiesQuotaDto();
            BeanUtils.copyProperties(p, dto);
            dto.setId(String.valueOf(p.getId()));
            list.add(dto);
        });
        BeanUtils.copyProperties(quotas, r, "records");
        r.setRecords(list);
        return r;
    }

    @Override
    public Page<ActivitiesQuotaDto> queryActivitiesDeductedPerson(QueryActivitiesQuotaParam queryParam) {
        Page<ActivitiesQuota> page = new Page<>(queryParam.getPageCurrent(), queryParam.getPageSize());
        Page<ActivitiesQuota> quotas = this.page(page, new QueryWrapper<ActivitiesQuota>()
                .eq("statistics_id", Long.parseLong(queryParam.getStatisticsId()))
                .like(!StringUtils.isEmpty(queryParam.getAgentName()), "agent_name", queryParam.getAgentName())
                .eq(!StringUtils.isEmpty(queryParam.getAgentPhone()), "agent_phone", queryParam.getAgentPhone())
                .eq("type", ActivitiesQuotaEnum.DEDUCTED_PERSON.getCode())
                .eq("deleted", DeletedCodeEnum.NOT_DELETE.getCode()));
        if (CollectionUtils.isEmpty(quotas.getRecords())) {
            return null;
        }
        Page<ActivitiesQuotaDto> r = new Page<>();
        List<ActivitiesQuotaDto> list = new ArrayList<>();
        quotas.getRecords().forEach(p -> {
            ActivitiesQuotaDto dto = new ActivitiesQuotaDto();
            BeanUtils.copyProperties(p, dto);
            dto.setId(String.valueOf(p.getId()));
            list.add(dto);
        });
        BeanUtils.copyProperties(quotas, r, "records");
        r.setRecords(list);
        return r;
    }

    @Override
    public List<ActivitiesQuota> queryDeductedPersonByPhone(String phone) {
        List<ActivitiesQuota> quotaList = this.list(new QueryWrapper<ActivitiesQuota>()
                .eq("deleted", DeletedCodeEnum.NOT_DELETE.getCode())
                .eq("agent_phone", phone));
        if (CollectionUtils.isEmpty(quotaList)) {
            return null;
        }
        return quotaList;
    }

    @Override
    public List<ActivitiesQuota> queryDeductedPersonByPhones(List<String> phone) {
        List<ActivitiesQuota> quotaList = this.list(new QueryWrapper<ActivitiesQuota>()
                .eq("deleted", DeletedCodeEnum.NOT_DELETE.getCode())
                .in("agent_phone", phone));
        if (CollectionUtils.isEmpty(quotaList)) {
            return null;
        }
        return quotaList;
    }
}
