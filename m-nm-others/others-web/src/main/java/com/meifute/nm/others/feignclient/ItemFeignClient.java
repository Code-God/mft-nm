package com.meifute.nm.others.feignclient;


import com.meifute.nm.othersserver.domain.dto.AgentNumber;
import com.meifute.nm.othersserver.domain.dto.MallUser;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Classname UserFeignClient
 * @Description feign
 * @Date 2020-07-07 10:40
 * @Created by MR. Xb.Wu
 */
@FeignClient("mall-item")
@RequestMapping(value = "/api/implement/item", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
public interface ItemFeignClient {

}
