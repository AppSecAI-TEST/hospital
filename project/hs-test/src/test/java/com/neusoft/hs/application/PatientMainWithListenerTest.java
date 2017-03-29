package com.neusoft.hs.application;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.neusoft.hs.platform.exception.HsException;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
public class PatientMainWithListenerTest {

	@Autowired
	@Qualifier(value = "patientMainTestService")
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
