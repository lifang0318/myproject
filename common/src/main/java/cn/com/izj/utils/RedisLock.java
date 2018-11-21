package cn.com.izj.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * redis缓存锁操作
 * @author lifang
 */
@Component
public class RedisLock {
    private static final Logger LOGGER = LoggerFactory.getLogger(RedisLock.class);

    @Autowired
    protected RedisTemplate<String, String> redisTemplate;

    private static final long DEFAULT_WAIT_LOCK_TIME_OUT = 60000;//60s 默认等待锁超时时间
    private static final long DEFAULT_EXPIRE = 80;//80s 超时自动释放锁

    /**
     * 等待锁的时间,单位为ms
     *
     * @param key
     * @param timeout ms
     */
    public boolean lock(String key, long timeout) {
        String lockKey = generateLockKey(key);
        long start = System.currentTimeMillis();
        try {
            while ((System.currentTimeMillis() - start) < timeout) {
                if (redisTemplate.getConnectionFactory().getConnection().setNX(lockKey.getBytes(), new byte[0])) {
                    redisTemplate.expire(lockKey, DEFAULT_EXPIRE, TimeUnit.SECONDS);//暂设置为80s过期，防止异常中断锁未释放
                    if (LOGGER.isDebugEnabled()) {
                        LOGGER.debug("add RedisLock[" + key + "].");
                    }
                    return true;
                }
                LOGGER.debug("未获取到锁");
                TimeUnit.SECONDS.sleep(3);
            }
        } catch (Exception e) {
            unlock(lockKey);
        }
        return false;
    }

    public void unlock(String key) {
        String lockKey = generateLockKey(key);
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("release RedisLock[" + lockKey + "].");
        }
        RedisConnection connection = redisTemplate.getConnectionFactory().getConnection();
        connection.del(lockKey.getBytes());
        connection.close();
    }

    private String generateLockKey(String key) {
        return String.format("LOCK:%s", key);
    }

    public boolean lock(String key) {
        return lock(key, DEFAULT_WAIT_LOCK_TIME_OUT);
    }
}