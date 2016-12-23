//Source file: F:\\my_workspace\\201611������ҽ�������\\DesignModel\\DesignElement\\domain\\organization\\Dept.java

package com.neusoft.hs.domain.organization;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

import com.neusoft.hs.domain.cost.ChargeRecord;
import com.neusoft.hs.domain.order.Order;
import com.neusoft.hs.domain.order.OrderExecute;
import com.neusoft.hs.domain.visit.Visit;

@Entity
@DiscriminatorValue("Dept")
public class Dept extends Unit {

	@OneToMany(mappedBy = "executeDept", cascade = { CascadeType.ALL })
	private List<OrderExecute> orderExecutes;

	@OneToMany(mappedBy = "chargeDept", cascade = { CascadeType.ALL })
	private List<ChargeRecord> chargeRecords;

	public Dept() {
	}

	public Dept(String id) {
		this.setId(id);
	}

	public List<OrderExecute> getOrderExecutes() {
		return orderExecutes;
	}

	public void setOrderExecutes(List<OrderExecute> orderExecutes) {
		this.orderExecutes = orderExecutes;
	}

	public List<ChargeRecord> getChargeRecords() {
		return chargeRecords;
	}

	public void setChargeRecords(List<ChargeRecord> chargeRecords) {
		this.chargeRecords = chargeRecords;
	}
}
