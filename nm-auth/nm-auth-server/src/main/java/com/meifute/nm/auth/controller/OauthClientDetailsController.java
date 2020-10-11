package com.meifute.nm.auth.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.meifute.nm.auth.base.BaseController;
import com.meifute.nm.auth.base.MallResponse;
import com.meifute.nm.auth.base.ResultResponse;
import com.meifute.nm.auth.entity.MallUser;
import com.meifute.nm.auth.entity.OauthClientDetails;
import com.meifute.nm.auth.entity.dto.OauthClientDetailsDTO;
import com.meifute.nm.auth.entity.vo.OauthClientDetailsAddVO;
import com.meifute.nm.auth.entity.vo.OauthClientDetailsQueryVO;
import com.meifute.nm.auth.entity.vo.OauthClientDetailsUpdateVO;
import com.meifute.nm.auth.service.IMallUserService;
import com.meifute.nm.auth.service.IOauthClientDetailsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 接入客户端信息 前端控制器
 * </p>
 *
 * @author chen
 * @since 2020-08-17
 */
@RestController
@RequestMapping("/auth/oauth/client/details")
@Api(tags = "客户端信息")
public class OauthClientDetailsController extends BaseController {

    @Autowired
    IOauthClientDetailsService oauthClientDetailsService;

    @ApiOperation(value = "新增客户端", notes = "新增客户端")
    @PostMapping(value = "/add")
    public ResponseEntity<MallResponse<Boolean>> addClient(@RequestBody OauthClientDetailsAddVO oauthClientDetailsAddVO) {
        return ResponseEntity.ok(new ResultResponse<Boolean>().successResult(oauthClientDetailsService.addClient(oauthClientDetailsAddVO)));
    }

    @ApiOperation(value = "更新客户端", notes = "更新客户端")
    @PostMapping(value = "/update")
    public ResponseEntity<MallResponse<Boolean>> updateClient(@RequestBody OauthClientDetailsUpdateVO oauthClientDetailsUpdateVO) {
        return ResponseEntity.ok(new ResultResponse<Boolean>().successResult(oauthClientDetailsService.updateClient(oauthClientDetailsUpdateVO)));
    }

    @ApiOperation(value = "删除客户端", notes = "删除客户端")
    @DeleteMapping(value = "/remove")
    public ResponseEntity<MallResponse<Boolean>> removeClient(@RequestParam String clientId) {
        return ResponseEntity.ok(new ResultResponse<Boolean>().successResult(oauthClientDetailsService.removeClient(clientId)));
    }

    @ApiOperation(value = "客户端列表", notes = "客户端列表")
    @PostMapping(value = "/page")
    public ResponseEntity<MallResponse<Page<OauthClientDetailsDTO>>> pageClient(@RequestBody OauthClientDetailsQueryVO oauthClientDetailsQueryVO) {
        return ResponseEntity.ok(new ResultResponse<Page<OauthClientDetailsDTO>>().successResult(oauthClientDetailsService.pageClient(oauthClientDetailsQueryVO)));
    }

    @ApiOperation(value = "客户端详情", notes = "客户端详情")
    @GetMapping(value = "/detail")
    public ResponseEntity<MallResponse<OauthClientDetailsDTO>> detailClient(@RequestParam String clientId) {
        return ResponseEntity.ok(new ResultResponse<OauthClientDetailsDTO>().successResult(oauthClientDetailsService.detailClient(clientId)));
    }

}
