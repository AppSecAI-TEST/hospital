//Source file: F:\\my_workspace\\201611������ҽ�������\\DesignModel\\DesignElement\\SimpleTreatmentItemValue.java

package com.neusoft.hs.domain.medicalrecord;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import com.neusoft.hs.domain.treatment.SimpleTreatmentItemValue;

@Entity
@DiscriminatorValue("Simple")
public class SimpleMedicalRecordItemValue extends MedicalRecordItemValue {

	@Column(length = 1024)
	private String info;

	/**
	 * @roseuid 58A108960144
	 */
	public SimpleMedicalRecordItemValue() {

	}

	public SimpleMedicalRecordItemValue(SimpleTreatmentItemValue value) {
		info = value.getInfo();
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	@Override
	public String toString() {
		return info;
	}
}
