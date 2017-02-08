//Source file: F:\\my_workspace\\201611������ҽ�������\\DesignModel\\DesignElement\\platform\\security\\Role.java

package com.neusoft.hs.domain.organization;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotEmpty;

import com.neusoft.hs.domain.order.OrderExecute;
import com.neusoft.hs.platform.entity.IdEntity;

@Entity
@Table(name = "domain_role")
public class Role extends IdEntity {

	@NotEmpty(message = "名称不能为空")
	@Column(length = 32)
	private String name;

	@OneToMany(mappedBy = "role", cascade = { CascadeType.ALL })
	private List<UserRole> userRoles;

	@OneToMany(mappedBy = "executeRole", cascade = { CascadeType.REFRESH })
	private List<OrderExecute> theOrderExecutes;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<UserRole> getUserRoles() {
		return userRoles;
	}

	public void setUserRoles(List<UserRole> userRoles) {
		this.userRoles = userRoles;
	}

	public List<OrderExecute> getTheOrderExecutes() {
		return theOrderExecutes;
	}

	public void setTheOrderExecutes(List<OrderExecute> theOrderExecutes) {
		this.theOrderExecutes = theOrderExecutes;
	}

}
