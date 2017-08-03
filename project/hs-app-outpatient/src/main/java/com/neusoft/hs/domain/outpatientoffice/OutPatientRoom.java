//Source file: F:\\my_workspace\\201611������ҽ�������\\DesignModel\\DesignElement\\domain\\outpatientoffice\\OutPatientRoom.java

package com.neusoft.hs.domain.outpatientoffice;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

import com.neusoft.hs.domain.organization.Dept;

@Entity
@DiscriminatorValue("OutPatientRoom")
public class OutPatientRoom extends Dept {

	@OneToMany(mappedBy = "room")
	private List<OutPatientPlanRecord> planRecords;

	public List<OutPatientPlanRecord> getPlanRecords() {
		return planRecords;
	}

	public void setPlanRecords(List<OutPatientPlanRecord> planRecords) {
		this.planRecords = planRecords;
	}

	@Override
	public String toString() {
		return getName();
	}
}
