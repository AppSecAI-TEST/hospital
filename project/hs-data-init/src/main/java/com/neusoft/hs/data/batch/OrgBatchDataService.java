package com.neusoft.hs.data.batch;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.neusoft.hs.domain.inspect.InspectDept;
import com.neusoft.hs.domain.organization.CommonDept;
import com.neusoft.hs.domain.organization.Dept;
import com.neusoft.hs.domain.organization.InPatientAreaDept;
import com.neusoft.hs.domain.organization.InPatientDept;
import com.neusoft.hs.domain.organization.Org;
import com.neusoft.hs.domain.organization.OrganizationAdminDomainService;
import com.neusoft.hs.domain.organization.OutPatientDept;
import com.neusoft.hs.domain.organization.Unit;
import com.neusoft.hs.domain.outpatientoffice.OutPatientRoom;
import com.neusoft.hs.domain.pharmacy.Pharmacy;

@Service
public class OrgBatchDataService {

	@Autowired
	private OrganizationAdminDomainService organizationAdminDomainService;

	private Random random;

	public final static int DeptCount = 200;

	public void init() {

		Org org = organizationAdminDomainService.getOrgs().get(0);

		Dept dept;
		List<Unit> depts = new ArrayList<Unit>();

		random = new Random();
	
		for (int i = 0; i < DeptCount; i++) {

			dept = this.createDept();
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

	private Dept createDept() {

		int type = random.nextInt(7);
		Dept dept = null;

		switch (type) {
		case 1: {
			dept = new CommonDept();
			break;
		}
		case 2: {
			dept = new InPatientAreaDept();
			break;
		}
		case 3: {
			dept = new InPatientDept();
			break;
		}
		case 4: {
			dept = new InspectDept();
			break;
		}
		case 5: {
			dept = new OutPatientDept();
			break;
		}
		case 6: {
			dept = new OutPatientRoom();
			break;
		}
		case 7: {
			dept = new Pharmacy();
			break;
		}
		default: {
			dept = new CommonDept();
			break;
		}
		}

		return dept;
	}

}
