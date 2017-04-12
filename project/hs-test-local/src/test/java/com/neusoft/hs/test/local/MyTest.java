package com.neusoft.hs.test.local;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.neusoft.hs.platform.exception.HsException;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
public class MyTest extends SuperTest{

	@Autowired
	private MyService myService;

	@Before
	public void testInit() {
		this.init();
	}
	
	@Test
	public void testUpdate() throws HsException {
		myService.testUpdate();
	}

}
