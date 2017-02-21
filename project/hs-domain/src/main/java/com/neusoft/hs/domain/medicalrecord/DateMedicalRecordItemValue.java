//Source file: F:\\my_workspace\\201611������ҽ�������\\DesignModel\\DesignElement\\SimpleTreatmentItemValue.java

package com.neusoft.hs.domain.medicalrecord;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import com.neusoft.hs.platform.exception.HsException;
import com.neusoft.hs.platform.util.DateUtil;

@Entity
@DiscriminatorValue("Date")
public class DateMedicalRecordItemValue extends MedicalRecordItemValue {

	@Column
	private Date date;

	/**
	 * @roseuid 58A108960144
	 */
	public DateMedicalRecordItemValue() {

	}

	public DateMedicalRecordItemValue(DateMedicalRecordItemValue value) {
		date = value.getDate();
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	@Override
	public String toString() {
		try {
			return DateUtil.toString(date);
		} catch (HsException e) {
			e.printStackTrace();
			return null;
		}
	}
}
