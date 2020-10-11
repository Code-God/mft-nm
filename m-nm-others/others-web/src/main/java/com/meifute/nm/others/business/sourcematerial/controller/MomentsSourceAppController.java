package com.meifute.nm.others.business.sourcematerial.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.meifute.nm.others.business.sourcematerial.service.MomentsClassifyService;
import com.meifute.nm.others.business.sourcematerial.service.MomentsOperationService;
import com.meifute.nm.others.business.sourcematerial.service.MomentsSourceService;
import com.meifute.nm.otherscommon.base.BaseController;
import com.meifute.nm.otherscommon.base.MallResponse;
import com.meifute.nm.othersserver.domain.vo.moments.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Classname MomentsSourceAppController
 * @Description 朋友圈素材
 * @Date 2020-08-13 12:21
 * @Created by MR. Xb.Wu
 */
@Slf4j
@RestController
@RequestMapping("/moments/app")
@Api(tags = "朋友圈素材APP接口")
public class MomentsSourceAppController extends BaseController {

    @Autowired
    private MomentsSourceService momentsSourceService;
    @Autowired
    private MomentsClassifyService momentsClassifyService;
    @Autowired
    private MomentsOperationService momentsOperationService;


    @ApiOperation(value = "获取素材", notes = "获取素材")
    @PostMapping(value = "/query/source")
    public ResponseEntity<MallResponse<Page<QueryMomentsSourceDto>>> querySourceForApp(@RequestBody QueryAppMomentsSource queryAppMomentsSource) {
        Page<QueryMomentsSourceDto> dtoPage = momentsSourceService.querySource(queryAppMomentsSource);
        return ResponseEntity.ok(successResult(dtoPage));
    }

    @ApiOperation(value = "记录发圈", notes = "记录发圈")
    @PostMapping(value = "/record/operation")
    public ResponseEntity<MallResponse<Boolean>> operationMoment(@RequestBody MomentOperationVO momentOperationVO) {
        boolean result = momentsOperationService.recordMoment(momentOperationVO);
        return ResponseEntity.ok(successResult(result));
    }

    @ApiOperation(value = "点赞/取消点赞", notes = "点赞/取消点赞")
    @PostMapping(value = "/give/thumbs/up")
    public ResponseEntity<MallResponse<Boolean>> giveUpMoment(@RequestBody GiveUpParam giveUpParam) {
        boolean result = momentsOperationService.giveUpMoment(giveUpParam);
        return ResponseEntity.ok(successResult(result));
    }

    @ApiOperation(value = "获取当天已发布素材的数量", notes = "获取当天已发布素材的数量")
    @GetMapping(value = "/today/source/number")
    public ResponseEntity<MallResponse<TodaySourceNum>> getTodayReleasedSourceNum() {
        TodaySourceNum sourceNum = momentsSourceService.getTodayReleasedSourceNum();
        return ResponseEntity.ok(successResult(sourceNum));
    }

    @ApiOperation(value = "获取分类", notes = "获取分类")
    @GetMapping("/classifications")
    public ResponseEntity<MallResponse<List<MomentsClassificationVO>>> queryClassification() {
        List<MomentsClassificationVO> classifications = momentsClassifyService.queryClassification();
        return ResponseEntity.ok(successResult(classifications));
    }

}
