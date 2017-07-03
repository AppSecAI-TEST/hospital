//Source file: F:\\my_workspace\\201611������ҽ�������\\DesignModel\\DesignElement\\SimpleTreatmentItemValue.java

package com.neusoft.hs.domain.medicalrecord;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;

import com.neusoft.hs.domain.treatment.ListTreatmentItemValue;

@Entity
@DiscriminatorValue("List")
public class ListMedicalRecordItemValue extends MedicalRecordItemValue {

	@ElementCollection(fetch=FetchType.EAGER)
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

		Map<String, Object> originalData = value.getData();

		for (String key : originalData.keySet()) {
			if (originalData.get(key) != null) {
				this.data.put(key, originalData.get(key).toString());
			} else {
				this.data.put(key, null);
			}
		}

	}

	public Map<String, String> getData() {
		return data;
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
