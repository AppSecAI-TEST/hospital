//Source file: F:\\my_workspace\\201611������ҽ�������\\DesignModel\\DesignElement\\SimpleTreatmentItemValue.java

package com.neusoft.hs.domain.treatment;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Transient;

import com.neusoft.hs.domain.medicalrecord.ListMedicalRecordItemValue;
import com.neusoft.hs.domain.medicalrecord.MedicalRecordException;
import com.neusoft.hs.domain.medicalrecord.MedicalRecordItemValue;

@Entity
@DiscriminatorValue("List")
public class ListTreatmentItemValue extends TreatmentItemValue {

	@Transient
	private Map<String, Object> data = new LinkedHashMap<String, Object>();

	/**
	 * @roseuid 58A108960144
	 */
	public ListTreatmentItemValue() {

	}

	public Map<String, Object> getData() {
		return data;
	}

	public void setData(Map<String, Object> data) {
		this.data = data;
	}

	public void putData(String key, Object value) {
		this.data.put(key, value);
	}

	@Override
	public MedicalRecordItemValue toMedicalRecordItemValue()
			throws MedicalRecordException {
		return new ListMedicalRecordItemValue(this);
	}

	@Override
	public String toString() {
		return data.toString();
	}
}
