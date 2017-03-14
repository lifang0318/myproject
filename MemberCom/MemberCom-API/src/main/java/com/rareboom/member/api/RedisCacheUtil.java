package com.rareboom.member.api;

import java.io.Serializable;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.stereotype.Component;

@Component
public class RedisCacheUtil {

	@Autowired
	private RedisTemplate jedisTemplate;

	/**
	 * 获取 RedisSerializer
	 */
	protected RedisSerializer<String> getRedisSerializer() {
		return jedisTemplate.getStringSerializer();
	}

	/**
	 * 设置缓存对象
	 * 
	 * @param key
	 * @param value
	 * @return
	 */
	public boolean set(final String key, Object value) {
		boolean result = false;
		try {
			ValueOperations<Serializable, Object> operations = jedisTemplate.opsForValue();
			operations.set(key, value);
			result = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 获取缓存对象
	 * 
	 * @param key
	 * @return
	 */
	public Object get(final String key) {
		Object result = null;
		ValueOperations<Serializable, Object> operations = jedisTemplate.opsForValue();
		result = operations.get(key);
		return result;
	}

	/**
	 * 更新缓存对象
	 * 
	 * @param key
	 * @param element
	 * @return
	 */
	public boolean update(final String key, Object obj) {
		boolean result = false;
		try {
			List<Object> list = (List<Object>) get(key);
			if (list != null) {
				list.add(obj);
				ValueOperations<Serializable, Object> operations = jedisTemplate.opsForValue();
				operations.getAndSet(key, list);
				result = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 根据key删除缓存对象
	 * 
	 * @param key
	 * @return
	 */
	public void del(final String key) {
	  jedisTemplate.delete(key);	
	}
}
