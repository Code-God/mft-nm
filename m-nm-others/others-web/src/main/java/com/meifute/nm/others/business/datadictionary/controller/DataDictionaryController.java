package com.meifute.nm.others.business.datadictionary.controller;

import com.meifute.nm.otherscommon.base.BaseController;
import com.meifute.nm.otherscommon.base.MallResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @Classname RegionController
 * @Description
 * @Date 2020-08-14 15:54
 * @Created by MR. Xb.Wu
 */
@Slf4j
@RestController
@RequestMapping("/data/dictionary")
@Api(tags = "数据字典接口")
public class DataDictionaryController extends BaseController {

    @ApiOperation(value = "创建省市区(标记叶子节点)", notes = "创建省(标记叶子节点)")
    @PostMapping("/region")
    public ResponseEntity<MallResponse<Boolean>> createRegion() {
        return ResponseEntity.ok(successResult(true));
    }

    @ApiOperation(value = "获取第一层省级列表", notes = "获取第一层省级列表")
    @GetMapping("/region/province/list")
    public ResponseEntity<MallResponse<Boolean>> queryRegionProvinceList() {
        return ResponseEntity.ok(successResult(true));
    }

    @ApiOperation(value = "根据上一级区域id获取下一级区域列表", notes = "根据上一级区域id获取下一级区域列表")
    @GetMapping("/region/next/list")
    public ResponseEntity<MallResponse<Boolean>> queryRegionNextList() {
        return ResponseEntity.ok(successResult(true));
    }

    @ApiOperation(value = "创建职业信息", notes = "创建职业信息")
    @PostMapping("/occupation")
    public ResponseEntity<MallResponse<Boolean>> createOccupation() {
        return ResponseEntity.ok(successResult(true));
    }

    @ApiOperation(value = "分页条件获取职业列表", notes = "分页条件获取职业列表")
    @PostMapping("/occupation/page")
    public ResponseEntity<MallResponse<Boolean>> queryOccupationPage() {
        return ResponseEntity.ok(successResult(true));
    }

    @ApiOperation(value = "创建列值", notes = "创建列值")
    @PostMapping("/sys/column")
    public ResponseEntity<MallResponse<Boolean>> creteSysColumn() {
        return ResponseEntity.ok(successResult(true));
    }

    @ApiOperation(value = "查询第一层值大列列表", notes = "查询第一层值大列列表")
    @GetMapping("/sys/column/list")
    public ResponseEntity<MallResponse<Boolean>> querySysColumn() {
        return ResponseEntity.ok(successResult(true));
    }

    @ApiOperation(value = "根据值大列CODE查询小类列表", notes = "根据值大列CODE查询小类列表")
    @GetMapping("/sys/next/column/list/{code}")
    public ResponseEntity<MallResponse<Boolean>> queryNextSysColumnList(@PathVariable("code") String code) {
        return ResponseEntity.ok(successResult(true));
    }

    @ApiOperation(value = "根据code值查询列值", notes = "根据code值查询列值")
    @GetMapping("/sys/column/{code}")
    public ResponseEntity<MallResponse<Boolean>> querySysColumnByCode(@PathVariable("code") String code) {
        return ResponseEntity.ok(successResult(true));
    }

}
