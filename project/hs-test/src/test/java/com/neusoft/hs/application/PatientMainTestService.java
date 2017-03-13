package com.neusoft.hs.application;

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
		
		inPatientMainTestService.choice();
		
		inPatientMainTestService.ready();
		
		MedicalRecordTestService.temporaryOrderCount = 5;
	}



	@Override
	public void execute() throws HsException {

		outPatientMainTestService.execute();

		inPatientMainTestService.setVisit(outPatientMainTestService.getVisit());
		
		inPatientMainTestService.doExecute();

	}

}
