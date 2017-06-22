package com.neusoft.hs.test.local;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.neusoft.hs.platform.exception.HsException;
import com.neusoft.hs.test.AppTestService;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
public class InPatientCompsiteOrderWithListenerTest{

	@Autowired
	@Qualifier(value = "inPatientCompsiteOrderTestService")
	private AppTestService appTestService;

	@Before
	public void testInit() {
		appTestService.testInit();
	}

	@Test
	public void testExecute() throws HsException {
		appTestService.execute();
	}

}
