package com.neusoft.hs.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.neusoft.hs.platform.exception.HsException;

@Service
public class PatientMainTestService extends AppTestService {

	@Autowired
	private OutPatientMainTestService outPatientMainTestService;

	@Autowired
	private InPatientMainTestService inPatientMainTestService;

	@Autowired
	CheckTestService checkTestService;

	@Override
	public void testInit() {
		super.testInit();

		outPatientMainTestService.clone(this);

		inPatientMainTestService.clone(this);

		MedicalRecordTestService.temporaryOrderCount = 8;
	}

	@Override
	public void execute() throws HsException {

		outPatientMainTestService.execute();

		inPatientMainTestService.setVisit001(outPatientMainTestService
				.getVisit001());
		
		inPatientMainTestService.createVisit004();//向住院场景增加患者004
		
		inPatientMainTestService.doExecute();

		checkTestService.setVisit001(inPatientMainTestService.getVisit001());

		checkTestService.setVisit002(outPatientMainTestService.getVisit002());

		checkTestService.setVisit003(outPatientMainTestService.getVisit003());

		checkTestService.execute();
	}

}
