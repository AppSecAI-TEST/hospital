//Source file: F:\\my_workspace\\201611������ҽ�������\\DesignModel\\DesignElement\\domain\\organization\\Org.java

package com.neusoft.hs.domain.organization;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity
@DiscriminatorValue("Org")
public class Org extends Unit {

	@OneToOne(fetch = FetchType.EAGER, cascade = { CascadeType.REMOVE,
			CascadeType.REFRESH })
	@JoinColumn(name = "out_charge_dept_id")
	private Dept outChargeDept;

	@OneToOne(fetch = FetchType.EAGER, cascade = { CascadeType.REMOVE,
			CascadeType.REFRESH })
	@JoinColumn(name = "in_charge_dept_id")
	private Dept inChargeDept;

	@OneToOne(fetch = FetchType.EAGER, cascade = { CascadeType.REMOVE,
			CascadeType.REFRESH })
	@JoinColumn(name = "record_room_dept_id")
	private Dept recordRoomDept;

	@OneToOne(fetch = FetchType.EAGER, cascade = { CascadeType.REMOVE,
			CascadeType.REFRESH })
	@JoinColumn(name = "in_patient_office_dept_id")
	private Dept inPatientOfficeDept;

	/**
	 * 门诊收费处
	 * 
	 * @return
	 */
	public Dept getOutChargeDept() {
		return outChargeDept;
	}

	public void setOutChargeDept(Dept outChargeDept) {
		this.outChargeDept = outChargeDept;
	}

	/**
	 * 住院收费处
	 * 
	 * @return
	 */
	public Dept getInChargeDept() {
		return inChargeDept;
	}

	public void setInChargeDept(Dept inChargeDept) {
		this.inChargeDept = inChargeDept;
	}

	/**
	 * 病案室
	 * 
	 * @return
	 */
	public Dept getRecordRoomDept() {
		return recordRoomDept;
	}

	public void setRecordRoomDept(Dept recordRoomDept) {
		this.recordRoomDept = recordRoomDept;
	}

	/**
	 * 住院处
	 * 
	 * @return
	 */
	public Dept getInPatientOfficeDept() {
		return inPatientOfficeDept;
	}

	public void setInPatientOfficeDept(Dept inPatientOfficeDept) {
		this.inPatientOfficeDept = inPatientOfficeDept;
	}

}
