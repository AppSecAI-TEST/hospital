//Source file: F:\\my_workspace\\201611������ҽ�������\\DesignModel\\DesignElement\\domain\\organization\\Doctor.java

package com.neusoft.hs.domain.organization;

import java.util.List;

import com.neusoft.hs.domain.order.Order;
import com.neusoft.hs.domain.visit.Visit;
import com.neusoft.hs.platform.user.User;

public class Doctor implements User {
	public Order theOrder[];
	public Doctor superior;
	public Doctor subordinates[];
	public Dept theDept;
	public Visit visits[];

	/**
	 * @roseuid 58574D290308
	 */
	public Doctor() {

	}

	@Override
	public String getId() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setId(String id) {
		// TODO Auto-generated method stub

	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setName(String name) {
		// TODO Auto-generated method stub

	}

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
}
