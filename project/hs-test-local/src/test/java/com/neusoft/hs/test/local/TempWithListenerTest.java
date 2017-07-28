package com.neusoft.hs.test.local;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.neusoft.hs.domain.organization.AbstractUser;
import com.neusoft.hs.domain.organization.UserAdminDomainService;
import com.neusoft.hs.platform.exception.HsException;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
public class TempWithListenerTest{

	@Autowired
	private UserAdminDomainService userAdminDomainService;

	@Test
	public void test() throws HsException {
		AbstractUser user = userAdminDomainService.find("staff-test-9");
	}


}
