package com.neusoft.hs.domain.visit;

import com.neusoft.hs.domain.organization.Dept;
import com.neusoft.hs.domain.organization.Doctor;
import com.neusoft.hs.domain.organization.Nurse;

public class TransferDeptVO {

	private Visit visit;

	private Dept dept;

	private Dept area;

	private Doctor respDoctor;

	private Nurse nurse;

	private String bed;

	public Visit getVisit() {
		return visit;
	}

	public void setVisit(Visit visit) {
		this.visit = visit;
	}

	public Dept getDept() {
		return dept;
	}

	public void setDept(Dept dept) {
		this.dept = dept;
	}

	public Dept getArea() {
		return area;
	}

	public void setArea(Dept area) {
		this.area = area;
	}

	public Doctor getRespDoctor() {
		return respDoctor;
	}

	public void setRespDoctor(Doctor respDoctor) {
		this.respDoctor = respDoctor;
	}

	public Nurse getNurse() {
		return nurse;
	}

	public void setNurse(Nurse nurse) {
		this.nurse = nurse;
	}

	public String getBed() {
		return bed;
	}

	public void setBed(String bed) {
		this.bed = bed;
	}
}
