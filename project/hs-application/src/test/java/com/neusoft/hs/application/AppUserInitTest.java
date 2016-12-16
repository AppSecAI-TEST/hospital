package com.neusoft.hs.application;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
public class AppUserInitTest {

	@Autowired
	private AppUserInitService appUserInitService;

	@Test
	public void createUsers() {
		appUserInitService.createUsers();
	}

}
