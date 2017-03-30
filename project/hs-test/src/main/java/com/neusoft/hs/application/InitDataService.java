package com.neusoft.hs.application;

import org.springframework.stereotype.Service;

import com.neusoft.hs.platform.exception.HsException;

@Service
public class InitDataService extends AppTestService {

	@Override
	public void testInit() {
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
