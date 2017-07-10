package com.neusoft.hs.platform.cache;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;

import com.neusoft.hs.platform.bean.ApplicationContextUtil;

@EnableAutoConfiguration
@ComponentScan(basePackages = { "com.neusoft.hs.platform.cache" })
public class CacheTestApplication {

	public static void main(String[] args) {
		ApplicationContext applicationContext = SpringApplication.run(CacheTestApplication.class, args);

		ApplicationContextUtil.setApplicationContext(applicationContext);
	}
}
