package com.neusoft.hs.application.organization;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.neusoft.hs.domain.organization.AbstractUser;
import com.neusoft.hs.domain.organization.Doctor;
import com.neusoft.hs.domain.organization.Nurse;
import com.neusoft.hs.domain.organization.Staff;
import com.neusoft.hs.domain.organization.UserAdminDomainService;

@Service
@Transactional(rollbackFor = Exception.class)
public class UserAdminAppService {

	@Autowired
	private UserAdminDomainService userAdminDomainService;


	public AbstractUser find(String id) {
		AbstractUser user = userAdminDomainService.find(id);
		user.doLoad();
		return user;
	}

	public Doctor findDoctor(String id) {
		Doctor doctor = userAdminDomainService.findDoctor(id);
		doctor.doLoad();
		return doctor;
	}

	public Nurse findNurse(String id) {
		Nurse nurse = userAdminDomainService.findNurse(id);
		nurse.doLoad();
		return nurse;
	}

	public Staff findStaff(String id) {
		Staff staff = userAdminDomainService.findStaff(id);
		staff.doLoad();
		return staff;
	}
}
