package com.meifute.nm.others.feignclient;

import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Classname AdminFeignClient
 * @Description
 * @Date 2020-07-08 15:16
 * @Created by MR. Xb.Wu
 */
@FeignClient("mall-admin")
public interface AdminFeignClient {

    @GetMapping("/api/implement/admin/get/by/userId")
    @ApiOperation(value = "根据userId查询adminCode")
    String getAdminCodeByUserId(@RequestParam("userId") String userId);
}
