package com.neusoft.hs.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.neusoft.hs.platform.exception.HsException;
import com.neusoft.hs.test.InitDataService;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
public class ClearDataTest extends SuperTest{

	@Autowired
	private InitDataService initDataService;

	@Test
	public void testClear() throws HsException {
		initDataService.testClear();
	}

}
