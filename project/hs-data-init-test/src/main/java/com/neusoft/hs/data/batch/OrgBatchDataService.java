package com.neusoft.hs.data.batch;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.neusoft.hs.domain.organization.CommonDept;
import com.neusoft.hs.domain.organization.Dept;
import com.neusoft.hs.domain.organization.Org;
import com.neusoft.hs.domain.organization.OrganizationAdminDomainService;
import com.neusoft.hs.domain.organization.Unit;

@Service
public class OrgBatchDataService {

	@Autowired
	private OrganizationAdminDomainService organizationAdminDomainService;

	public final static int DeptCount = 2000;

	public void init() {

		Org org = organizationAdminDomainService.getOrgs().get(0);

		Dept dept;
		List<Unit> depts = new ArrayList<Unit>();

		for (int i = 0; i < DeptCount; i++) {

			dept = new CommonDept();

			dept.setId("dept-test-" + i);
			dept.setName("dept-test-name-" + i);
			dept.setParent(org);
			dept.setOrg(org);

			depts.add(dept);
		}

		organizationAdminDomainService.create(depts);
	}

	public void clear() {
		for (int i = 0; i < DeptCount; i++) {
			organizationAdminDomainService.delete("dept-test-" + i);
		}
	}

}
