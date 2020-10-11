package com.meifute.external.aliyun.rocketmq;

import com.aliyun.openservices.ons.api.MessageListener;
import com.aliyun.openservices.ons.api.PropertyKeyConst;
import com.aliyun.openservices.ons.api.bean.ConsumerBean;
import com.aliyun.openservices.ons.api.bean.ProducerBean;
import com.aliyun.openservices.ons.api.bean.Subscription;

import java.util.Map;
import java.util.Properties;

/**
 * @author wzeng
 * @date 2020/7/20
 * @Description
 */
public class AliyunRocketMQFactory {
    String accessKey;
    String accessSecret;
    String nameServAddr;
    int timeOutMillis=3000;

    public AliyunRocketMQFactory(String accessKey,String accessSecret,String nameServAddr){
        this.accessKey = accessKey;
        this.accessSecret = accessSecret;
        this.nameServAddr = nameServAddr;
    }

    public ProducerBean createProducerBean(String groupId){
        ProducerBean producerBean = new ProducerBean();
        producerBean.setProperties(createAliyunRocketMQClientProperties(groupId));
        return producerBean;
    }

    /**
     *
     * @param groupId
     * @param processors 使用 {@link ConsumerProcessorBuilder}  建立 Processor。
     * @return
     */
    public ConsumerBean createConsumerBean(String groupId, Map<Subscription,MessageListener> processors){
        ConsumerBean consumerBean =new ConsumerBean();
        Properties properties = createAliyunRocketMQClientProperties(groupId);
        consumerBean.setSubscriptionTable(processors);
        consumerBean.setProperties(properties);
        return consumerBean;
    }

    private Properties createAliyunRocketMQClientProperties(String groupId){
        Properties properties = new Properties();
        // AccessKeyId 阿里云身份验证，在阿里云用户信息管理控制台获取。
        properties.put(PropertyKeyConst.AccessKey,accessKey);
        // AccessKeySecret 阿里云身份验证，在阿里云用户信息管理控制台获取。
        properties.put(PropertyKeyConst.SecretKey, accessSecret);
        //设置发送超时时间，单位毫秒。
        properties.setProperty(PropertyKeyConst.SendMsgTimeoutMillis,String.valueOf(timeOutMillis));
        // 设置 TCP 接入域名，进入控制台的实例详情页面的 TCP 协议客户端接入点区域查看。
        properties.put(PropertyKeyConst.NAMESRV_ADDR, nameServAddr);
        if(groupId!=null){
            properties.put(PropertyKeyConst.GROUP_ID,groupId);
        }
        return properties;

    }
}
