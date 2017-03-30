package com.neusoft.hs.domain.patient;

import org.springframework.stereotype.Service;

@Service
public class PatientDomainServiceStub implements PatientDomainService {

	@Override
	public Patient findByCardNumber(String cardNumber) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void clear() {
		// TODO Auto-generated method stub

	}

}
