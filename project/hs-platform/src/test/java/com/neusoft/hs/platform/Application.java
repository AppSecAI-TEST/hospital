package com.neusoft.hs.platform;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.neusoft.hs.platform.bean.ApplicationContextUtil;

@EnableAutoConfiguration
@EnableJpaRepositories(basePackages = { "com.neusoft.hs.*" })
@EntityScan(basePackages = { "com.neusoft.hs.*" })
@ComponentScan(basePackages = { "com.neusoft.hs.*" })
@EnableScheduling
@EnableAsync
public class Application {

	public static void main(String[] args) {
		ApplicationContext applicationContext = SpringApplication.run(Application.class, args);

		ApplicationContextUtil.setApplicationContext(applicationContext);
	}
}
