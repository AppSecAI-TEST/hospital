//Source file: F:\\my_workspace\\201611������ҽ�������\\DesignModel\\DesignElement\\SimpleTreatmentItemValue.java

package com.neusoft.hs.domain.treatment;

import javax.persistence.Column;

public class SimpleTreatmentItemValue extends TreatmentItemValue {

	@Column(length = 1024)
	private String value;

	/**
	 * @roseuid 58A108960144
	 */
	public SimpleTreatmentItemValue() {

	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}
