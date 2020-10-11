package com.meifute.nm.others.business.overseasstatistics.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.meifute.nm.others.business.overseasstatistics.entity.ActivitiesStatistics;
import com.meifute.nm.othersserver.domain.dto.ActivitiesStatisticsDto;
import com.meifute.nm.othersserver.domain.vo.ActivitiesStatisticsVO;
import com.meifute.nm.othersserver.domain.vo.QueryActivitiesStatisticsParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @Classname ActivitiesStatisticsService
 * @Description
 * @Date 2020-08-06 11:23
 * @Created by MR. Xb.Wu
 */
public interface ActivitiesStatisticsService {

    Page<ActivitiesStatisticsDto> queryActivitiesStatistics(QueryActivitiesStatisticsParam queryParam);

    boolean insertActivitiesStatistics(ActivitiesStatisticsVO statisticsVo);

    boolean editActivitiesStatistics(ActivitiesStatisticsVO statisticsVo);

    boolean deleteActivitiesStatistics(Long id);

    ActivitiesStatisticsVO queryEditActivitiesStatistics(Long id);

    ActivitiesStatistics queryById(Long id);

    String importActivities(MultipartFile file);

    void getImportResult(String key, HttpServletResponse response);

    List<ActivitiesStatistics> queryByIds(List<Long> ids);

}
