package com.neusoft.hs.platform.user;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;

@Entity
public interface User {
	
	@Id
	public String getId();

	public void setId(String id);

	public String getName();

	public void setName(String name);

	@Transient
	public String getDeptId();

	public void setDeptId(String deptId);

	@Transient
	public String getOrgId();

	public void setOrgId(String orgId);

	@Transient
	public List<String> getRoleIds();

	public void setRoleIds(List<String> roleIds);
}
