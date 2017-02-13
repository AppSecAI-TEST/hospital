//Source file: F:\\my_workspace\\201611������ҽ�������\\DesignModel\\DesignElement\\domain\\medicalrecord\\MedicalRecord.java

package com.neusoft.hs.domain.medicalrecord;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotEmpty;

import com.neusoft.hs.domain.organization.Doctor;
import com.neusoft.hs.platform.entity.IdEntity;

@Entity
@Table(name = "domain_medical_record")
public class MedicalRecord extends IdEntity {

	@NotEmpty(message = "状态不能为空")
	@Column(length = 32)
	private String state;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "clip_id")
	private MedicalRecordClip clip;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "type_id")
	private MedicalRecordType type;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "doctor_id")
	private Doctor doctor;

	@OneToMany(mappedBy = "record", cascade = { CascadeType.ALL })
	private List<MedicalRecordItem> otherItems;

	@OneToMany(mappedBy = "record", cascade = { CascadeType.ALL })
	private List<MedicalRecordLog> logs;

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public MedicalRecordClip getClip() {
		return clip;
	}

	public void setClip(MedicalRecordClip clip) {
		this.clip = clip;
	}

	public MedicalRecordType getType() {
		return type;
	}

	public void setType(MedicalRecordType type) {
		this.type = type;
	}

	public Doctor getDoctor() {
		return doctor;
	}

	public void setDoctor(Doctor doctor) {
		this.doctor = doctor;
	}

	public List<MedicalRecordItem> getOtherItems() {
		return otherItems;
	}

	public void setOtherItems(List<MedicalRecordItem> otherItems) {
		this.otherItems = otherItems;
	}

	public List<MedicalRecordLog> getLogs() {
		return logs;
	}

	public void setLogs(List<MedicalRecordLog> logs) {
		this.logs = logs;
	}
}
