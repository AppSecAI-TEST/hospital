//Source file: F:\\my_workspace\\201611������ҽ�������\\DesignModel\\DesignElement\\domain\\medicalrecord\\MedicalRecordDomainService.java

package com.neusoft.hs.domain.medicalrecord;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.neusoft.hs.domain.organization.AbstractUser;
import com.neusoft.hs.domain.organization.Dept;
import com.neusoft.hs.domain.organization.Doctor;
import com.neusoft.hs.domain.treatment.Itemable;
import com.neusoft.hs.domain.visit.Visit;
import com.neusoft.hs.platform.log.LogUtil;
import com.neusoft.hs.platform.util.DateUtil;

@Service
@Transactional(rollbackFor = Exception.class)
public class MedicalRecordDomainService {

	@Autowired
	private MedicalRecordClipRepo medicalRecordClipRepo;

	@Autowired
	private MedicalRecordTypeRepo medicalRecordTypeRepo;

	@Autowired
	private MedicalRecordRepo medicalRecordRepo;

	@Autowired
	private MedicalRecordTypeBuilderRepo medicalRecordTypeBuilderRepo;

	@Autowired
	private ApplicationContext applicationContext;

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
			MedicalRecordType type, Doctor doctor) {
		MedicalRecord record = new MedicalRecord(builder, type, visit, doctor);

		return record;

	}

	/**
	 * 保存病历
	 * 
	 * @param record
	 */
	public void create(MedicalRecord record) {
		if (record.getCreateDate() == null) {
			record.setCreateDate(DateUtil.getSysDate());
		}
		if (record.getClip() == null) {
			record.setClip(this.getMedicalRecordClip(record.getVisit()));
		}
		record.setState(MedicalRecord.State_Created);

		// 填充病历项目信息
		for (Itemable item : record.getDatas().values()) {
			if (item.getCreateDate() == null) {
				item.setCreateDate(DateUtil.getSysDate());
			}
			if (item.getCreator() == null) {
				item.setCreator(record.getDoctor());
			}
		}

		record.save();

		MedicalRecordLog recordLog = new MedicalRecordLog();
		recordLog.setRecord(record);
		recordLog.setType(MedicalRecordLog.Type_Create);
		recordLog.setOperator(record.getDoctor());
		recordLog.setCreateDate(DateUtil.getSysDate());

		recordLog.save();

		applicationContext.publishEvent(new MedicalRecordCreatedEvent(record));

		LogUtil.log(this.getClass(), "医生[{}]为患者一次就诊[{}]创建病历[{}],病历类型为[{}]",
				record.getDoctor().getId(), record.getVisit().getName(),
				record.getId(), record.getType().getId());
	}

	public MedicalRecordClip findClip(String id) {
		return medicalRecordClipRepo.findOne(id);
	}

	public MedicalRecord find(String id) {
		MedicalRecord record = medicalRecordRepo.findOne(id);
		if (record != null) {
			record.load();
		}

		return record;
	}

	/**
	 * 签名
	 * 
	 * @param record
	 * @param doctor
	 * @throws MedicalRecordException
	 */
	public void sign(MedicalRecord record, Doctor doctor)
			throws MedicalRecordException {

		record.sign(doctor);

		applicationContext.publishEvent(new MedicalRecordSignedEvent(record));

		LogUtil.log(this.getClass(), "医生[{}]为患者一次就诊[{}]的病历[{}]签名, 病历类型为[{}]",
				doctor.getId(), record.getVisit().getName(), record.getId(),
				record.getType().getId());
	}

	/**
	 * 锁定
	 * 
	 * @param record
	 * @param user
	 * @throws MedicalRecordException
	 */
	public void fix(MedicalRecord record, AbstractUser user) throws MedicalRecordException {

		record.fix(user);

		applicationContext.publishEvent(new MedicalRecordFixedEvent(record));

		LogUtil.log(this.getClass(), "用户[{}]锁定患者一次就诊[{}]的病历[{}]病历类型为[{}]",
				user.getId(), record.getVisit().getName(), record.getId(),
				record.getType().getId());
	}

	public void transfer(Visit visit, Dept dept, AbstractUser user)
			throws MedicalRecordException {
		MedicalRecordClip clip = this.getMedicalRecordClip(visit);

		clip.transfer(dept);

		applicationContext.publishEvent(new MedicalRecordClipTransferedEvent(
				clip));

		LogUtil.log(this.getClass(), "用户[{}]将患者一次就诊[{}]的病历夹[{}]移交到病案室[{}]",
				user.getId(), visit.getName(), clip.getId(), dept.getId());
	}

	public void toArchive(MedicalRecordClip clip, AbstractUser user)
			throws MedicalRecordException {

		clip.setState(MedicalRecordClip.State_Archiving);
		clip.setChecker(user);
		clip.save();

		LogUtil.log(this.getClass(), "用户[{}]将患者一次就诊[{}]的病历夹[{}]发送给归档岗位",
				user.getId(), clip.getVisit().getName(), clip.getId());
	}

	public MedicalRecordClip getMedicalRecordClip(Visit visit) {
		return medicalRecordClipRepo.findByVisit(visit);
	}

	public List<MedicalRecordClip> findClips(String state, Dept dept) {
		return medicalRecordClipRepo.findByStateAndCheckDept(state, dept);
	}

	public void createMedicalRecordType(MedicalRecordType type) {
		medicalRecordTypeRepo.save(type);
	}

	public void createMedicalRecordTypes(
			List<MedicalRecordType> medicalRecordTypes) {
		medicalRecordTypeRepo.save(medicalRecordTypes);
	}

	/**
	 * @roseuid 584E167A0000
	 */
	public void clear() {
		medicalRecordTypeBuilderRepo.deleteAll();
		medicalRecordClipRepo.deleteAll();
		medicalRecordTypeRepo.deleteAll();
	}

}
