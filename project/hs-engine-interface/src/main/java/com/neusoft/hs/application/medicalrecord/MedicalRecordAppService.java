package com.neusoft.hs.application.medicalrecord;

import org.springframework.cloud.netflix.feign.FeignClient;

import com.neusoft.hs.domain.medicalrecord.MedicalRecord;
import com.neusoft.hs.domain.medicalrecord.MedicalRecordBuilder;
import com.neusoft.hs.domain.medicalrecord.MedicalRecordException;
import com.neusoft.hs.domain.medicalrecord.MedicalRecordType;
import com.neusoft.hs.domain.organization.AbstractUser;
import com.neusoft.hs.domain.organization.Dept;
import com.neusoft.hs.domain.organization.Doctor;
import com.neusoft.hs.domain.visit.Visit;

@FeignClient("engine-service")
public interface MedicalRecordAppService {

	public MedicalRecord create(MedicalRecordBuilder builder, Visit visit,
			MedicalRecordType type, Doctor doctor);

	public void create(MedicalRecord record);

	public MedicalRecord find(String id);

	public void sign(String id, Doctor doctor) throws MedicalRecordException;

	public void fix(String id, AbstractUser user) throws MedicalRecordException;

	public void transfer(Visit visit, Dept dept, AbstractUser user)
			throws MedicalRecordException;
}
