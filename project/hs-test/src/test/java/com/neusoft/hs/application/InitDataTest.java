package com.neusoft.hs.application;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.neusoft.hs.platform.exception.HsException;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
public class InitDataTest {

	@Autowired
	private InitDataService initDataService;

	@Before
	public void testInit() {
		initDataService.testInit();
	}

	@Test
	public void testExecute() throws HsException {
		initDataService.execute();
	}

}
