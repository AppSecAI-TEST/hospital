//Source file: F:\\my_workspace\\201611������ҽ�������\\DesignModel\\DesignElement\\domain\\medicalrecord\\MedicalRecordItem.java

package com.neusoft.hs.domain.medicalrecord;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotEmpty;

import com.neusoft.hs.platform.entity.IdEntity;

@Entity
@Table(name = "domain_medical_record_item")
public class MedicalRecordItem extends IdEntity {

	@NotEmpty(message = "Key不能为空")
	@Column(length = 32)
	private String name;

	@Column(length = 1024)
	private String info;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "record_id")
	private MedicalRecord record;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public MedicalRecord getRecord() {
		return record;
	}

	public void setRecord(MedicalRecord record) {
		this.record = record;
	}

}
