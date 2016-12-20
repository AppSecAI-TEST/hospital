//Source file: F:\\my_workspace\\201611������ҽ�������\\DesignModel\\DesignElement\\domain\\cost\\CostRecord.java

package com.neusoft.hs.domain.cost;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotEmpty;

import com.neusoft.hs.platform.entity.IdEntity;

@Entity
@Table(name = "domain_cost_record")
public class CostRecord extends IdEntity {

	private float cost;

	@NotEmpty(message = "状态不能为空")
	@Column(length = 32)
	private String state;

	@OneToMany(mappedBy = "costRecord", cascade = { CascadeType.ALL })
	private List<ChargeRecord> chargeRecords;

	public float getCost() {
		return cost;
	}

	public void setCost(float cost) {
		this.cost = cost;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public List<ChargeRecord> getChargeRecords() {
		return chargeRecords;
	}

	public void setChargeRecords(List<ChargeRecord> chargeRecords) {
		this.chargeRecords = chargeRecords;
	}

}
