package com.neusoft.hs.test.server;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.neusoft.hs.platform.exception.HsException;
import com.neusoft.hs.test.AppTestService;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = TestServer.class)
public class PatientMainWithListenerTest extends SuperTest {

	@Autowired
	@Qualifier(value = "patientMainTestService")
	private AppTestService appTestService;

	@Before
	public void testInit() {
		this.init();
		appTestService.testInit();
	}

	@Test
	public void testExecute() throws HsException {
		appTestService.execute();
	}

}
