//Source file: F:\\my_workspace\\201611������ҽ�������\\DesignModel\\DesignElement\\domain\\organization\\Dept.java

package com.neusoft.hs.domain.organization;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.neusoft.hs.domain.cost.ChargeRecord;
import com.neusoft.hs.domain.order.Order;
import com.neusoft.hs.domain.order.OrderExecute;
import com.neusoft.hs.domain.visit.Visit;

@Entity
public abstract class Dept extends Unit {

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "org_id")
	private Org org;

	@OneToMany(mappedBy = "belongDept", cascade = { CascadeType.REFRESH })
	private List<Order> belongOrders;

	@OneToMany(mappedBy = "belongDept", cascade = { CascadeType.REFRESH })
	private List<OrderExecute> belongOrderExecutes;

	@OneToMany(mappedBy = "executeDept", cascade = { CascadeType.REFRESH })
	private List<Order> orders;

	@OneToMany(mappedBy = "executeDept", cascade = { CascadeType.REFRESH })
	private List<OrderExecute> orderExecutes;

	@OneToMany(mappedBy = "chargeDept", cascade = { CascadeType.REFRESH })
	private List<ChargeRecord> chargeRecords;

	@OneToMany(mappedBy = "dept", cascade = { CascadeType.REFRESH })
	private List<Visit> visits;

	public Dept() {
	}

	public Dept(String id) {
		this.setId(id);
	}

	public Org getOrg() {
		return org;
	}

	public void setOrg(Org org) {
		this.org = org;
	}

	public List<Order> getBelongOrders() {
		return belongOrders;
	}

	public void setBelongOrders(List<Order> belongOrders) {
		this.belongOrders = belongOrders;
	}

	public List<OrderExecute> getBelongOrderExecutes() {
		return belongOrderExecutes;
	}

	public void setBelongOrderExecutes(List<OrderExecute> belongOrderExecutes) {
		this.belongOrderExecutes = belongOrderExecutes;
	}

	public List<Order> getOrders() {
		return orders;
	}

	public void setOrders(List<Order> orders) {
		this.orders = orders;
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

	public List<Visit> getVisits() {
		return visits;
	}

	public void setVisits(List<Visit> visits) {
		this.visits = visits;
	}
}
