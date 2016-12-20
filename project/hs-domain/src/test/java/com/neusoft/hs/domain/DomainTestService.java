package com.neusoft.hs.domain;

import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import com.neusoft.hs.platform.bean.ApplicationContextUtil;
import com.neusoft.hs.platform.exception.HsException;
import com.neusoft.hs.platform.util.DateUtil;

@Service
public class DomainTestService {

	public static void testInit() {
		// 初始化Context
		ApplicationContext applicationContext = SpringApplication
				.run(Application.class);
		ApplicationContextUtil.setApplicationContext(applicationContext);
	}

	/**
	 * 
	 * @throws HsException
	 */
	public void testDomain() throws HsException {

		DateUtil.setSysDate(DateUtil.createDate("2016-01-15"));

		this.execute();

		DateUtil.setSysDate(DateUtil.createDate("2016-06-15"));

		DateUtil.setSysDate(DateUtil.createDate("2016-12-01"));

		DateUtil.setSysDate(DateUtil.createDate("2017-01-20"));

	}

	protected void initData() {

	}

	protected void execute() {

	}

	protected void clear() {
	}

}
