package com.neusoft.hs.platform.user;

import java.util.List;

import com.neusoft.hs.platform.user.User;

public class UserImpl implements User {

	private String id;

	private String name;

	private String deptId;

	private String orgId;

	private List<String> roleIds;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDeptId() {
		return deptId;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}

	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	@Override
	public List<String> getRoleIds() {
		return this.roleIds;
	}

	@Override
	public void setRoleIds(List<String> roleIds) {
		this.roleIds = roleIds;
	}
}
