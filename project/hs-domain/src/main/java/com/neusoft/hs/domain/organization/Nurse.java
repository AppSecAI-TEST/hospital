//Source file: F:\\my_workspace\\201611������ҽ�������\\DesignModel\\DesignElement\\domain\\organization\\Nurse.java

package com.neusoft.hs.domain.organization;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotEmpty;

import com.neusoft.hs.domain.visit.Visit;
import com.neusoft.hs.platform.entity.IdEntity;
import com.neusoft.hs.platform.user.User;

@Entity
@DiscriminatorValue("Nurse")
public class Nurse extends AbstractUser {
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "dept_id")
	private Dept dept;

	@OneToMany(mappedBy = "respNurse", cascade = { CascadeType.ALL })
	private List<Visit> visits;

	@Override
	public String getDeptId() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setDeptId(String deptId) {
		// TODO Auto-generated method stub

	}

	@Override
	public String getOrgId() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setOrgId(String orgId) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<String> getRoleIds() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setRoleIds(List<String> roleIds) {
		// TODO Auto-generated method stub

	}

	public List<Visit> getVisits() {
		return visits;
	}

	public void setVisits(List<Visit> visits) {
		this.visits = visits;
	}
	
	public Dept getDept() {
		return dept;
	}

	public void setDept(Dept dept) {
		this.dept = dept;
	}

}
