//Source file: F:\\my_workspace\\201611������ҽ�������\\DesignModel\\DesignElement\\domain\\medicalrecord\\MedicalRecordDomainService.java

package com.neusoft.hs.domain.medicalrecord;

import java.util.List;

import com.neusoft.hs.domain.organization.AbstractUser;
import com.neusoft.hs.domain.organization.Dept;
import com.neusoft.hs.domain.organization.Doctor;
import com.neusoft.hs.domain.visit.Visit;

public interface MedicalRecordDomainService {

	/**
	 * 创建病历
	 * 
	 * @param builder
	 * @param visit
	 * @param type
	 * @param doctor
	 * @return
	 */
	public MedicalRecord create(MedicalRecordBuilder builder, Visit visit,
			MedicalRecordType type, Doctor doctor);

	/**
	 * 保存病历
	 * 
	 * @param record
	 */
	public void create(MedicalRecord record);

	public MedicalRecordClip findClip(String id);

	public MedicalRecord find(String id);

	/**
	 * 签名
	 * 
	 * @param record
	 * @param doctor
	 * @throws MedicalRecordException
	 */
	public void sign(MedicalRecord record, Doctor doctor)
			throws MedicalRecordException;

	/**
	 * 锁定
	 * 
	 * @param record
	 * @param user
	 * @throws MedicalRecordException
	 */
	public void fix(MedicalRecord record, AbstractUser user)
			throws MedicalRecordException;

	public void transfer(Visit visit, Dept dept, AbstractUser user)
			throws MedicalRecordException;

	public void toArchive(MedicalRecordClip clip, AbstractUser user)
			throws MedicalRecordException;

	public MedicalRecordClip getMedicalRecordClip(Visit visit);

	public List<MedicalRecordClip> findClips(String state, Dept dept);

	public void createMedicalRecordType(MedicalRecordType type);

	public void createMedicalRecordTypes(
			List<MedicalRecordType> medicalRecordTypes);

	/**
	 * @roseuid 584E167A0000
	 */
	public void clear();

}
