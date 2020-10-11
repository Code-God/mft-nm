package com.meifute.external.aliyun.rocketmq;

import com.aliyun.openservices.ons.api.*;
import com.aliyun.openservices.ons.api.bean.ConsumerBean;
import com.aliyun.openservices.ons.api.bean.ProducerBean;
import com.aliyun.openservices.ons.api.bean.Subscription;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author wzeng
 * @date 2020/7/20
 * @Description
 */
class AliyunRocketMQFactoryTest {



    @Test
    void createProducerBean() {
        AliyunRocketMQFactory factory = new AliyunRocketMQFactory("key","secret","nameservers");
        ProducerBean producerBean = factory.createProducerBean("p1");
        Properties p= producerBean.getProperties();
        Assertions.assertEquals("key",p.get(PropertyKeyConst.AccessKey));
        assertEquals("secret",p.get(PropertyKeyConst.SecretKey));
        assertEquals("nameservers",p.getProperty(PropertyKeyConst.NAMESRV_ADDR));
        assertEquals("p1",p.getProperty(PropertyKeyConst.GROUP_ID));

    }

    @Test
    void createConsumerBean() {
        AliyunRocketMQFactory factory = new AliyunRocketMQFactory("key","secret","nameservers");
        ConsumerProcessorBuilder processorBuilder = new ConsumerProcessorBuilder();
        MessageListener listener =new MessageListener() {
            @Override
            public Action consume(Message message, ConsumeContext consumeContext) {
                return null;
            }
        };
        processorBuilder.with("topic","tags",listener);
        ConsumerBean consumerBean = factory.createConsumerBean("p1","topic","tags",processorBuilder.build());
        Properties p= consumerBean.getProperties();
        Assertions.assertEquals("key",p.get(PropertyKeyConst.AccessKey));
        assertEquals("secret",p.get(PropertyKeyConst.SecretKey));
        assertEquals("nameservers",p.getProperty(PropertyKeyConst.NAMESRV_ADDR));
        assertEquals("p1",p.getProperty(PropertyKeyConst.GROUP_ID));

        Map<Subscription, MessageListener> processors = consumerBean.getSubscriptionTable();

        assertEquals(1,processors.size());
        Map.Entry<Subscription,MessageListener> entry=processors.entrySet().iterator().next();
        assertEquals("topic",entry.getKey().getTopic());
        assertEquals("tags",entry.getKey().getExpression());
        assertTrue(listener== entry.getValue());

    }
}