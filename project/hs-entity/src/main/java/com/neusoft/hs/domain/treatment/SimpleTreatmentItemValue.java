//Source file: F:\\my_workspace\\201611������ҽ�������\\DesignModel\\DesignElement\\SimpleTreatmentItemValue.java

package com.neusoft.hs.domain.treatment;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import com.neusoft.hs.domain.medicalrecord.MedicalRecordException;
import com.neusoft.hs.domain.medicalrecord.MedicalRecordItemValue;
import com.neusoft.hs.domain.medicalrecord.SimpleMedicalRecordItemValue;

@Entity
@DiscriminatorValue("Simple")
public class SimpleTreatmentItemValue extends TreatmentItemValue {

	@Column(length = 1024)
	private String info;

	/**
	 * @roseuid 58A108960144
	 */
	public SimpleTreatmentItemValue() {

	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	@Override
	public MedicalRecordItemValue toMedicalRecordItemValue()
			throws MedicalRecordException {
		return new SimpleMedicalRecordItemValue(this);
	}

	@Override
	public String toString() {
		return info;
	}
}
