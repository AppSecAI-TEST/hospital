package com.neusoft.hs.application.user;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.validator.constraints.NotEmpty;

import com.neusoft.hs.platform.entity.IdEntity;
import com.neusoft.hs.platform.entity.SuperEntity;
import com.neusoft.hs.platform.user.User;

@Entity
@Table(name = "app_user")
public class User2Impl extends SuperEntity implements User {

	@Id
	@Column(name = "id", unique = true, nullable = false, length = 36)
	private String id;

	@NotEmpty(message = "名称不能为空")
	@Column(length = 32)
	private String name;

	@NotEmpty(message = "部门不能为空")
	@Column(length = 36, name = "dept_id")
	private String deptId;

	@NotEmpty(message = "单位不能为空")
	@Column(length = 36, name = "org_id")
	private String orgId;

	@Transient
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
		return roleIds;
	}

	@Override
	public void setRoleIds(List<String> roleIds) {
		this.roleIds = roleIds;
	}

}
