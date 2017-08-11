//Source file: F:\\my_workspace\\201611������ҽ�������\\DesignModel\\DesignElement\\domain\\organization\\Dept.java

package com.neusoft.hs.domain.organization;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.neusoft.hs.domain.cost.ChargeRecord;
import com.neusoft.hs.domain.order.Order;
import com.neusoft.hs.domain.order.OrderExecute;
import com.neusoft.hs.domain.visit.Visit;

@Entity
public abstract class Dept extends Unit {

	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "org_id")
	private Org org;

	@JsonIgnore
	@OneToMany(mappedBy = "belongDept")
	private List<Order> belongOrders;

	@JsonIgnore
	@OneToMany(mappedBy = "belongDept")
	private List<OrderExecute> belongOrderExecutes;

	@JsonIgnore
	@OneToMany(mappedBy = "executeDept")
	private List<Order> orders;

	@JsonIgnore
	@OneToMany(mappedBy = "executeDept")
	private List<OrderExecute> orderExecutes;

	@JsonIgnore
	@OneToMany(mappedBy = "chargeDept")
	private List<ChargeRecord> chargeRecords;

	@JsonIgnore
	@OneToMany(mappedBy = "dept")
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
	
	/**
	 * 部门关联的可操作部门
	 * 
	 * @return
	 */
	@JsonIgnore
	public List<Dept> getOperationDepts() {
		List<Dept> operationDepts = new ArrayList<Dept>();
		operationDepts.add(this);
		return operationDepts;
	}

	public static List<String> getNames(List<Dept> depts) {
		List<String> names = new ArrayList<String>();

		for (Dept dept : depts) {
			names.add(dept.getName());
		}

		return names;
	}
}
