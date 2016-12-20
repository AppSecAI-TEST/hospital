//Source file: F:\\my_workspace\\201611������ҽ�������\\DesignModel\\DesignElement\\domain\\organization\\Nurse.java

package com.neusoft.hs.domain.organization;

import java.util.List;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
@DiscriminatorValue("Staff")
public class Staff extends AbstractUser {
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "dept_id")
	private Dept dept;

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

	public Dept getDept() {
		return dept;
	}

	public void setDept(Dept dept) {
		this.dept = dept;
	}

}
