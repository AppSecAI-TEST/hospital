package com.neusoft.hs.application;

import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;

import com.neusoft.hs.platform.bean.ApplicationContextUtil;

public class SuperTest {

	public void init() {
		// 初始化Context
		ApplicationContext applicationContext = SpringApplication
				.run(Application.class);
		ApplicationContextUtil.setApplicationContext(applicationContext);
	}

}
