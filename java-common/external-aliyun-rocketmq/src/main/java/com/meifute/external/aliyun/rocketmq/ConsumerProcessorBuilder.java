package com.meifute.external.aliyun.rocketmq;

import com.aliyun.openservices.ons.api.MessageListener;
import com.aliyun.openservices.ons.api.bean.Subscription;

import java.util.HashMap;

/**
 * @author wzeng
 * @date 2020/7/20
 * @Description
 */
public class ConsumerProcessorBuilder {

    HashMap<Subscription, MessageListener> map=new HashMap<Subscription, MessageListener>();

    public void with(String topic,String express,MessageListener listener){
        Subscription subscription =new Subscription();
        subscription.setTopic(topic);
        subscription.setExpression(express);
        map.put(subscription,listener);
    }

    public HashMap<Subscription, MessageListener> build(){
        return map;
    }
}
