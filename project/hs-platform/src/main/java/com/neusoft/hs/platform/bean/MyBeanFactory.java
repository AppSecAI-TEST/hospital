package com.neusoft.hs.platform.bean;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.stereotype.Service;

@Service
public class MyBeanFactory implements BeanFactoryAware {

	private static BeanFactory beanFactory;

	@Override
	public void setBeanFactory(BeanFactory beanFactory1) throws BeansException {
		beanFactory = beanFactory1;
	}

	public static Object getBean(String beanId) {
		return beanFactory.getBean(beanId);
	}

}