//Source file: F:\\my_workspace\\201611������ҽ�������\\DesignModel\\DesignElement\\domain\\medicalrecord\\MedicalRecordClip.java

package com.neusoft.hs.domain.medicalrecord;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotEmpty;

import com.neusoft.hs.domain.organization.AbstractUser;
import com.neusoft.hs.domain.organization.Dept;
import com.neusoft.hs.domain.visit.Visit;
import com.neusoft.hs.domain.visit.VisitException;
import com.neusoft.hs.platform.entity.IdEntity;
import com.neusoft.hs.platform.util.DateUtil;

/**
 * 病历夹 包含多份病历 在患者一次就诊创建时创建
 * 
 * @author kingbox
 *
 */
@Entity
@Table(name = "domain_medical_record_clip")
public class MedicalRecordClip extends IdEntity {

	@NotEmpty(message = "状态不能为空")
	@Column(length = 32)
	private String state = State_Writing;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "visit_id")
	private Visit visit;

	@Column(name = "visit_name", length = 16)
	private String visitName;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "check_dept_id")
	private Dept checkDept;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "checker_id")
	private AbstractUser checker;

	@OneToMany(mappedBy = "clip", cascade = { CascadeType.ALL })
	private List<MedicalRecord> records;

	public static final String State_Writing = "编写中";

	public static final String State_Ended = "已结束";

	public static final String State_Checking = "检查中";

	public static final String State_Archiving = "待归档";

	public static final String State_Archived = "已归档";

	public MedicalRecordClip() {
	}

	/**
	 * 将病历夹移交给病案室 并设置病历夹为【检查中】
	 * 
	 * @param dept
	 * @throws MedicalRecordException
	 * @throws VisitException
	 */
	public void transfer(Dept dept, AbstractUser user)
			throws MedicalRecordException, VisitException {

		Set<MedicalRecordType> createdRecordTypes = new HashSet<MedicalRecordType>();
		for (MedicalRecord record : records) {
			record.checkTransfer();
			createdRecordTypes.add(record.getType());
		}

		List<MedicalRecordType> needCreateRecordTypes = this.getService(
				MedicalRecordTypeRepo.class).findByNeedCreate(true);
		for (MedicalRecordType needCreateRecordType : needCreateRecordTypes) {
			if (!createdRecordTypes.contains(needCreateRecordType)) {
				throw new MedicalRecordException(null, "类型为[%s]病历还没有创建",
						needCreateRecordType.getName());
			}
		}

		this.checkDept = dept;
		this.state = State_Checking;

		this.visit.transferRecordRoom(dept, user);
	}

	/**
	 * 移交归档
	 * 
	 * @param checker
	 */
	public void toArchive(AbstractUser checker) {
		this.setState(MedicalRecordClip.State_Archiving);
		this.setChecker(checker);
	}

	/**
	 * 归档
	 * 
	 * @param user
	 */
	public void archive(AbstractUser user) {
		this.setState(MedicalRecordClip.State_Archived);
		this.visit.archive(user);
	}

	/**
	 * 门诊离院
	 * 
	 * @param user
	 */
	public void leaveHospital(AbstractUser user) {
		this.state = State_Ended;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public Visit getVisit() {
		return visit;
	}

	public void setVisit(Visit visit) {
		this.visit = visit;
		this.visitName = visit.getName();
		this.visit.setMedicalRecordClip(this);
	}

	public String getVisitName() {
		return visitName;
	}

	public void setVisitName(String visitName) {
		this.visitName = visitName;
	}

	public Dept getCheckDept() {
		return checkDept;
	}

	public void setCheckDept(Dept checkDept) {
		this.checkDept = checkDept;
	}

	public AbstractUser getChecker() {
		return checker;
	}

	public void setChecker(AbstractUser checker) {
		this.checker = checker;
	}

	public List<MedicalRecord> getRecords() {
		return records;
	}

	public void setRecords(List<MedicalRecord> records) {
		this.records = records;
	}

	public void save() {
		this.getService(MedicalRecordClipRepo.class).save(this);
	}

	@Override
	public String toString() {
		return this.visitName;
	}

}
