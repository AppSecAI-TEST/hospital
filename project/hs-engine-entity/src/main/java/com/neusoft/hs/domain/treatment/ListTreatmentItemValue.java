//Source file: F:\\my_workspace\\201611������ҽ�������\\DesignModel\\DesignElement\\SimpleTreatmentItemValue.java

package com.neusoft.hs.domain.treatment;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;

import com.neusoft.hs.domain.medicalrecord.ListMedicalRecordItemValue;
import com.neusoft.hs.domain.medicalrecord.MedicalRecordException;
import com.neusoft.hs.domain.medicalrecord.MedicalRecordItemValue;

@Entity
@DiscriminatorValue("List")
public class ListTreatmentItemValue extends TreatmentItemValue {

	@ElementCollection(fetch=FetchType.EAGER)
	@CollectionTable(name = "domain_treatment_item_value_list", joinColumns = @JoinColumn(name = "value_id"))
	@Column(name = "data")
	private Map<String, String> data = new LinkedHashMap<String, String>();

	/**
	 * @roseuid 58A108960144
	 */
	public ListTreatmentItemValue() {

	}

	public Map<String, String> getData() {
		return data;
	}

	public void setData(Map<String, String> data) {
		this.data = data;
	}

	public void putData(String key, String value) {
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
