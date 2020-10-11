package com.meifute.nm.others.business.youxin.service.impl;

import com.alibaba.fastjson.JSON;
import com.meifute.nm.others.business.youxin.config.Config;
import com.meifute.nm.others.business.youxin.entity.*;
import com.meifute.nm.others.business.youxin.enums.YouXinResponseCodeEnum;
import com.meifute.nm.others.business.youxin.service.IMsService;
import com.meifute.nm.others.feignclient.AgentFeignClient;
import com.meifute.nm.others.feignclient.OrderFeignClient;
import com.meifute.nm.others.feignclient.UserFeignClient;
import com.meifute.nm.others.utils.SignUtil;
import com.meifute.nm.otherscommon.utils.ObjectUtils;
import com.meifute.nm.othersserver.domain.dto.MallUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @ClassName: MsService
 * @Description:
 * @Author: Chen
 * @Date: 2020/7/16 10:47
 * @Version: 1.0
 */

@Slf4j
@Service
public class MsService implements IMsService {

    @Autowired
    UserFeignClient userFeignClient;
    @Autowired
    AgentFeignClient agentFeignClient;
    @Autowired
    OrderFeignClient orderFeignClient;

    @Override
    public MsAgentDTO getMsAgent(String seqid, String phone, String number, String verifyType, String sign) {
        log.info("getMsAgent=====>>>param seqid:{},phone:{},number:{},verifyType:{},sign:{}",seqid,phone,number,verifyType,sign);
        MsAgentDTO msAgentDTO = new MsAgentDTO();
        if (ObjectUtils.isNullOrEmpty(seqid) || ObjectUtils.isNullOrEmpty(phone)|| ObjectUtils.isNullOrEmpty(sign)){
            msAgentDTO.setCode(YouXinResponseCodeEnum.RESPONSE_CODE_2001.getCode());
            msAgentDTO.setMessage(YouXinResponseCodeEnum.RESPONSE_CODE_2001.getDesc());
            return msAgentDTO;
        }
        String requestDate = "seqid="+seqid;
        if (ObjectUtils.isNotNullAndEmpty(phone)) requestDate += "&phone="+phone;
        if (ObjectUtils.isNotNullAndEmpty(number)) requestDate += "&number="+number;
        if (ObjectUtils.isNotNullAndEmpty(verifyType)) requestDate += "&verifyType="+verifyType;
        log.info("getMsAgent=====>>> requestDate:{}",requestDate);
        boolean verifySign = SignUtil.verifySign(Config.YOUXIN_PUBLIC_KEY, requestDate, sign);
        if (!verifySign){
            msAgentDTO.setCode(YouXinResponseCodeEnum.RESPONSE_CODE_2000.getCode());
            msAgentDTO.setMessage(YouXinResponseCodeEnum.RESPONSE_CODE_2000.getDesc());
            return msAgentDTO;
        }
        //查询用户信息
        try {
            MallUser mallUser = userFeignClient.getUserByPhoneYouXin(phone);
            if (ObjectUtils.isNotNullAndEmpty(mallUser)){
                msAgentDTO.setCode(YouXinResponseCodeEnum.RESPONSE_CODE_0000.getCode());
                msAgentDTO.setMessage(YouXinResponseCodeEnum.RESPONSE_CODE_0000.getDesc());
                msAgentDTO.setPhone(mallUser.getPhone());
                msAgentDTO.setName(mallUser.getNickName());
                msAgentDTO.setLevel(mallUser.getRoleId());
                msAgentDTO.setIsTop("4".equals(mallUser.getRoleId())?"1":"0");
            }else{
                msAgentDTO.setCode(YouXinResponseCodeEnum.RESPONSE_CODE_3000.getCode());
                msAgentDTO.setMessage(YouXinResponseCodeEnum.RESPONSE_CODE_3000.getDesc());
            }
        }catch (Exception e){
            msAgentDTO.setCode(YouXinResponseCodeEnum.RESPONSE_CODE_2002.getCode());
            msAgentDTO.setMessage(YouXinResponseCodeEnum.RESPONSE_CODE_2002.getDesc());
        }

        try {

        }catch (Exception e){
            msAgentDTO.setCode(YouXinResponseCodeEnum.RESPONSE_CODE_2002.getCode());
            msAgentDTO.setMessage(YouXinResponseCodeEnum.RESPONSE_CODE_2002.getDesc());
        }

        log.info("cancelOrder=====>>>seqid:{},result:{}",seqid,JSON.toJSONString(msAgentDTO));
        return msAgentDTO;
    }

    @Override
    public CloudStockDTO agentCloudStock(String seqid, String itemCode, String childIdentify, String wareCode, String sign) {
        log.info("agentCloudStock=====>>>params seqid:{},itemCode:{},childIdentify:{},wareCode:{}",seqid,itemCode,childIdentify,wareCode);
        CloudStockDTO cloudStockDTO = new CloudStockDTO();
        if (ObjectUtils.isNullOrEmpty(seqid) || ObjectUtils.isNullOrEmpty(childIdentify) || ObjectUtils.isNullOrEmpty(sign)){
            cloudStockDTO.setCode(YouXinResponseCodeEnum.RESPONSE_CODE_2001.getCode());
            cloudStockDTO.setMessage(YouXinResponseCodeEnum.RESPONSE_CODE_2001.getDesc());
            return cloudStockDTO;
        }
        //TODO 校验签名
        String requestDate = "seqid="+seqid;
        if (ObjectUtils.isNotNullAndEmpty(itemCode)) requestDate += "&itemCode="+itemCode;
        if (ObjectUtils.isNotNullAndEmpty(childIdentify)) requestDate += "&childIdentify="+childIdentify;
        if (ObjectUtils.isNotNullAndEmpty(wareCode)) requestDate += "&wareCode="+wareCode;

        boolean verifySign = SignUtil.verifySign(Config.YOUXIN_PUBLIC_KEY, requestDate, sign);
        if (!verifySign){
            cloudStockDTO.setCode(YouXinResponseCodeEnum.RESPONSE_CODE_2000.getCode());
            cloudStockDTO.setMessage(YouXinResponseCodeEnum.RESPONSE_CODE_2000.getDesc());
            return cloudStockDTO;
        }

        try {
            MallUser mallUser = userFeignClient.getUserByPhoneYouXin(childIdentify);
            if (ObjectUtils.isNullOrEmpty(mallUser)){
                cloudStockDTO.setCode(YouXinResponseCodeEnum.RESPONSE_CODE_3000.getCode());
                cloudStockDTO.setMessage(YouXinResponseCodeEnum.RESPONSE_CODE_3000.getDesc());
                return cloudStockDTO;
            }

            List<MallCloudStockDTO> cloudStockInfo = agentFeignClient.getCloudStockInfo(mallUser.getId());

            //itemCode  就是skuCode
            List<CloudStockInfo> cloudStockInfos = new ArrayList<>();
            for (MallCloudStockDTO mallCloudStockDTO : cloudStockInfo) {
                if (ObjectUtils.isNotNullAndEmpty(itemCode) && itemCode.equals(mallCloudStockDTO.getSkuCode())){
                    cloudStockInfos.add(CloudStockInfo.builder()
                            .childIdentify(childIdentify)
                            .itemCode(itemCode)
                            .wareCode(itemCode)
                            .availableCnt(mallCloudStockDTO.getStock().toString())
                            .totalCnt(mallCloudStockDTO.getStock().toString())
                            .build()
                    );
                }else{
                    cloudStockInfos.add(CloudStockInfo.builder()
                            .childIdentify(childIdentify)
                            .itemCode(mallCloudStockDTO.getTransportGoodsNo())
                            .wareCode(mallCloudStockDTO.getTransportGoodsNo())
                            .availableCnt(mallCloudStockDTO.getStock().toString())
                            .totalCnt(mallCloudStockDTO.getStock().toString())
                            .build()
                    );
                }
            }
            cloudStockDTO.setCode(YouXinResponseCodeEnum.RESPONSE_CODE_0000.getCode());
            cloudStockDTO.setMessage(YouXinResponseCodeEnum.RESPONSE_CODE_0000.getDesc());
            cloudStockDTO.setData(cloudStockInfos);
        }catch (Exception e){
            cloudStockDTO.setCode(YouXinResponseCodeEnum.RESPONSE_CODE_2002.getCode());
            cloudStockDTO.setMessage(YouXinResponseCodeEnum.RESPONSE_CODE_2002.getDesc());
        }
        log.info("cancelOrder=====>>>result:{}",JSON.toJSONString(cloudStockDTO));
        return cloudStockDTO;
    }

    @Override
    public BooleanResultDTO createOrder(OrderCreateVO orderCreateVO) {
        log.info("createOrder=====>>>params orderCreateVO:{}", JSON.toJSONString(orderCreateVO));
        BooleanResultDTO booleanResultDTO = new BooleanResultDTO();
        String sign = orderCreateVO.getSign();
        String seqid = orderCreateVO.getSeqid();
        String orderCode = orderCreateVO.getOrderCode();
        SellerInfoVO sellInfo = orderCreateVO.getSellInfo();
        String buyerPhone = sellInfo.getBuyerPhone();
        List<OrderItemVO> orderItem = orderCreateVO.getOrderItem();

        if (ObjectUtils.isNullOrEmpty(seqid)
                || ObjectUtils.isNullOrEmpty(orderCode)
                || ObjectUtils.isNullOrEmpty(buyerPhone)
                || ObjectUtils.isNullOrEmpty(orderItem)
                || ObjectUtils.isNullOrEmpty(sign)){
            booleanResultDTO.setCode(YouXinResponseCodeEnum.RESPONSE_CODE_2001.getCode());
            booleanResultDTO.setMessage(YouXinResponseCodeEnum.RESPONSE_CODE_2001.getDesc());
            return booleanResultDTO;
        }
        //TODO 校验签名
        String requestDate = "seqid="+seqid;
        if (ObjectUtils.isNotNullAndEmpty(orderCode)) requestDate += "&orderCode="+orderCode;
        if (ObjectUtils.isNotNullAndEmpty(buyerPhone)) requestDate += "&buyerPhone="+buyerPhone;

        boolean verifySign = SignUtil.verifySign(Config.YOUXIN_PUBLIC_KEY, requestDate, sign);
        if (!verifySign){
            booleanResultDTO.setCode(YouXinResponseCodeEnum.RESPONSE_CODE_2000.getCode());
            booleanResultDTO.setMessage(YouXinResponseCodeEnum.RESPONSE_CODE_2000.getDesc());
            return booleanResultDTO;
        }

        try {
            MallUser mallUser = userFeignClient.getUserByPhoneYouXin(buyerPhone);
            if (ObjectUtils.isNullOrEmpty(mallUser)){
                booleanResultDTO.setCode(YouXinResponseCodeEnum.RESPONSE_CODE_3000.getCode());
                booleanResultDTO.setMessage(YouXinResponseCodeEnum.RESPONSE_CODE_3000.getDesc());
                return booleanResultDTO;
            }

            List<MallRegulateInfo> mallRegulateInfos = orderFeignClient.checkRegulationInfoExist(orderCode);
            if (!ObjectUtils.isNullOrEmpty(mallRegulateInfos)){
                booleanResultDTO.setCode(YouXinResponseCodeEnum.RESPONSE_CODE_5014.getCode());
                booleanResultDTO.setMessage(YouXinResponseCodeEnum.RESPONSE_CODE_5014.getDesc());
                return booleanResultDTO;
            }

            ArrayList<CloudStockAdjustDetailDTO> detailDTOS = new ArrayList<>();
            for (OrderItemVO orderItemVO : orderItem) {
                String subOrderCode = orderItemVO.getSubOrderCode();
                String itemCode = orderItemVO.getItemCode();
                Integer num = orderItemVO.getNum();
                if (ObjectUtils.isNullOrEmpty(subOrderCode)
                        || ObjectUtils.isNullOrEmpty(itemCode)
                        || ObjectUtils.isNullOrEmpty(num)
                        || num <= 0 ){
                    booleanResultDTO.setCode(YouXinResponseCodeEnum.RESPONSE_CODE_2001.getCode());
                    booleanResultDTO.setMessage(YouXinResponseCodeEnum.RESPONSE_CODE_2001.getDesc());
                    return booleanResultDTO;
                }
                detailDTOS.add(CloudStockAdjustDetailDTO.builder().skuCode(itemCode).subOrderCode(subOrderCode).quantity(num).build());
            }
            log.info("mallUser:{},detailDTOS:{}",JSON.toJSONString(mallUser),JSON.toJSONString(detailDTOS));
            Boolean ret = agentFeignClient.cloudStockInfoBatchAddOrSub(CloudStockAdjustDTO.builder().userId(mallUser.getId()).orderId(orderCode).detailDTOS(detailDTOS).build());
            if (ret){
                booleanResultDTO.setCode(YouXinResponseCodeEnum.RESPONSE_CODE_0000.getCode());
                booleanResultDTO.setMessage(YouXinResponseCodeEnum.RESPONSE_CODE_0000.getDesc());
            }else{
                booleanResultDTO.setCode(YouXinResponseCodeEnum.RESPONSE_CODE_2002.getCode());
                booleanResultDTO.setMessage(YouXinResponseCodeEnum.RESPONSE_CODE_2002.getDesc());
            }
        }catch (Exception e){
            booleanResultDTO.setCode(YouXinResponseCodeEnum.RESPONSE_CODE_2002.getCode());
            booleanResultDTO.setMessage(YouXinResponseCodeEnum.RESPONSE_CODE_2002.getDesc());
        }
        log.info("cancelOrder=====>>>result:{}",JSON.toJSONString(booleanResultDTO));
        return booleanResultDTO;
    }

    @Override
    public BooleanResultDTO cancelOrder(String seqid, String orderCode, String subOrderCode, String reason, String sign) {

        log.info("cancelOrder=====>>>param seqid:{},orderCode:{},subOrderCode:{},reason:{}",seqid,orderCode,subOrderCode,reason);
        BooleanResultDTO booleanResultDTO = new BooleanResultDTO();
        if (ObjectUtils.isNullOrEmpty(seqid)
                || ObjectUtils.isNullOrEmpty(orderCode)
                || ObjectUtils.isNullOrEmpty(sign)){
            booleanResultDTO.setCode(YouXinResponseCodeEnum.RESPONSE_CODE_2001.getCode());
            booleanResultDTO.setMessage(YouXinResponseCodeEnum.RESPONSE_CODE_2001.getDesc());
            return booleanResultDTO;
        }
        //TODO 校验签名
        String requestDate = "seqid="+seqid;
        if (ObjectUtils.isNotNullAndEmpty(orderCode)) requestDate += "&orderCode="+orderCode;

        boolean verifySign = SignUtil.verifySign(Config.YOUXIN_PUBLIC_KEY, requestDate, sign);
        if (!verifySign){
            booleanResultDTO.setCode(YouXinResponseCodeEnum.RESPONSE_CODE_2000.getCode());
            booleanResultDTO.setMessage(YouXinResponseCodeEnum.RESPONSE_CODE_2000.getDesc());
            return booleanResultDTO;
        }

        //查询相应的云调剂单
        try {
            List<MallRegulateInfo> mallRegulateInfos = orderFeignClient.checkRegulationInfoExist(orderCode);
            if (ObjectUtils.isNullOrEmpty(mallRegulateInfos)){
                booleanResultDTO.setCode(YouXinResponseCodeEnum.RESPONSE_CODE_5010.getCode());
                booleanResultDTO.setMessage(YouXinResponseCodeEnum.RESPONSE_CODE_5010.getDesc());
                return booleanResultDTO;
            }
            for (MallRegulateInfo mallRegulateInfo : mallRegulateInfos) {
                if ("0".equals(mallRegulateInfo.getRegulateType())){
                    booleanResultDTO.setCode(YouXinResponseCodeEnum.RESPONSE_CODE_5013.getCode());
                    booleanResultDTO.setMessage(YouXinResponseCodeEnum.RESPONSE_CODE_5013.getDesc());
                    return booleanResultDTO;
                }
            }

            Boolean ret = agentFeignClient.cancelOrderAndReturnCloudStock(orderCode,subOrderCode,reason);
            if (ret){
                booleanResultDTO.setCode(YouXinResponseCodeEnum.RESPONSE_CODE_0000.getCode());
                booleanResultDTO.setMessage(YouXinResponseCodeEnum.RESPONSE_CODE_0000.getDesc());
            }else{
                booleanResultDTO.setCode(YouXinResponseCodeEnum.RESPONSE_CODE_2002.getCode());
                booleanResultDTO.setMessage(YouXinResponseCodeEnum.RESPONSE_CODE_2002.getDesc());
            }
        }catch (Exception e){
            booleanResultDTO.setCode(YouXinResponseCodeEnum.RESPONSE_CODE_2002.getCode());
            booleanResultDTO.setMessage(YouXinResponseCodeEnum.RESPONSE_CODE_2002.getDesc());
        }

        log.info("cancelOrder=====>>>result:{}",JSON.toJSONString(booleanResultDTO));
        return booleanResultDTO;
    }

    private Boolean checkParam(String... params) {
        return Arrays.stream(params).anyMatch(q -> ObjectUtils.isNullOrEmpty(q));
    }


}