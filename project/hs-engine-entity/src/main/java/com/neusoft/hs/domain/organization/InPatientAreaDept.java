//Source file: F:\\my_workspace\\201611������ҽ�������\\DesignModel\\DesignElement\\engine\\entity\\organization\\InpatientAreaDept.java

package com.neusoft.hs.domain.organization;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * 住院病区
 * 
 * 与住院科室是多对多关系
 * 
 * @author kingbox
 *
 */
@Entity
@DiscriminatorValue("InPatientAreaDept")
public class InPatientAreaDept extends Dept {
	@JsonIgnore
	@OneToMany(mappedBy = "dept", cascade = { CascadeType.REFRESH })
	private List<Nurse> nurses;

	@JsonIgnore
	@ManyToMany(fetch = FetchType.EAGER, cascade = { CascadeType.ALL })
	@JoinTable(name = "domain_organization_area_dept", joinColumns = { @JoinColumn(name = "area_id", referencedColumnName = "id") }, inverseJoinColumns = { @JoinColumn(name = "dept_id", referencedColumnName = "id") })
	private List<InPatientDept> depts;

	public List<Nurse> getNurses() {
		return nurses;
	}

	public void setNurses(List<Nurse> nurses) {
		this.nurses = nurses;
	}

	public List<InPatientDept> getDepts() {
		return depts;
	}

	public void setDepts(List<InPatientDept> depts) {
		this.depts = depts;
	}

	public void addDept(InPatientDept dept) {
		if (this.depts == null) {
			this.depts = new ArrayList<InPatientDept>();
		}
		this.depts.add(dept);
	}

}
