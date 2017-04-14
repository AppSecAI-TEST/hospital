package com.neusoft.hs.test.local;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ImportResource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAutoConfiguration
@EnableJpaRepositories(basePackages = { "com.neusoft.hs.*" })
@EntityScan(basePackages = { "com.neusoft.hs.*" })
@ComponentScan(basePackages = { "com.neusoft.hs.*" })
@ImportResource(locations = { "classpath:application-bean.xml" })
// @EnableScheduling
@EnableAsync
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}
