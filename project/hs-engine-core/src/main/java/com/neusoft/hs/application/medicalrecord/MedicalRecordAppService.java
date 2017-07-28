package com.neusoft.hs.application.medicalrecord;

import java.util.List;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.neusoft.hs.domain.medicalrecord.MedicalRecord;
import com.neusoft.hs.domain.medicalrecord.MedicalRecordAdminDomainService;
import com.neusoft.hs.domain.medicalrecord.MedicalRecordBuilder;
import com.neusoft.hs.domain.medicalrecord.MedicalRecordClip;
import com.neusoft.hs.domain.medicalrecord.MedicalRecordDomainService;
import com.neusoft.hs.domain.medicalrecord.MedicalRecordException;
import com.neusoft.hs.domain.medicalrecord.MedicalRecordType;
import com.neusoft.hs.domain.medicalrecord.MedicalRecordTypeBuilder;
import com.neusoft.hs.domain.organization.AbstractUser;
import com.neusoft.hs.domain.treatment.TreatmentException;
import com.neusoft.hs.domain.visit.Visit;
import com.neusoft.hs.platform.exception.HsException;

@Service
@Transactional(rollbackFor = Exception.class)
public class MedicalRecordAppService {

	@Autowired
	private MedicalRecordDomainService medicalRecordDomainService;

	@Autowired
	private MedicalRecordAdminDomainService medicalRecordAdminDomainService;

	/**
	 * 创建病历
	 * 
	 * @param typeId
	 * @param visit
	 * @param user
	 * @return
	 * @throws HsException
	 */
	public MedicalRecord create(String typeId, Visit visit,
			boolean needTreatment, AbstractUser user) throws HsException {

		MedicalRecordType type = medicalRecordAdminDomainService
				.getMedicalRecordType(typeId);

		MedicalRecordBuilder builder = new MedicalRecordTypeBuilder(type, visit);
		builder.setNeedTreatment(needTreatment);

		return medicalRecordDomainService.create(builder, visit, type, user);
	}

	/**
	 * 创建病历
	 * 
	 * @param builder
	 * @param visit
	 * @param type
	 * @param doctor
	 * @return
	 * @throws TreatmentException
	 */
	public MedicalRecord create(MedicalRecordBuilder builder, Visit visit,
			MedicalRecordType type, AbstractUser doctor)
			throws TreatmentException {
		return medicalRecordDomainService.create(builder, visit, type, doctor);
	}

	/**
	 * 保存病历
	 * 
	 * @param record
	 * @throws MedicalRecordException
	 */
	public void save(MedicalRecord record) throws MedicalRecordException {
		medicalRecordDomainService.save(record);
	}

	/**
	 * 病历锁定
	 * 
	 * @param id
	 * @param user
	 * @throws MedicalRecordException
	 * @throws TreatmentException
	 */
	public void fix(String id, AbstractUser user)
			throws MedicalRecordException, TreatmentException {
		MedicalRecord record = medicalRecordDomainService.find(id);
		if (record == null) {
			throw new MedicalRecordException(null, "id=[%s]病历不存在", id);
		}
		medicalRecordDomainService.fix(record, user);
	}

	/**
	 * 病历签名
	 * 
	 * @param id
	 * @param doctor
	 * @throws MedicalRecordException
	 * @throws TreatmentException
	 */
	public void sign(String id, AbstractUser doctor)
			throws MedicalRecordException, TreatmentException {
		MedicalRecord record = medicalRecordDomainService.find(id);
		if (record == null) {
			throw new MedicalRecordException(null, "id=[%s]病历不存在", id);
		}
		medicalRecordDomainService.sign(record, doctor);
	}

	public MedicalRecord find(String id) throws TreatmentException {
		return medicalRecordDomainService.find(id);
	}

	public List<MedicalRecord> getMedicalRecords(Visit visit, Pageable pageable) {
		return medicalRecordDomainService.getMedicalRecords(visit, pageable);
	}

	public List<MedicalRecord> getMedicalRecords(MedicalRecordClip clip,
			Pageable pageable) {
		return medicalRecordDomainService.getMedicalRecords(clip, pageable);
	}

	public List<MedicalRecordClip> getMedicalRecordClips(String state,
			Pageable pageable) {
		return medicalRecordDomainService
				.getMedicalRecordClips(state, pageable);
	}
}
