package com.neusoft.hs.portal.swing.init;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.neusoft.hs.data.init.InitDataService;
import com.neusoft.hs.platform.exception.HsException;
import com.neusoft.hs.test.PatientMainTestService;

@Service
public class DataService {

	@Autowired
	private PatientMainTestService patientMainTestService;

	@Autowired
	private InitDataService initDataService;

	@Autowired
	private SwingDataInitService swingDataInitService;

	public void initBaseData() {
		initDataService.init();
		swingDataInitService.init();
	}

	public void initTestData() throws HsException {
		patientMainTestService.init();
		patientMainTestService.execute();
		swingDataInitService.init();
	}

}
