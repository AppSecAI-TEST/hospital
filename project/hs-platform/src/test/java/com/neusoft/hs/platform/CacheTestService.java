package com.neusoft.hs.platform;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class CacheTestService {

	@Autowired
	private StringRedisTemplate redisTemplate;

	public void testRedis() {
		
		redisTemplate.delete("test");
		
		redisTemplate.opsForValue().append("test", "test");
	}

	@Cacheable(value = "testCache", key = "#testId")
	public CacheVO testEhcache(String testId) {
		
		CacheVO cacheVO = new CacheVO();
		
		cacheVO.setId("test");
		cacheVO.setName("测试");
		
		return cacheVO;
	}

	@CacheEvict(value = "testCache", key = "#testId")
	public void testDeleteEhcache(String testId) {

	}

}
