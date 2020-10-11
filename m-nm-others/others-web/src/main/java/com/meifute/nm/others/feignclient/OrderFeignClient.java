package com.meifute.nm.others.feignclient;


import com.meifute.nm.others.business.youxin.entity.MallRegulateInfo;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @Classname UserFeignClient
 * @Description feign
 * @Date 2020-07-07 10:40
 * @Created by MR. Xb.Wu
 */
@FeignClient("mall-order")
@RequestMapping(value = "/api/implement/order", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
public interface OrderFeignClient {

    @GetMapping("/regulate/id")
    List<MallRegulateInfo> checkRegulationInfoExist(@RequestParam("id") String id);

}
