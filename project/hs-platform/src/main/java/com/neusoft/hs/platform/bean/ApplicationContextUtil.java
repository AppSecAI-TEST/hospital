package com.neusoft.hs.platform.bean;

import org.springframework.context.ApplicationContext;

public class ApplicationContextUtil {
	
	private static ApplicationContext applicationContext;

	public static ApplicationContext getApplicationContext() {
		return applicationContext;
	}

	public static void setApplicationContext(ApplicationContext applicationContext) {
		ApplicationContextUtil.applicationContext = applicationContext;
	}
}
