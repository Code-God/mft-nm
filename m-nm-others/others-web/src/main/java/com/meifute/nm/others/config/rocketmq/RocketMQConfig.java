package com.meifute.nm.others.config.rocketmq;

import com.aliyun.openservices.ons.api.PropertyKeyConst;
import com.aliyun.openservices.ons.api.bean.ProducerBean;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

/**
 * @Classname Swagger2Config
 * @Description mq
 * @Date 2020-07-06 15:20
 * @Created by MR. Xb.Wu
 */
@Configuration
@Slf4j
@RefreshScope
@Data
public class RocketMQConfig {

    @Value("${mq_access_key_id}")
    public String accessKeyId;
    @Value("${mq_access_key_secret}")
    public String accessKeySecret;
    //超时时间
    @Value("${ali-sendMsgTimeoutMillis}")
    public String sendMsgTimeoutMillis;
    //实例地址
    @Value("${mall-ali-ons-addr}")
    private String mallAliOnsAddr;

    @Value("${training-enroll-topic}")
    private String trainingEnrollTopic;

    @Value("${training-enroll-gid}")
    private String trainingEnrollGid;

    public static final String TRAINING_ENROLL = "training_enroll";


    //短信营销提交审核单
    @Bean(initMethod = "start", destroyMethod = "shutdown")
    public ProducerBean trainingEnrollProducer() {
        return producer(trainingEnrollGid, mallAliOnsAddr);
    }


    //生产者
    private ProducerBean producer(String GID, String onsAddr) {
        ProducerBean producerBean = new ProducerBean();
        Properties properties = new Properties();
        properties.put(PropertyKeyConst.GROUP_ID, GID);
        properties.put(PropertyKeyConst.AccessKey, accessKeyId);
        properties.put(PropertyKeyConst.SecretKey, accessKeySecret);
        properties.put(PropertyKeyConst.SendMsgTimeoutMillis, sendMsgTimeoutMillis);
        properties.put(PropertyKeyConst.NAMESRV_ADDR, onsAddr);
        producerBean.setProperties(properties);
        return producerBean;
    }

}
