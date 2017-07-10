package com.neusoft.hs.platform.cache;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = CacheTestApplication.class)
public class EhCacheCacheTest {

	@Autowired
	private EhCacheCacheTestService cacheTestService;

	@Test
	public void testEhcache() {
		
		cacheTestService.testEhcache("test");
		
		cacheTestService.testEhcache("test");
		
		cacheTestService.testDeleteEhcache("test");
		
		cacheTestService.testEhcache("test");
		
		cacheTestService.testEhcache("test");
		
	}
}
