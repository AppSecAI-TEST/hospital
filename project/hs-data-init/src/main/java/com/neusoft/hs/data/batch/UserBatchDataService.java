package com.neusoft.hs.data.batch;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.neusoft.hs.domain.organization.AbstractUser;
import com.neusoft.hs.domain.organization.Dept;
import com.neusoft.hs.domain.organization.Doctor;
import com.neusoft.hs.domain.organization.Nurse;
import com.neusoft.hs.domain.organization.OrganizationAdminDomainService;
import com.neusoft.hs.domain.organization.Staff;
import com.neusoft.hs.domain.organization.UserAdminDomainService;
import com.neusoft.hs.platform.exception.HsException;

@Service
public class UserBatchDataService {

	@Autowired
	private OrganizationAdminDomainService organizationAdminDomainService;

	@Autowired
	private UserAdminDomainService userAdminDomainService;

	private Random random;

	public final static int UserCount = 400;

	public void init() throws HsException {

		AbstractUser user;
		List<AbstractUser> users = new ArrayList<AbstractUser>();

		Dept dept;
		String deptId;
		random = new Random();

		for (int i = 0; i < UserCount; i++) {

			user = this.createAbstractUser();

			user.setId("user-test-" + i);
			user.setName("user-test-name-" + i);

			deptId = "dept-test-"
					+ random.nextInt(OrgBatchDataService.DeptCount - 1);
			dept = organizationAdminDomainService.findTheDept(deptId);
			if (dept == null) {
				throw new HsException("部门[%s]不存在", deptId);
			}
			user.setDept(dept);

			users.add(user);

		}

		userAdminDomainService.create(users);
	}

	public void clear() {
		for (int i = 0; i < UserCount; i++) {
			userAdminDomainService.delete("user-test-" + i);
		}
	}

	private AbstractUser createAbstractUser() {

		int type = random.nextInt(3);
		AbstractUser user = null;

		switch (type) {
		case 1: {
			user = new Doctor();
			break;
		}
		case 2: {
			user = new Nurse();
			break;
		}
		case 3: {
			user = new Staff();
			break;
		}

		default: {
			user = new Staff();
			break;
		}
		}

		return user;
	}

}
