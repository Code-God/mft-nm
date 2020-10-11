package com.meifute.external.aliyun.realname.certification;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.cloudauth.model.v20180807.GetMaterialsRequest;
import com.aliyuncs.cloudauth.model.v20180807.GetMaterialsResponse;
import com.aliyuncs.cloudauth.model.v20180807.GetVerifyTokenRequest;
import com.aliyuncs.cloudauth.model.v20180807.GetVerifyTokenResponse;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;

import java.util.UUID;

/**
 * @Classname RealNameAuth
 * @Description
 * @Date 2020-07-23 16:15
 * @Created by MR. Xb.Wu
 */
@Slf4j
public class RealNameAuth {

    private String regionId;
    private String accessKeyId;
    private String secret;
    private IAcsClient client;

    public RealNameAuth(String regionId, String accessKeyId, String secret) {
        this.regionId = regionId;
        this.accessKeyId = accessKeyId;
        this.secret = secret;
        this.client = new DefaultAcsClient(DefaultProfile.getProfile(this.regionId, this.accessKeyId, this.secret));
    }


    /**
     * 获取认证token
     * @return
     */
    public RealNameTokenResponse getVerifyToken(String biz) {
        //认证ID, 由使用方指定, 发起不同的认证任务需要更换不同的认证ID
        String ticketId = UUID.randomUUID().toString();
        GetVerifyTokenRequest getVerifyTokenRequest = new GetVerifyTokenRequest();
        getVerifyTokenRequest.setBiz(biz);
        getVerifyTokenRequest.setTicketId(ticketId);

        RealNameTokenResponse result = null;
        try {
            GetVerifyTokenResponse response = client.getAcsResponse(getVerifyTokenRequest);
            String token = response.getData().getVerifyToken().getToken();
            result = new RealNameTokenResponse();
            result.setToken(token);
            result.setBiz(biz);
            result.setTicketId(ticketId);
        } catch (Exception e) {
            log.error("getVerifyToken Error Message:{}", e.getMessage());
        }
        return result;
    }


    /**
     * 获取实名认证结果
     * @param biz
     * @param ticketId
     * @return
     */
    public RealNameAuthResponse getRealNameAuthResult(String biz, String ticketId) {
        RealNameAuthResponse dto = new RealNameAuthResponse();
        GetMaterialsRequest getMaterialsRequest = new GetMaterialsRequest();
        getMaterialsRequest.setBiz(biz);
        getMaterialsRequest.setTicketId(ticketId);
        try {
            GetMaterialsResponse materialsResponse = client.getAcsResponse(getMaterialsRequest);
            if (StringUtils.isEmpty(materialsResponse.getData().getIdentificationNumber())) {
                log.info("getRealNameAuthResult Fail....");
                return dto;
            }
            dto.setIdCardBackPic(materialsResponse.getData().getIdCardBackPic());
            dto.setIdCardFrontPic(materialsResponse.getData().getIdCardFrontPic());
            dto.setName(materialsResponse.getData().getName());
            dto.setIdCardExpiry(materialsResponse.getData().getIdCardExpiry());
            dto.setIdCardStartDate(materialsResponse.getData().getIdCardStartDate());
            dto.setUserPhoto(materialsResponse.getData().getFacePic());
            dto.setSex(materialsResponse.getData().getSex());
            dto.setAddress(materialsResponse.getData().getAddress());
            dto.setIdentificationNumber(materialsResponse.getData().getIdentificationNumber());
        } catch (Exception e) {
            log.error("getRealNameAuthResult Error Message: {}", e.getMessage());
        }
        return dto;
    }
}
