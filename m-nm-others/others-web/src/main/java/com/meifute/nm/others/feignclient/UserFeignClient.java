package com.meifute.nm.others.feignclient;


import com.meifute.nm.othersserver.domain.dto.AgentNumber;
import com.meifute.nm.othersserver.domain.dto.MallUser;
import com.meifute.nm.othersserver.domain.dto.MallUserAccountDto;
import io.swagger.annotations.ApiOperation;
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
@FeignClient("mall-user")
@RequestMapping(value = "/api/implement/user", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
public interface UserFeignClient {

    @GetMapping("/get/user/by/phone")
    MallUser getUserByPhone(@RequestParam("phone") String phone);

    @GetMapping("/get/user/by/phone/youxin")
    MallUser getUserByPhoneYouXin(@RequestParam("phone") String phone);

    @GetMapping("/get/user/by/id")
    MallUser getUserById(@RequestParam(value = "id") String id);

    @PostMapping(value = "/next/super/agent/number/userids")
    List<AgentNumber> queryNextSuperAgents(@RequestBody List<String> userIds);

    @PostMapping(value = "/query/by/ids")
    List<MallUser> queryUsersByIds(@RequestBody List<String> userIds);

    @PostMapping("/query/users/by/phones")
    List<MallUser> queryUsersByPhones(@RequestBody List<String> phones);

    @GetMapping({"/get/user/account/info"})
    @ApiOperation("userId查询账户信息")
    MallUserAccountDto getAccountInfo(@RequestParam("userId") String userId);

    @ApiOperation(value = "根据昵称获取用户信息")
    @GetMapping("/get/user/by/nick/name")
    List<MallUser> getUserByNickName(@RequestParam(value = "nickName", required = false) String nickName);

    @ApiOperation(value = "通用条件查询用户")
    @GetMapping("/get/user/by/input")
    List<MallUser> getUserByInput(
            @RequestParam(value = "nickName", required = false) String nickName,
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "phone", required = false) String phone,
            @RequestParam(value = "refNickName", required = false) String refNickName,
            @RequestParam(value = "refNickPhone", required = false) String refNickPhone

    );


}
