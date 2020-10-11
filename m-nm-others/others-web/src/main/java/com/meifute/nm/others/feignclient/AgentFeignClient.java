package com.meifute.nm.others.feignclient;


import com.meifute.nm.others.business.youxin.entity.CloudStockAdjustDTO;
import com.meifute.nm.others.business.youxin.entity.MallCloudStockDTO;
import com.meifute.nm.othersserver.domain.dto.*;
import com.meifute.nm.othersserver.domain.vo.QueryNoveltyAgent;
import io.swagger.annotations.ApiModelProperty;
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
@FeignClient("mall-agent")
@RequestMapping(value = "/api/implement/agent", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
public interface AgentFeignClient {

    @GetMapping("/get/cloud/stock")
    List<MallCloudStockDTO> getCloudStockInfo(@RequestParam("userId") String userId);

    @PostMapping("/cloud/stock/batch/addOrSub")
    Boolean cloudStockInfoBatchAddOrSub(@RequestBody CloudStockAdjustDTO stockAdjustDTO);

    @GetMapping("/cloud/stock/return")
    Boolean cancelOrderAndReturnCloudStock(@RequestParam("orderCode") String orderCode, @RequestParam(value = "subOrderCode",required = false) String subOrderCode, @RequestParam(value = "reason",required = false) String reason);

    @ApiOperation(value = "查询代理所属公司和最后升级时间", notes = "查询代理所属公司和最后升级时间")
    @PostMapping("/get/company/and/upgradeDate")
    List<MallAgent> getCompanyAndUpgradeDate(@RequestBody List<String> userIds);

    @PostMapping("/get/back/agent/by/phones")
    List<MallBackAgent> getBackAgentByPhones(@RequestBody List<String> phones);

    @ApiModelProperty("查询新招总代，排除有退代记录的")
    @PostMapping("/get/novelty/agents/by/userid")
    QueryNoveltyAgentDto getNoveltyAgents(@RequestBody QueryNoveltyAgent queryNoveltyAgent);

    @ApiModelProperty("根据用户id获取公司信息")
    @PostMapping("/get/company/by/userids")
    List<MallBranchOffice> queryCompanyByUserIds(@RequestBody List<String> userIds);

    @ApiModelProperty("分页获取信息新招总代信息（不含有已退代记录的）")
    @PostMapping("/get/novelty/agents/page")
    NoveltyAgentDtoPage getNoveltyAgentsPage(@RequestBody QueryNoveltyAgentInput queryNoveltyAgentInput);

    @ApiOperation(value = "根据userId查代理信息", notes = "根据userId查代理信息")
    @GetMapping("/get/by/userid")
    MallAgent getAgentByUserId(@RequestParam("userId") String userId);
}
