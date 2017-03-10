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
	public void execute() throws HsException {
		
		outPatientMainTestService.execute();
		
		inPatientMainTestService.execute();

	}

}
