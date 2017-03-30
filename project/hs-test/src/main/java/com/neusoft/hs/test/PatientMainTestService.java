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

		inPatientMainTestService.setVisit(outPatientMainTestService.getVisit());

		inPatientMainTestService.doExecute();

	}

}
