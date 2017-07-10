package com.neusoft.hs.platform.cache;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.neusoft.hs.platform.Application;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
public class EhCacheCacheTest {

	@Autowired
	private EhCacheCacheTestService cacheTestService;

	@Test
	public void testEhcache() {
		cacheTestService.testEhcache("test");
	}

	@Test
	public void testEhcacheDelete() {
		cacheTestService.testDeleteEhcache("test");
	}

}
