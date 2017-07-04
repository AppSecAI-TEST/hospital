//Source file: F:\\my_workspace\\201611������ҽ�������\\DesignModel\\DesignElement\\entity\\diagnosis\\DiagnosisTreatmentItemValue.java

package com.neusoft.hs.domain.diagnosis;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.neusoft.hs.domain.medicalrecord.DiagnosisMedicalRecordItemValue;
import com.neusoft.hs.domain.medicalrecord.MedicalRecordException;
import com.neusoft.hs.domain.medicalrecord.MedicalRecordItemValue;
import com.neusoft.hs.domain.treatment.TreatmentItemValue;

@Entity
@DiscriminatorValue("Diagnosis")
public class DiagnosisTreatmentItemValue extends TreatmentItemValue {

	@Column(length = 128)
	private String remark;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "disease_id")
	private Disease disease;

	/**
	 * @roseuid 58CA2959006C
	 */
	public DiagnosisTreatmentItemValue() {

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
	public MedicalRecordItemValue toMedicalRecordItemValue()
			throws MedicalRecordException {
		return new DiagnosisMedicalRecordItemValue(this);
	}

	@Override
	public String toString() {
		
		StringBuffer info = new StringBuffer();
		info.append("remark:");
		info.append(remark);
		info.append(" disease:");
		info.append(disease.getName());

		return info.toString();
	}

}
