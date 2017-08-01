package com.neusoft.hs.data.batch;

import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.neusoft.hs.domain.cost.CostDomainService;
import com.neusoft.hs.domain.organization.Admin;
import com.neusoft.hs.domain.organization.Dept;
import com.neusoft.hs.domain.organization.Doctor;
import com.neusoft.hs.domain.organization.OrganizationAdminDomainService;
import com.neusoft.hs.domain.organization.UserAdminDomainService;
import com.neusoft.hs.domain.patient.PatientDomainService;
import com.neusoft.hs.domain.visit.CreateVisitVO;
import com.neusoft.hs.domain.visit.Visit;
import com.neusoft.hs.domain.visit.VisitAdminDomainService;
import com.neusoft.hs.domain.visit.VisitDomainService;
import com.neusoft.hs.platform.exception.HsException;
import com.neusoft.hs.platform.util.DateUtil;

@Service
public class VisitBatchDataService {

	@Autowired
	private VisitDomainService visitDomainService;

	@Autowired
	private VisitAdminDomainService visitAdminDomainService;

	@Autowired
	private PatientDomainService patientDomainService;

	@Autowired
	private OrganizationAdminDomainService organizationAdminDomainService;

	@Autowired
	private UserAdminDomainService userAdminDomainService;

	@Autowired
	private CostDomainService costDomainService;

	private Random random;

	private Random randomSex;

	private Random randomInPatient;

	private Random randomRespDoctor;

	private Admin admin;

	public final static int VisitCount = 5000;

	public void init() throws HsException {

		CreateVisitVO createVisitVO;

		random = new Random();
		randomSex = new Random();
		randomInPatient = new Random();
		randomRespDoctor = new Random();

		Pageable pageable = new PageRequest(0, Integer.MAX_VALUE);

		admin = userAdminDomainService.findAdmin(pageable).get(0);

		List<Doctor> doctors = userAdminDomainService.findDoctor(pageable);

		List<Dept> depts = organizationAdminDomainService.findDept(pageable);
		int deptCount = depts.size();

		int respDoctorCount = doctors.size();

		for (int i = 0; i < VisitCount; i++) {

			createVisitVO = new CreateVisitVO();

			createVisitVO.setCardNumber("v-t-cardNumber-" + i);
			createVisitVO.setName("v-t-n-" + i);
			createVisitVO.setSex(randomSex.nextInt(2) == 1 ? "男" : "女");

			createVisitVO.setDept(depts.get(deptCount - 1));

			createVisitVO.setInPatient(randomInPatient.nextInt(2) == 1 ? true
					: false);

			createVisitVO.setOperator(admin);
			createVisitVO.setState(Visit.State_Archived);
			createVisitVO.setRespDoctor(doctors.get(respDoctorCount - 1));
			createVisitVO.setBirthday(DateUtil.createDay("2009-01-01"));

			this.create(createVisitVO);
		}
	}

	@Transactional(rollbackFor = Exception.class)
	public void create(CreateVisitVO createVisitVO) {
		Visit visit = visitDomainService.create(createVisitVO);
		costDomainService.createChargeBill(visit, 0, admin);
		visit.save();
	}

	public void clear() {
		for (int i = 0; i < VisitCount; i++) {
			clear("v-t-cardNumber-" + i);
		}
	}

	@Transactional(rollbackFor = Exception.class)
	public void clear(String id) {
		visitAdminDomainService.delete(id);
		patientDomainService.delete(id);
	}

}
