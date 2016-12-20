//Source file: F:\\my_workspace\\201611������ҽ�������\\DesignModel\\DesignElement\\platform\\security\\UserRole.java

package com.neusoft.hs.domain.organization;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.neusoft.hs.platform.entity.IdEntity;
import com.neusoft.hs.platform.user.User;

@Entity
@Table(name = "domain_user_role")
public class UserRole extends IdEntity {

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private User user;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "role_id")
	private Role role;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

}
