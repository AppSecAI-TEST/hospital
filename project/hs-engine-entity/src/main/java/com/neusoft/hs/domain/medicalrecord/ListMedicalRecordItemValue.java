//Source file: F:\\my_workspace\\201611������ҽ�������\\DesignModel\\DesignElement\\SimpleTreatmentItemValue.java

package com.neusoft.hs.domain.medicalrecord;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;

import com.neusoft.hs.domain.treatment.ListTreatmentItemValue;

@Entity
@DiscriminatorValue("List")
public class ListMedicalRecordItemValue extends MedicalRecordItemValue {

	@ElementCollection
	@CollectionTable(name = "domain_medical_record_item_value_list", joinColumns = @JoinColumn(name = "value_id"))
	@Column(name = "data")
	private Map<String, String> data;

	/**
	 * @roseuid 58A108960144
	 */
	public ListMedicalRecordItemValue() {

	}

	public ListMedicalRecordItemValue(ListTreatmentItemValue value) {

		this.data = new LinkedHashMap<String, String>();

		for (String key : value.getData().keySet()) {
			this.data.put(key, value.getData().get(key).toString());
		}

	}

	@Override
	public String toString() {

		StringBuffer info = new StringBuffer();

		for (String key : data.keySet()) {
			info.append(key);
			info.append(":");
			info.append(data.get(key));
			info.append("\n");
		}
		return info.toString();
	}
}
