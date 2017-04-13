package com.neusoft.hs.domain.inspect;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.neusoft.hs.domain.organization.Dept;

@Entity
@DiscriminatorValue("InspectDept")
public class InspectDept extends Dept {

	@JsonIgnore
	@OneToMany(mappedBy = "inspectDept", cascade = { CascadeType.DETACH })
	private List<InspectResult> inspectResults;

	public List<InspectResult> getInspectResults() {
		return inspectResults;
	}

	public void setInspectResults(List<InspectResult> inspectResults) {
		this.inspectResults = inspectResults;
	}

}
