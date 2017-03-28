//Source file: F:\\my_workspace\\201611������ҽ�������\\DesignModel\\DesignElement\\SimpleTreatmentItemValue.java

package com.neusoft.hs.domain.medicalrecord;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.neusoft.hs.domain.organization.Dept;

@Entity
@DiscriminatorValue("Dept")
public class DeptMedicalRecordItemValue extends MedicalRecordItemValue {

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "dept_id")
	private Dept dept;

	/**
	 * @roseuid 58A108960144
	 */
	public DeptMedicalRecordItemValue() {

	}

	public DeptMedicalRecordItemValue(DeptMedicalRecordItemValue value) {
		dept = value.getDept();
	}

	public Dept getDept() {
		return dept;
	}

	public void setDept(Dept dept) {
		this.dept = dept;
	}

	@Override
	public String toString() {
		return dept.getName();
	}
}
