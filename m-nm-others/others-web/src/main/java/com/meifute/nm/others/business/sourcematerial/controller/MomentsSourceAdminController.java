package com.meifute.nm.others.business.sourcematerial.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.meifute.nm.others.business.sourcematerial.service.MomentsClassifyService;
import com.meifute.nm.others.business.sourcematerial.service.MomentsSourceService;
import com.meifute.nm.otherscommon.base.BaseController;
import com.meifute.nm.otherscommon.base.MallResponse;
import com.meifute.nm.othersserver.domain.vo.moments.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Classname MomentsSourceAdminController
 * @Description 朋友圈素材后台接口
 * @Date 2020-08-13 12:21
 * @Created by MR. Xb.Wu
 */
@Slf4j
@RestController
@RequestMapping("/moments/admin")
@Api(tags = "朋友圈素材后台接口")
public class MomentsSourceAdminController extends BaseController {

    @Autowired
    private MomentsSourceService momentsSourceService;
    @Autowired
    private MomentsClassifyService momentsClassifyService;

    @ApiOperation(value = "创建素材", notes = "创建素材")
    @PostMapping(value = "/source")
    public ResponseEntity<MallResponse<Boolean>> createSource(@RequestBody MomentsSourceVO momentsSourceVO) {
        boolean source = momentsSourceService.createSource(momentsSourceVO);
        return ResponseEntity.ok(successResult(source));
    }

    @ApiOperation(value = "获取素材列表", notes = "获取素材列表")
    @PostMapping(value = "/query/source")
    public ResponseEntity<MallResponse<Page<QueryMomentsSourceDto>>> querySource(@RequestBody QueryAdminMomentsSource queryAdminMomentsSource) {
        Page<QueryMomentsSourceDto> dtoPage = momentsSourceService.querySource(queryAdminMomentsSource);
        return ResponseEntity.ok(successResult(dtoPage));
    }

    @ApiOperation(value = "编辑素材", notes = "编辑素材")
    @PutMapping(value = "/edit/source")
    public ResponseEntity<MallResponse<Boolean>> editSource(@RequestBody MomentsSourceVO momentsSourceVO) {
        boolean result = momentsSourceService.editSource(momentsSourceVO);
        return ResponseEntity.ok(successResult(result));
    }

    @ApiOperation(value = "删除素材", notes = "删除素材")
    @DeleteMapping(value = "/source/{id}")
    public ResponseEntity<MallResponse<Boolean>> deleteSource(@PathVariable("id") String id) {
        boolean b = momentsSourceService.deleteSource(Long.parseLong(id));
        return ResponseEntity.ok(successResult(b));
    }

    @ApiOperation(value = "发布素材", notes = "发布素材")
    @PutMapping(value = "/release/source/{id}")
    public ResponseEntity<MallResponse<Boolean>> releaseSource(@PathVariable("id") String id) {
        boolean result = momentsSourceService.releaseSource(Long.parseLong(id));
        return ResponseEntity.ok(successResult(result));
    }

    @ApiOperation(value = "素材开关(是否在app上显示素材模块) 开启/关闭", notes = "开启/关闭(返回开关状态)")
    @GetMapping(value = "/switch/moments")
    public ResponseEntity<MallResponse<Integer>> switchMoments(@RequestParam("onOff") @ApiParam(name = "onOff", value = "0查询，1开启，2关闭") Integer onOff) {
        Integer result = momentsSourceService.switchMoments(onOff);
        return ResponseEntity.ok(successResult(result));
    }


    @ApiOperation(value = "获取分类", notes = "获取分类")
    @GetMapping("/classifications")
    public ResponseEntity<MallResponse<List<MomentsClassificationVO>>> queryClassification() {
        List<MomentsClassificationVO> classifications = momentsClassifyService.queryClassification();
        return ResponseEntity.ok(successResult(classifications));
    }

    @ApiOperation(value = "创建分类", notes = "创建分类")
    @PostMapping(value = "/classification")
    public ResponseEntity<MallResponse<Boolean>> createClassification(@RequestBody MomentsClassificationVO momentsClassificationVO) {
        boolean result = momentsClassifyService.createClassification(momentsClassificationVO);
        return ResponseEntity.ok(successResult(result));
    }

    @ApiOperation(value = "编辑分类", notes = "编辑分类")
    @PutMapping(value = "/classification")
    public ResponseEntity<MallResponse<Boolean>> updateClassification(@RequestBody MomentsClassificationVO momentsClassificationVO) {
        boolean result = momentsClassifyService.editClassification(momentsClassificationVO);
        return ResponseEntity.ok(successResult(result));
    }

    @ApiOperation(value = "删除分类", notes = "删除分类")
    @DeleteMapping(value = "/classification/{id}")
    public ResponseEntity<MallResponse<Boolean>> deleteClassification(@PathVariable("id") String id) {
        boolean result = momentsClassifyService.deleteClassification(Long.parseLong(id));
        return ResponseEntity.ok(successResult(result));
    }

    @ApiOperation(value = "排序分类", notes = "排序分类")
    @PostMapping(value = "/sort/classification")
    public ResponseEntity<MallResponse<Boolean>> sortClassification(@RequestBody SortClassification sortClassification) {
        boolean result = momentsClassifyService.sortClassification(sortClassification);
        return ResponseEntity.ok(successResult(result));
    }
}
