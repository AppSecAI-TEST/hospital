//Source file: F:\\my_workspace\\201611������ҽ�������\\DesignModel\\DesignElement\\SimpleTreatmentItemValue.java

package com.neusoft.hs.domain.medicalrecord;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.neusoft.hs.domain.diagnosis.DiagnosisTreatmentItemValue;
import com.neusoft.hs.domain.diagnosis.Disease;

@Entity
@DiscriminatorValue("Diagnosis")
public class DiagnosisMedicalRecordItemValue extends MedicalRecordItemValue {

	@Column(length = 128)
	private String remark;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "disease_id")
	private Disease disease;

	/**
	 * @roseuid 58A108960144
	 */
	public DiagnosisMedicalRecordItemValue() {

	}

	public DiagnosisMedicalRecordItemValue(DiagnosisTreatmentItemValue value) {
		this.remark = value.getRemark();
		this.disease = value.getDisease();
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Disease getDisease() {
		return disease;
	}

	public void setDisease(Disease disease) {
		this.disease = disease;
	}

	@Override
	public String toString() {
		StringBuffer info = new StringBuffer();
		info.append(disease.getName());
		if (this.remark != null) {
			info.append(" ");
			info.append(this.remark);
		}
		return info.toString();
	}
}
