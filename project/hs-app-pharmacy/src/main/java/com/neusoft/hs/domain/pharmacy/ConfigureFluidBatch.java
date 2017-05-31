//Source file: F:\\my_workspace\\201611������ҽ�������\\DesignModel\\DesignElement\\app\\pharmacy\\domain\\ConfigureFluidBatch.java

package com.neusoft.hs.domain.pharmacy;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotEmpty;

import com.neusoft.hs.platform.entity.SuperEntity;

@Entity
@Table(name = "app_pharmacy_configure_fluid_batch")
public class ConfigureFluidBatch extends SuperEntity {

	@Id
	@Column(name = "id", unique = true, nullable = false, length = 36)
	private String id;

	@NotEmpty(message = "代码不能为空")
	@Column(length = 32)
	private String code;

	@NotEmpty(message = "名称不能为空")
	@Column(length = 32)
	private String name;

	@NotEmpty(message = "开始时间不能为空")
	private Date begin;

	@NotEmpty(message = "截止时间不能为空")
	private Date end;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Date getBegin() {
		return begin;
	}

	public void setBegin(Date begin) {
		this.begin = begin;
	}

	public Date getEnd() {
		return end;
	}

	public void setEnd(Date end) {
		this.end = end;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
