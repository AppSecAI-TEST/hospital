package com.neusoft.hs.portal.framework.util;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.neusoft.hs.platform.entity.SuperEntity;

@Entity
@Table(name = "portal_sys_date")
public class SysDate extends SuperEntity {

	@Id
	@Column(name = "id", unique = true, nullable = false, length = 36)
	private String id;

	@Column(name = "sys_date")
	private Date sysDate;

	public static final String Id = "sysDate";

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Date getSysDate() {
		return sysDate;
	}

	public void setSysDate(Date sysDate) {
		this.sysDate = sysDate;
	}

}
