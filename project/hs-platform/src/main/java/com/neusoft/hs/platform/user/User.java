package com.neusoft.hs.platform.user;

import java.util.List;

public interface User {

	public String getId();

	public void setId(String id);

	public String getName();

	public void setName(String name);

	public String getDeptId();

	public void setDeptId(String deptId);

	public String getOrgId();

	public void setOrgId(String orgId);

	public List<String> getRoleIds();

	public void setRoleIds(List<String> roleIds);
}
