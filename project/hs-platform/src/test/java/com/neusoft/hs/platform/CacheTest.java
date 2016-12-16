package com.neusoft.hs.platform;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
public class CacheTest {
	
	@Autowired
	private CacheTestService cacheTestService;
	
	@Test
	public void testRedis(){
		cacheTestService.testRedis();
	}
	
	@Test
	public void testEhcache() {
		cacheTestService.testEhcache("test");
	}

	@Test
	public void testEhcacheDelete() {
		cacheTestService.testDeleteEhcache("test");
	}

}
