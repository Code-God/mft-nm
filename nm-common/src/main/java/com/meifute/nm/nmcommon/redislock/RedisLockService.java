package com.meifute.nm.nmcommon.redislock;

import com.meifute.nm.nmcommon.enums.SysErrorEnums;
import com.meifute.nm.nmcommon.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.params.SetParams;

import javax.annotation.PostConstruct;
import java.util.Collections;
import java.util.UUID;

/**
 * @Classname RedisLockService
 * @Description
 * @Date 2020-07-06 18:03
 * @Created by MR. Xb.Wu
 */
@Slf4j
@Service
public class RedisLockService {

    private static JedisPool jedisPool;

    public static String host;

    @Value("${spring.redis.host}")
    private void setHost(String host) {
        RedisLockService.host = host;
    }

    private static String password;

    @Value("${spring.redis.password}")
    private void setPassword(String password) {
        RedisLockService.password = password;
    }

    private static Integer port;

    @Value("${spring.redis.port}")
    private void setPort(Integer port) {
        RedisLockService.port = port;
    }

    @PostConstruct
    private void init() {
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(200);
        config.setMaxIdle(8);
        config.setMaxWaitMillis(100000L);
        config.setTestOnBorrow(true);
        jedisPool = new JedisPool(config, host, port, 3000, password);
    }


    private static long timeout = 30000L; //获取锁的超时时间 30s没获取到锁直接失败

    //SET命令的参数
    private SetParams params = SetParams.setParams().nx();

    /**
     * 加锁
     *
     * @param lockKey
     * @return
     */
    public String lock(String lockKey, long expireTime, boolean repeat) {
        String requestId = UUID.randomUUID().toString();

        try (Jedis jedis = jedisPool.getResource()) {
            long start = System.currentTimeMillis();
            for (; ; ) {
                //SET命令返回OK ，则证明获取锁成功
                String lock = jedis.set(lockKey, requestId, params.px(expireTime));
                if ("OK".equals(lock)) {
                    log.info("lockKey:{},加锁成功.....", lockKey);
                    return requestId;
                } else if (repeat) {
                    throw new BusinessException(SysErrorEnums.REPEAT_REQUEST);
                }
                log.info("lock........");
                //否则循环等待，在timeout时间内仍未获取到锁，则获取失败
                long l = System.currentTimeMillis() - start;
                if (l >= timeout) {
                    throw new RuntimeException("获取锁失败");
                }
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    log.error("加锁异常:{0}", e);
                }
            }
        }
    }


    /**
     * 解锁
     *
     * @return
     */
    public boolean unlock(String lockKey, String value) {
        try (Jedis jedis = jedisPool.getResource()) {
            String script =
                    "if redis.call('get',KEYS[1]) == ARGV[1] then" +
                            "   return redis.call('del',KEYS[1]) " +
                            "else" +
                            "   return 0 " +
                            "end";
            Object result = jedis.eval(script, Collections.singletonList(lockKey), Collections.singletonList(value));
            if ("1".equals(result.toString())) {
                log.info("lockKey:{},释放锁成功.....", lockKey);
                return true;
            }
            return false;
        }
    }

}
