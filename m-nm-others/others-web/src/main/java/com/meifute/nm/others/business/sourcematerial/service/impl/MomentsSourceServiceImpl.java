package com.meifute.nm.others.business.sourcematerial.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.meifute.nm.others.business.sourcematerial.entity.MomentsClassification;
import com.meifute.nm.others.business.sourcematerial.entity.MomentsSource;
import com.meifute.nm.others.business.sourcematerial.mapper.MomentsSourceMapper;
import com.meifute.nm.others.business.sourcematerial.service.MomentsClassifyService;
import com.meifute.nm.others.business.sourcematerial.service.MomentsOperationService;
import com.meifute.nm.others.business.sourcematerial.service.MomentsSourceService;
import com.meifute.nm.others.utils.CurrentUserService;
import com.meifute.nm.others.utils.MftUser;
import com.meifute.nm.others.utils.RedisUtil;
import com.meifute.nm.otherscommon.enums.DeletedCodeEnum;
import com.meifute.nm.otherscommon.utils.IDUtils;
import com.meifute.nm.otherscommon.utils.JsonUtils;
import com.meifute.nm.othersserver.domain.vo.moments.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Classname MomentsSourceServiceImpl
 * @Description
 * @Date 2020-08-13 12:21
 * @Created by MR. Xb.Wu
 */
@Slf4j
@Service
public class MomentsSourceServiceImpl extends ServiceImpl<MomentsSourceMapper, MomentsSource> implements MomentsSourceService {

    @Autowired
    private MomentsClassifyService momentsClassifyService;
    @Autowired
    private MomentsOperationService momentsOperationService;

    @Override
    public boolean createSource(MomentsSourceVO momentsSourceVO) {
        MomentsSource momentsSource = new MomentsSource();
        BeanUtils.copyProperties(momentsSourceVO, momentsSource);
        momentsSource.setId(IDUtils.genLongId());
        return this.save(momentsSource);
    }

    @Override
    public boolean editSource(MomentsSourceVO momentsSourceVO) {
        MomentsSource momentsSource = new MomentsSource();
        BeanUtils.copyProperties(momentsSourceVO, momentsSource);
        momentsSource.setId(Long.parseLong(momentsSourceVO.getId()));
        return this.updateById(momentsSource);
    }

    @Override
    public boolean deleteSource(Long id) {
        return this.removeById(id);
    }

    @Override
    public boolean releaseSource(Long id) {
        MomentsSource momentsSource = new MomentsSource();
        momentsSource.setId(id);
        momentsSource.setStatus("1");
        momentsSource.setReleaseTime(LocalDateTime.now());
        return this.updateById(momentsSource);
    }

    @Override
    public Page<QueryMomentsSourceDto> querySource(QueryAdminMomentsSource queryAdminMomentsSource) {
        Page<MomentsSource> page = new Page<>(queryAdminMomentsSource.getPageCurrent(), queryAdminMomentsSource.getPageSize());
        Page<MomentsSource> sourcePage = this.page(page, new QueryWrapper<MomentsSource>()
                .eq(!StringUtils.isEmpty(queryAdminMomentsSource.getClassifyId()), "classify_id", queryAdminMomentsSource.getClassifyId())
                .eq(!StringUtils.isEmpty(queryAdminMomentsSource.getStatus()), "status", queryAdminMomentsSource.getStatus())
                .gt(!StringUtils.isEmpty(queryAdminMomentsSource.getReleaseStartTime()), "release_time", queryAdminMomentsSource.getReleaseStartTime())
                .lt(!StringUtils.isEmpty(queryAdminMomentsSource.getReleaseEndTime()), "release_time", queryAdminMomentsSource.getReleaseEndTime())
                .gt(!StringUtils.isEmpty(queryAdminMomentsSource.getCreateStartTime()), "create_time", queryAdminMomentsSource.getCreateStartTime())
                .lt(!StringUtils.isEmpty(queryAdminMomentsSource.getCreateEndTime()), "create_time", queryAdminMomentsSource.getCreateEndTime())
                .like(!StringUtils.isEmpty(queryAdminMomentsSource.getContent()), "content", queryAdminMomentsSource.getContent())
                .orderByDesc("create_time"));
        Page<QueryMomentsSourceDto> dto = new Page<>();
        List<QueryMomentsSourceDto> list = new ArrayList<>();
        if (!CollectionUtils.isEmpty(sourcePage.getRecords())) {
            sourcePage.getRecords().forEach(p -> {
                QueryMomentsSourceDto query = new QueryMomentsSourceDto();
                BeanUtils.copyProperties(p, query);
                query.setId(String.valueOf(p.getId()));
                Integer giveUpNum = momentsOperationService.getGiveUpMomentNumber(p.getId());
                query.setGiveUpNum(giveUpNum);
                Integer usedNum = momentsOperationService.getRecordMomentNumber(p.getId());
                query.setUsedNum(usedNum);
                MomentsClassification classification = momentsClassifyService.queryById(p.getClassifyId());
                query.setClassifyId(String.valueOf(classification.getId()));
                query.setClassifyName(classification.getClassifyName());
                list.add(query);
            });
        }
        BeanUtils.copyProperties(page, dto, "records");
        dto.setRecords(list);
        return dto;
    }

    @Override
    public Page<QueryMomentsSourceDto> querySource(QueryAppMomentsSource queryAppMomentsSource) {
        MftUser mftUser = CurrentUserService.getCurrentMftUser();
        Long userId = mftUser.getId();
        Page<MomentsSource> page = new Page<>(queryAppMomentsSource.getPageCurrent(), queryAppMomentsSource.getPageSize());
        Page<MomentsSource> result = this.page(page, new QueryWrapper<MomentsSource>()
                .eq(!StringUtils.isEmpty(queryAppMomentsSource.getClassifyId()), "classify_id", queryAppMomentsSource.getClassifyId())
                .like(!StringUtils.isEmpty(queryAppMomentsSource.getContent()), "content", queryAppMomentsSource.getContent())
                .eq("status", "1")
                .orderByDesc("release_time"));
        Page<QueryMomentsSourceDto> dto = new Page<>();
        List<QueryMomentsSourceDto> list = new ArrayList<>();
        if (!CollectionUtils.isEmpty(result.getRecords())) {
            result.getRecords().forEach(p -> {
                QueryMomentsSourceDto query = new QueryMomentsSourceDto();
                BeanUtils.copyProperties(p, query);
                query.setId(String.valueOf(p.getId()));
                boolean isGiveUp = momentsOperationService.checkIsGiveUp(userId, p.getId());
                query.setIsGiveUp(isGiveUp);
                query.setClassifyId(String.valueOf(p.getClassifyId()));
                list.add(query);
            });
            //标记已读取
            recordNumSource(userId);
        }
        BeanUtils.copyProperties(page, dto, "records");
        dto.setRecords(list);
        return null;
    }

    private void recordNumSource(Long userId) {
        List<MomentsSource> todaySources = getTodaySource();
        if (!CollectionUtils.isEmpty(todaySources)) {
            String sourceRedis = RedisUtil.get("todaySourceNum:moment_" + userId);
            if (!StringUtils.isEmpty(sourceRedis)) {
                List<MomentsSource> momentsSources = JsonUtils.jsonToList(sourceRedis, MomentsSource.class);
                List<Long> ids = momentsSources.stream().map(MomentsSource::getId).collect(Collectors.toList());
                List<MomentsSource> sources = todaySources.stream().filter(p -> !ids.contains(p.getId())).collect(Collectors.toList());
                if (sources.size() > 0) {
                    RedisUtil.set("todaySourceNum:moment_" + userId, JsonUtils.objectToJson(todaySources), getToDayMaxTime());
                }
            } else {
                RedisUtil.set("todaySourceNum:moment_" + userId, JsonUtils.objectToJson(todaySources), getToDayMaxTime());
            }
        }
    }

    private List<MomentsSource> getTodaySource() {
        List<MomentsSource> sources = this.list(new QueryWrapper<MomentsSource>()
                .eq("status", "1")
                .ge("release_time", LocalDateTime.of(LocalDate.now(), LocalTime.MIN)));
        if (CollectionUtils.isEmpty(sources)) {
            return null;
        }
        return sources;
    }

    private long getToDayMaxTime() {
        LocalDateTime end_date = LocalDateTime.of(LocalDate.now(), LocalTime.MAX);
        Duration between = Duration.between(LocalDateTime.now(), end_date);
        return between.getSeconds();
    }

    @Override
    public Integer switchMoments(Integer onOff) {
        String momentsOff = RedisUtil.get("moments_off");
        if (0 == onOff) {
            if (momentsOff == null) {
                RedisUtil.set("moments_off", "2");
                momentsOff = "2";
            }
        } else {
            RedisUtil.set("moments_off", String.valueOf(onOff));
            momentsOff = String.valueOf(onOff);
        }
        return Integer.parseInt(momentsOff);
    }

    @Override
    public TodaySourceNum getTodayReleasedSourceNum() {
        MftUser mftUser = CurrentUserService.getCurrentMftUser();
        Long userId = mftUser.getId();
        String momentsOff = RedisUtil.get("moments_off");
        if (momentsOff == null) {
            RedisUtil.set("moments_off", "2");
            momentsOff = "2";
        }
        TodaySourceNum todaySourceNum = new TodaySourceNum();
        todaySourceNum.setOnOff(Integer.parseInt(momentsOff));
        List<MomentsSource> todaySources = getTodaySource();
        int todaySourceNumber = todaySources == null ? 0 : todaySources.size();
        String sourceRedis = RedisUtil.get("todaySourceNum:moment_" + userId);
        if (sourceRedis != null && todaySources != null) {
            List<MomentsSource> momentsSources = JsonUtils.jsonToList(sourceRedis, MomentsSource.class);
            List<Long> ids = momentsSources.stream().map(MomentsSource::getId).collect(Collectors.toList());
            List<MomentsSource> sources = todaySources.stream().filter(p -> !ids.contains(p.getId())).collect(Collectors.toList());
            todaySourceNum.setTodayReleasedSourceNum(sources.size());
        } else {
            todaySourceNum.setTodayReleasedSourceNum(todaySourceNumber);
        }
        return todaySourceNum;
    }

    @Override
    public List<MomentsSource> getMomentSourceByClassifyId(Long classifyId) {
        return this.list(new QueryWrapper<MomentsSource>()
                .eq("classify_id", classifyId)
                .eq("deleted", DeletedCodeEnum.NOT_DELETE.getCode()));
    }
}
