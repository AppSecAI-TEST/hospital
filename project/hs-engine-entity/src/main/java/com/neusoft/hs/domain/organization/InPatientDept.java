//Source file: F:\\my_workspace\\201611������ҽ�������\\DesignModel\\DesignElement\\domain\\organization\\Dept.java

package com.neusoft.hs.domain.organization;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * 住院科室
 * 
 * @author kingbox
 *
 */
@Entity
@DiscriminatorValue("InPatientDept")
public class InPatientDept extends Dept {

	@JsonIgnore
	@OneToMany(mappedBy = "dept")
	private List<Doctor> doctors;

	@JsonIgnore
	@ManyToMany(mappedBy = "depts", fetch = FetchType.LAZY)
	private List<InPatientAreaDept> areas;

	public InPatientDept() {
	}

	public InPatientDept(String id) {
		this.setId(id);
	}

	public List<Doctor> getDoctors() {
		return doctors;
	}

	public void setDoctors(List<Doctor> doctors) {
		this.doctors = doctors;
	}

	public List<InPatientAreaDept> getAreas() {
		return areas;
	}

	public void setAreas(List<InPatientAreaDept> areas) {
		this.areas = areas;
	}

}
