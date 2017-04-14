package com.neusoft.hs.portal.swing;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ImportResource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableAsync;

import com.neusoft.hs.platform.bean.ApplicationContextUtil;
import com.neusoft.hs.portal.swing.ui.main_menu.controller.MainMenuController;
import com.neusoft.hs.portal.swing.util.LookAndFeelUtils;

@EnableAutoConfiguration
@EnableJpaRepositories(basePackages = { "com.neusoft.hs.*" })
@EntityScan(basePackages = { "com.neusoft.hs.*" })
@ComponentScan(basePackages = { "com.neusoft.hs.*" })
@ImportResource(locations = { "classpath:application-bean.xml" })
// @EnableScheduling
@EnableAsync
public class Application {

	public static void main(String[] args) {
		LookAndFeelUtils.setWindowsLookAndFeel();
		ConfigurableApplicationContext context = new SpringApplicationBuilder(
				Application.class).headless(false).run(args);
		ApplicationContextUtil.setApplicationContext(context);
		MainMenuController mainMenuController = context
				.getBean(MainMenuController.class);
		mainMenuController.prepareAndOpenFrame();
	}

}
