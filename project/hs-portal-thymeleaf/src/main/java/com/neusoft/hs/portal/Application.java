package com.neusoft.hs.portal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ImportResource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.neusoft.hs.platform.bean.ApplicationContextUtil;

@EnableAutoConfiguration
@EnableJpaRepositories(basePackages = { "com.neusoft.hs.*","org.apache.shiro.*" })
@EntityScan(basePackages = { "com.neusoft.hs.*","org.apache.shiro.*" })
@ComponentScan(basePackages = { "com.neusoft.hs.*","org.apache.shiro.*" })
@ImportResource(locations={"classpath:application-bean.xml","classpath:spring-shiro.xml"})
@EnableScheduling
@EnableAsync
public class Application {

	public static void main(String[] args) {
		ApplicationContext applicationContext = SpringApplication.run(Application.class, args);

		ApplicationContextUtil.setApplicationContext(applicationContext);
	}
}
