//Source file: F:\\my_workspace\\201611������ҽ�������\\DesignModel\\DesignElement\\app\\pharmacy\\domain\\ConfigureFluidBatch.java

package com.neusoft.hs.domain.pharmacy;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "pharmacy_id")
	private Pharmacy pharmacy;

	private int beginDate;

	private int endDate;

	private int planExecuteDate;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

	public Pharmacy getPharmacy() {
		return pharmacy;
	}

	public void setPharmacy(Pharmacy pharmacy) {
		this.pharmacy = pharmacy;
	}

	public int getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(int beginDate) {
		this.beginDate = beginDate;
	}

	public int getEndDate() {
		return endDate;
	}

	public void setEndDate(int endDate) {
		this.endDate = endDate;
	}

	public int getPlanExecuteDate() {
		return planExecuteDate;
	}

	public void setPlanExecuteDate(int planExecuteDate) {
		this.planExecuteDate = planExecuteDate;
	}

}
