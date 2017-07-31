package com.neusoft.hs.data.batch;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.neusoft.hs.domain.organization.AbstractUser;
import com.neusoft.hs.domain.organization.Dept;
import com.neusoft.hs.domain.organization.Org;
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

	public final static int UserCount = 10000;

	public void init() throws HsException {

		AbstractUser user;
		List<AbstractUser> users = new ArrayList<AbstractUser>();

		Dept dept;
		String deptId;
		Random random = new Random();

		for (int i = 0; i < UserCount; i++) {

			user = new Staff();

			user.setId("staff-test-" + i);
			user.setName("staff-test-name-" + i);

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
			userAdminDomainService.delete("staff-test-" + i);
		}
	}

}
