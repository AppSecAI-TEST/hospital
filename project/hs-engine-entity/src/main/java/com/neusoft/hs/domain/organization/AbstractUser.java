package com.neusoft.hs.domain.organization;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotEmpty;

import com.neusoft.hs.platform.entity.SuperEntity;
import com.neusoft.hs.platform.user.User;

@Entity
@Table(name = "domain_user")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class AbstractUser extends SuperEntity implements User {

	@Id
	@Column(name = "id", unique = true, nullable = false, length = 36)
	private String id;

	@NotEmpty(message = "名称不能为空")
	@Column(length = 32)
	private String name;

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

	public abstract Dept getDept();

	public abstract void setDept(Dept dept);

	@Override
	public String toString() {
		return name;
	}

}
