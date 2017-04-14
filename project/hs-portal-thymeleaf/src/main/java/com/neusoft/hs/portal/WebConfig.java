package com.neusoft.hs.portal;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.DelegatingFilterProxy;

@Configuration
public class WebConfig {
	@Bean
	public FilterRegistrationBean filterRegistrationBean() {
		FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
		
		DelegatingFilterProxy proxy = new DelegatingFilterProxy("shiroFilter");
		proxy.setTargetFilterLifecycle(true);
		
		filterRegistrationBean.setFilter(proxy);
		filterRegistrationBean.setEnabled(true);
		filterRegistrationBean.addUrlPatterns("/*");
		
		return filterRegistrationBean;
	}

}
