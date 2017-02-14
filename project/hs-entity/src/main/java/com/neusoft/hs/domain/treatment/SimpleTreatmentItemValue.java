//Source file: F:\\my_workspace\\201611������ҽ�������\\DesignModel\\DesignElement\\SimpleTreatmentItemValue.java

package com.neusoft.hs.domain.treatment;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

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
}
