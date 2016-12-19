//Source file: F:\\my_workspace\\201611������ҽ�������\\DesignModel\\DesignElement\\domain\\cost\\ChargeRecord.java

package com.neusoft.hs.domain.cost;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.neusoft.hs.domain.order.OrderExecute;
import com.neusoft.hs.domain.organization.Dept;
import com.neusoft.hs.platform.entity.IdEntity;

@Entity
@Table(name = "domain_charge_record")
public class ChargeRecord extends IdEntity {

	private float amount;

	private int count;

	private float price;

	@Column(name = "create_date")
	private Date createDate;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "cost_record_id")
	private CostRecord costRecord;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "charge_item_id")
	private ChargeItem chargeItem;

	@OneToOne(mappedBy = "original", cascade = { CascadeType.ALL })
	private ChargeRecord original;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "order_execute_id")
	private OrderExecute orderExecute;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "charge_bill_id")
	private ChargeBill chargeBill;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "charge_dept_id")
	private Dept chargeDept;

	/**
	 * @roseuid 5850A1CD019F
	 */
	public void createCostRecord() {

	}

	/**
	 * @roseuid 5850BE360360
	 */
	public void undo() {

	}

	public float getAmount() {
		return amount;
	}

	public void setAmount(float amount) {
		this.amount = amount;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public CostRecord getCostRecord() {
		return costRecord;
	}

	public void setCostRecord(CostRecord costRecord) {
		this.costRecord = costRecord;
	}

	public ChargeItem getChargeItem() {
		return chargeItem;
	}

	public void setChargeItem(ChargeItem chargeItem) {
		this.chargeItem = chargeItem;
	}

	public ChargeRecord getOriginal() {
		return original;
	}

	public void setOriginal(ChargeRecord original) {
		this.original = original;
	}

	public OrderExecute getOrderExecute() {
		return orderExecute;
	}

	public void setOrderExecute(OrderExecute orderExecute) {
		this.orderExecute = orderExecute;
	}

	public ChargeBill getChargeBill() {
		return chargeBill;
	}

	public void setChargeBill(ChargeBill chargeBill) {
		this.chargeBill = chargeBill;
	}

	public Dept getChargeDept() {
		return chargeDept;
	}

	public void setChargeDept(Dept chargeDept) {
		this.chargeDept = chargeDept;
	}

}
