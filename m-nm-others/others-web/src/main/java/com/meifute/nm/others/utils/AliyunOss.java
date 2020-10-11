package com.meifute.nm.others.utils;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.common.utils.BinaryUtil;
import com.aliyun.oss.model.MatchMode;
import com.aliyun.oss.model.PolicyConditions;
import com.aliyun.oss.model.PutObjectRequest;
import com.aliyun.oss.model.PutObjectResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @Classname AliyunOss
 * @Description
 * @Date 2020-07-07 11:58
 * @Created by MR. Xb.Wu
 */
@Slf4j
@Component
public class AliyunOss {

    private static String endpoint;
    private static String bucketUrl;
    private static String accessKeyId;
    private static String accessKeySecret;
    private static String bucketName;
    private static String fileDir;

    private static OSS ossClient;

    @Value("${oss.endpoint}")
    private void setEndpoint(String endpoint) {
        AliyunOss.endpoint = endpoint;
    }

    @Value("${oss.bucketUrl}")
    private void setBucketUrl(String bucketUrl) {
        AliyunOss.bucketUrl = bucketUrl;
    }

    @Value("${oss.accessKeyId}")
    private void setAccessKeyId(String accessKeyId) {
        AliyunOss.accessKeyId = accessKeyId;
    }

    @Value("${oss.accessKeySecret}")
    private void setAccessKeySecret(String accessKeySecret) {
        AliyunOss.accessKeySecret = accessKeySecret;
    }

    @Value("${oss.bucketName}")
    private void setBucketName(String bucketName) {
        AliyunOss.bucketName = bucketName;
    }

    @Value("${oss.fileDir}")
    private void setFileDir(String fileDir) {
        AliyunOss.fileDir = fileDir;
    }

    @PostConstruct
    public void init() {
        ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
    }

    public String ossUpload(MultipartFile file, String fileKey) throws IOException {
        PutObjectResult res = ossClient.putObject(bucketName, fileKey, new ByteArrayInputStream(file.getBytes()));
        if (res != null) {
            return fileKey;
        }
        return null;
    }

    public String ossUploadByteStream(String fileKey, byte[] bytes) {
        log.info("endpoint:{},bucketUrl:{},accessKeyId:{},accessKeySecret:{},bucketName:{}", endpoint, bucketUrl, accessKeyId, accessKeySecret, bucketName);
        PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, fileDir + fileKey, new ByteArrayInputStream(bytes));

        PutObjectResult res = ossClient.putObject(putObjectRequest);
        if (res != null) {
            return getImgUrl(fileKey, ossClient);
        }

        return null;
    }


    public Map<String, String> getPolicy(String dir) throws UnsupportedEncodingException {
        Map<String, String> respMap = new LinkedHashMap<String, String>();

        PolicyConditions policyConds = new PolicyConditions();
        policyConds.addConditionItem(PolicyConditions.COND_CONTENT_LENGTH_RANGE, 0, 1048576000);
        policyConds.addConditionItem(MatchMode.StartWith, PolicyConditions.COND_KEY, dir);

        long expireTime = 30;
        long expireEndTime = System.currentTimeMillis() + expireTime * 1000;
        Date expiration = new Date(expireEndTime);
        String postPolicy = ossClient.generatePostPolicy(expiration, policyConds);
        byte[] binaryData = postPolicy.getBytes("utf-8");
        String encodedPolicy = BinaryUtil.toBase64String(binaryData);
        String postSignature = ossClient.calculatePostSignature(postPolicy);

        respMap.put("accessid", accessKeyId);
        respMap.put("policy", encodedPolicy);
        respMap.put("signature", postSignature);
        respMap.put("dir", dir);
        respMap.put("fileName", UUID.randomUUID().toString());
        respMap.put("host", bucketUrl);
        respMap.put("expire", String.valueOf(expireEndTime / 1000));
        respMap.put("callback", "");

        return respMap;
    }

    public String getImgUrl(String fileUrl, OSS ossClient) {
        if (!StringUtils.isEmpty(fileUrl)) {
            String[] split = fileUrl.split("/");
            return this.getUrl(fileDir + split[split.length - 1], ossClient);
        }
        return null;
    }

    public String getUrl(String key, OSS ossClient) {
        // 设置URL过期时间为10年  3600l* 1000*24*365*10
        Date expiration = new Date(System.currentTimeMillis() + 3600L * 1000 * 24 * 365 * 10);
        // 生成URL
        URL url = ossClient.generatePresignedUrl(bucketName, key, expiration);
        if (url != null) {
            String head = (url.toString().split("\\?")[0]).split(":")[1];
            return "https:" + head;
        }
        return null;
    }

    public String getBucketUrl() {
        return bucketUrl;
    }
}
