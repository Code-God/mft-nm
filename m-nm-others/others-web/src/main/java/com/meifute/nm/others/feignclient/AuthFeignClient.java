package com.meifute.nm.others.feignclient;

import com.meifute.nm.others.config.feignconf.FeignSupportConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Map;

/**
 * @Classname AuthFeignClient
 * @Description
 * @Date 2020-07-08 14:01
 * @Created by MR. Xb.Wu
 */
@FeignClient(name = "mall-auth", configuration = FeignSupportConfig.class)
public interface AuthFeignClient {

    @GetMapping("/current/user/v2")
    Map<String, Object> getCurrentUsers();
}
