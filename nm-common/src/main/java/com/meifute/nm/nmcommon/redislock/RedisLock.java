package com.meifute.nm.nmcommon.redislock;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Classname RedisLock
 * @Description redis 分布式锁
 * @Date 2020-06-01 15:48
 * @Created by MR. Xb.Wu
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface RedisLock {

    /**
     * 要加锁的字段
     * @return
     */
    String key() default "";

    /**
     * 是否锁住整个方法
     * @return
     */
    boolean fixed() default false;

    /**
     * 自动过期时间 默认10s
     * @return
     */
    long expireTime() default 10000L;

    /**
     * 防止重复请求
     * @return
     */
    boolean repeat() default false;

}
