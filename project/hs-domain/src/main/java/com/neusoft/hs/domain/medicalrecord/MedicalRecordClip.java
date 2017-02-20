//Source file: F:\\my_workspace\\201611������ҽ�������\\DesignModel\\DesignElement\\domain\\medicalrecord\\MedicalRecordClip.java

package com.neusoft.hs.domain.medicalrecord;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotEmpty;

import com.neusoft.hs.domain.visit.Visit;
import com.neusoft.hs.platform.entity.IdEntity;

@Entity
@Table(name = "domain_medical_record_clip")
public class MedicalRecordClip extends IdEntity {

	@NotEmpty(message = "状态不能为空")
	@Column(length = 32)
	private String state = State_Normal;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "visit_id")
	private Visit visit;

	@OneToMany(mappedBy = "clip", cascade = { CascadeType.ALL })
	private List<MedicalRecord> records;

	public static final String State_Normal = "正常";

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

}
