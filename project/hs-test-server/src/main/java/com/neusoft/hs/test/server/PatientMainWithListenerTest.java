package com.neusoft.hs.test.server;

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
@SpringBootTest(classes = TestServer.class)
public class PatientMainWithListenerTest {

	@Autowired
	@Qualifier(value = "patientMainTestService")
	private AppTestService appTestService;

	@Before
	public void testInit() {
		appTestService.init();
	}

	@Test
	public void testExecute() throws HsException {
		appTestService.execute();
	}

}
