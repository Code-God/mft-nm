package com.meifute.nm.others.business.overseasstatistics.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.meifute.nm.others.business.overseasstatistics.entity.ActivitiesQuota;
import com.meifute.nm.othersserver.domain.dto.ActivitiesQuotaDto;
import com.meifute.nm.othersserver.domain.vo.QueryActivitiesQuotaParam;

import java.util.List;

/**
 * @Classname ActivitiesQuotaService
 * @Description
 * @Date 2020-08-06 11:23
 * @Created by MR. Xb.Wu
 */
public interface ActivitiesQuotaService {

    List<ActivitiesQuota> queryQuotaByStatisticsId(Long statisticsId);

    boolean batchInsertActivitiesQuota(List<ActivitiesQuota> activitiesQuotas);

    boolean deleteQuotaByStatisticsId(Long statisticsId);

    boolean batchUpdateQuotaById(List<ActivitiesQuota> activitiesQuotas);

    Page<ActivitiesQuotaDto> queryActivitiesQualifiedPerson(QueryActivitiesQuotaParam queryParam);

    Page<ActivitiesQuotaDto> queryActivitiesRetrogression(QueryActivitiesQuotaParam queryParam);

    Page<ActivitiesQuotaDto> queryActivitiesDeductedPerson(QueryActivitiesQuotaParam queryParam);

    List<ActivitiesQuota> queryDeductedPersonByPhone(String phone);

    List<ActivitiesQuota> queryDeductedPersonByPhones(List<String> phone);
}
