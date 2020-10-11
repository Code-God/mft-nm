package com.meifute.nm.others.business.youxin.service;

import com.meifute.nm.others.business.youxin.entity.BooleanResultDTO;
import com.meifute.nm.others.business.youxin.entity.CloudStockDTO;
import com.meifute.nm.others.business.youxin.entity.MsAgentDTO;
import com.meifute.nm.others.business.youxin.entity.OrderCreateVO;

/**
 * @ClassName:IMsService
 * @Description:
 * @Author:Chen
 * @Date:2020/7/16 10:48
 * @Version:1.0
 */
public interface IMsService {
    MsAgentDTO getMsAgent(String seqid, String phone, String number, String verifyType, String sign);

    CloudStockDTO agentCloudStock(String seqid, String itemCode, String childIdentify, String wareCode, String sign);

    BooleanResultDTO createOrder(OrderCreateVO orderCreateVO);

    BooleanResultDTO cancelOrder(String seqid, String orderCode, String subOrderCode, String reason, String sign);
}
