package com.neusoft.hs.data.init;

import org.springframework.stereotype.Service;

@Service
public class InitDataService extends DataIniter {

	public void init() {
		clear();
		initData();
	}
}
