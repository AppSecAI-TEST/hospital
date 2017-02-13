//Source file: F:\\my_workspace\\201611������ҽ�������\\DesignModel\\DesignElement\\domain\\medicalrecord\\MedicalRecordLog.java

package com.neusoft.hs.domain.medicalrecord;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.neusoft.hs.platform.entity.IdEntity;

@Entity
@Table(name = "domain_medical_record_log")
public class MedicalRecordLog extends IdEntity {

	@Column(name = "create_date")
	private Date createDate;

	@Column(length = 256)
	private String template;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "record_id")
	private MedicalRecord record;

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getTemplate() {
		return template;
	}

	public void setTemplate(String template) {
		this.template = template;
	}

	public MedicalRecord getRecord() {
		return record;
	}

	public void setRecord(MedicalRecord record) {
		this.record = record;
	}

}
