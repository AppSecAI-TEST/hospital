package com.neusoft.hs.application;

import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import com.neusoft.hs.platform.bean.ApplicationContextUtil;
import com.neusoft.hs.platform.exception.HsException;

@Service
public class InitDataService extends AppTestService {

	@Override
	public void testInit() {
		// 初始化Context
		ApplicationContext applicationContext = SpringApplication
				.run(Application.class);
		ApplicationContextUtil.setApplicationContext(applicationContext);

		clear();

		initData();
	}

	@Override
	public void execute() throws HsException {
	}

	public void testClear() {
		this.clear();
	}

}
