package com.neusoft.hs.application.medicalrecord;

import org.springframework.stereotype.Service;

import com.neusoft.hs.domain.medicalrecord.MedicalRecord;
import com.neusoft.hs.domain.medicalrecord.MedicalRecordBuilder;
import com.neusoft.hs.domain.medicalrecord.MedicalRecordException;
import com.neusoft.hs.domain.medicalrecord.MedicalRecordType;
import com.neusoft.hs.domain.organization.AbstractUser;
import com.neusoft.hs.domain.organization.Dept;
import com.neusoft.hs.domain.organization.Doctor;
import com.neusoft.hs.domain.visit.Visit;
@Service
public class MedicalRecordAppServiceStub implements MedicalRecordAppService {

	@Override
	public MedicalRecord create(MedicalRecordBuilder builder, Visit visit,
			MedicalRecordType type, Doctor doctor) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void create(MedicalRecord record) {
		// TODO Auto-generated method stub

	}

	@Override
	public MedicalRecord find(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void sign(String id, Doctor doctor) throws MedicalRecordException {
		// TODO Auto-generated method stub

	}

	@Override
	public void fix(String id, AbstractUser user) throws MedicalRecordException {
		// TODO Auto-generated method stub

	}

	@Override
	public void transfer(Visit visit, Dept dept, AbstractUser user)
			throws MedicalRecordException {
		// TODO Auto-generated method stub

	}

}
