package com.meifute.nm.others.business.overseasstatistics.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.meifute.nm.others.business.overseasstatistics.service.ActivitiesQuotaService;
import com.meifute.nm.others.business.overseasstatistics.service.ActivitiesStatisticsService;
import com.meifute.nm.otherscommon.base.BaseController;
import com.meifute.nm.otherscommon.base.MallResponse;
import com.meifute.nm.othersserver.domain.dto.ActivitiesQuotaDto;
import com.meifute.nm.othersserver.domain.dto.ActivitiesStatisticsDto;
import com.meifute.nm.othersserver.domain.vo.ActivitiesStatisticsVO;
import com.meifute.nm.othersserver.domain.vo.QueryActivitiesQuotaParam;
import com.meifute.nm.othersserver.domain.vo.QueryActivitiesStatisticsParam;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;


/**
 * @Classname ActivitiesStatisticsSignController
 * @Description
 * @Date 2020-08-06 11:25
 * @Created by MR. Xb.Wu
 */
@Slf4j
@RequestMapping("/bgw/activities/statistics")
@RestController
@Api(tags = "海外游活动统计接口")
public class ActivitiesStatisticsSignController extends BaseController {

    @Autowired
    private ActivitiesStatisticsService activitiesStatisticsService;

    @Autowired
    private ActivitiesQuotaService activitiesQuotaService;

    @ApiOperation(value = "查询活动统计与标记列表", notes = "查询活动统计与标记列表")
    @PostMapping(value = "/query/list")
    public ResponseEntity<MallResponse<Page<ActivitiesStatisticsDto>>> queryActivitiesStatistics(@RequestBody QueryActivitiesStatisticsParam queryParam) {
        Page<ActivitiesStatisticsDto> statisticsDto = activitiesStatisticsService.queryActivitiesStatistics(queryParam);
        return ResponseEntity.ok(successResult(statisticsDto));
    }

    @ApiOperation(value = "新增活动统计与标记", notes = "新增活动统计与标记")
    @PostMapping(value = "/insert")
    public ResponseEntity<MallResponse<Boolean>> insertActivitiesStatistics(@RequestBody ActivitiesStatisticsVO statisticsVo) {
        boolean b = activitiesStatisticsService.insertActivitiesStatistics(statisticsVo);
        return ResponseEntity.ok(successResult(b));
    }

    @ApiOperation(value = "查询编辑活动统计与标记", notes = "查询编辑活动统计与标记")
    @GetMapping(value = "/edit/query")
    public ResponseEntity<MallResponse<ActivitiesStatisticsVO>> queryEditActivitiesStatistics(@RequestParam("id") String id) {
        ActivitiesStatisticsVO statisticsVo = activitiesStatisticsService.queryEditActivitiesStatistics(Long.parseLong(id));
        return ResponseEntity.ok(successResult(statisticsVo));
    }

    @ApiOperation(value = "编辑活动统计与标记", notes = "编辑活动统计与标记")
    @PostMapping(value = "/edit")
    public ResponseEntity<MallResponse<Boolean>> editActivitiesStatistics(@RequestBody ActivitiesStatisticsVO statisticsVo) {
        boolean b = activitiesStatisticsService.editActivitiesStatistics(statisticsVo);
        return ResponseEntity.ok(successResult(b));
    }

    @ApiOperation(value = "删除统计与标记", notes = "删除统计与标记")
    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<MallResponse<Boolean>> deleteActivitiesStatistics(@PathVariable("id") String id) {
        boolean b = activitiesStatisticsService.deleteActivitiesStatistics(Long.parseLong(id));
        return ResponseEntity.ok(successResult(b));
    }

    @ApiOperation(value = "查询达标人数", notes = "查询达标人数")
    @PostMapping(value = "/qualifiedPerson")
    public ResponseEntity<MallResponse<Page<ActivitiesQuotaDto>>> queryActivitiesQualifiedPerson(@RequestBody QueryActivitiesQuotaParam queryParam) {
        Page<ActivitiesQuotaDto> quotaDtos = activitiesQuotaService.queryActivitiesQualifiedPerson(queryParam);
        return ResponseEntity.ok(successResult(quotaDtos));
    }

    @ApiOperation(value = "查询达标名额是否有退代", notes = "查询达标名额是否有退代")
    @PostMapping(value = "/retrogression")
    public ResponseEntity<MallResponse<Page<ActivitiesQuotaDto>>> queryActivitiesRetrogression(@RequestBody QueryActivitiesQuotaParam queryParam) {
        Page<ActivitiesQuotaDto> quotaDtos = activitiesQuotaService.queryActivitiesRetrogression(queryParam);
        return ResponseEntity.ok(successResult(quotaDtos));
    }

    @ApiOperation(value = "查询是否其他名额抵扣费用", notes = "查询是否其他名额抵扣费用")
    @PostMapping(value = "/deductedPerson")
    public ResponseEntity<MallResponse<Page<ActivitiesQuotaDto>>> queryActivitiesDeductedPerson(@RequestBody QueryActivitiesQuotaParam queryParam) {
        Page<ActivitiesQuotaDto> quotaDtos = activitiesQuotaService.queryActivitiesDeductedPerson(queryParam);
        return ResponseEntity.ok(successResult(quotaDtos));
    }

    @ApiOperation(value = "导入活动统计与标记", notes = "导入活动统计与标记")
    @PostMapping(value = "/import/activities")
    public ResponseEntity<MallResponse<String>> importActivities(@RequestBody MultipartFile file) {
        String key = activitiesStatisticsService.importActivities(file);
        return ResponseEntity.ok(successResult(key));
    }

    @ApiOperation(value = "获取导入活动统计与标记结果", notes = "获取导入活动统计与标记结果")
    @GetMapping(value = "/get/import/result")
    public ResponseEntity<MallResponse<Boolean>> getImportResult(@RequestParam("key") String key, HttpServletResponse response) {
        activitiesStatisticsService.getImportResult(key, response);
        return ResponseEntity.ok(successResult(true));
    }
}
