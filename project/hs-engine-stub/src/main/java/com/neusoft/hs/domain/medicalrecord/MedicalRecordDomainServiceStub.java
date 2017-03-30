package com.neusoft.hs.domain.medicalrecord;

import java.util.List;

import org.springframework.stereotype.Service;

import com.neusoft.hs.domain.organization.AbstractUser;
import com.neusoft.hs.domain.organization.Dept;
import com.neusoft.hs.domain.organization.Doctor;
import com.neusoft.hs.domain.visit.Visit;
@Service
public class MedicalRecordDomainServiceStub implements
		MedicalRecordDomainService {

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
	public MedicalRecordClip findClip(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public MedicalRecord find(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void sign(MedicalRecord record, Doctor doctor)
			throws MedicalRecordException {
		// TODO Auto-generated method stub

	}

	@Override
	public void fix(MedicalRecord record, AbstractUser user)
			throws MedicalRecordException {
		// TODO Auto-generated method stub

	}

	@Override
	public void transfer(Visit visit, Dept dept, AbstractUser user)
			throws MedicalRecordException {
		// TODO Auto-generated method stub

	}

	@Override
	public void toArchive(MedicalRecordClip clip, AbstractUser user)
			throws MedicalRecordException {
		// TODO Auto-generated method stub

	}

	@Override
	public MedicalRecordClip getMedicalRecordClip(Visit visit) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<MedicalRecordClip> findClips(String state, Dept dept) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void createMedicalRecordType(MedicalRecordType type) {
		// TODO Auto-generated method stub

	}

	@Override
	public void createMedicalRecordTypes(
			List<MedicalRecordType> medicalRecordTypes) {
		// TODO Auto-generated method stub

	}

	@Override
	public void clear() {
		// TODO Auto-generated method stub

	}

}
