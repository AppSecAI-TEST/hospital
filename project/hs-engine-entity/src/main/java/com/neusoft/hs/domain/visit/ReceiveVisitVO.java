package com.neusoft.hs.domain.visit;

import com.neusoft.hs.domain.organization.Nurse;

public class ReceiveVisitVO {

	private Visit visit;

	private Nurse nurse;

	private String bed;

	public Visit getVisit() {
		return visit;
	}

	public void setVisit(Visit visit) {
		this.visit = visit;
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
