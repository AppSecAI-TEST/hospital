//Source file: F:\\my_workspace\\201611������ҽ�������\\DesignModel\\DesignElement\\domain\\cost\\ChargeRecord.java

package com.neusoft.hs.domain.cost;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.neusoft.hs.domain.order.OrderExecute;
import com.neusoft.hs.domain.organization.Dept;
import com.neusoft.hs.platform.entity.IdEntity;
import com.neusoft.hs.platform.util.DateUtil;

@Entity
@Table(name = "domain_charge_record")
public class ChargeRecord extends IdEntity {

	private Float amount;

	private Integer count;

	private Float price;

	@Column(name = "create_date")
	private Date createDate;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "cost_record_id")
	private CostRecord costRecord;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "charge_item_id")
	private ChargeItem chargeItem;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "original_id")
	private ChargeRecord original;

	@OneToOne(mappedBy = "original", cascade = { CascadeType.ALL })
	private ChargeRecord newChargeRecord;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "order_execute_id")
	private OrderExecute orderExecute;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "charge_bill_id")
	private ChargeBill chargeBill;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "charge_dept_id")
	private Dept chargeDept;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "belong_dept_id")
	private Dept belongDept;

	@Transient
	private boolean haveCost = true;

	@Transient
	private boolean haveCharge = true;

	/**
	 * @roseuid 5850A1CD019F
	 */
	public CostRecord createCostRecord() {
		if (this.haveCost) {
			CostRecord costRecord = new CostRecord();

			List<ChargeRecord> chargeRecords = new ArrayList<ChargeRecord>();
			chargeRecords.add(this);
			costRecord.setChargeRecords(chargeRecords);

			this.setCostRecord(costRecord);

			costRecord.setCost(this.getAmount());
			costRecord.setState(CostRecord.State_Normal);
			costRecord.setCreateDate(createDate);

			return costRecord;
		} else {
			return null;
		}

	}

	/**
	 * @return
	 * @roseuid 5850BE360360
	 */
	public ChargeRecord undo() {
		ChargeRecord chargeRecord = new ChargeRecord();

		chargeRecord.setCount(count);
		chargeRecord.setPrice(price);
		chargeRecord.setAmount(-amount);
		chargeRecord.setOriginal(this);
		chargeRecord.setChargeItem(chargeItem);
		chargeRecord.setCostRecord(costRecord);
		chargeRecord.setOrderExecute(orderExecute);

		chargeRecord.setChargeDept(chargeDept);
		chargeRecord.setBelongDept(belongDept);
		chargeRecord.setCreateDate(DateUtil.getSysDate());

		this.setNewChargeRecord(chargeRecord);

		return chargeRecord;
	}

	public ChargeRecord createPayRecord() {
		ChargeRecord chargeRecord = new ChargeRecord();

		chargeRecord.setCount(count);
		chargeRecord.setPrice(price);
		chargeRecord.setAmount(-amount);
		chargeRecord.setChargeItem(chargeItem);
		chargeRecord.setOrderExecute(orderExecute);
		chargeRecord.setChargeDept(chargeDept);
		chargeRecord.setBelongDept(belongDept);
		chargeRecord.setCreateDate(createDate);

		return chargeRecord;
	}

	public Float getAmount() {
		return amount;
	}

	public void setAmount(Float amount) {
		this.amount = amount;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public Float getPrice() {
		return price;
	}

	public void setPrice(Float price) {
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

	public Dept getBelongDept() {
		return belongDept;
	}

	public void setBelongDept(Dept belongDept) {
		this.belongDept = belongDept;
	}

	public ChargeRecord getNewChargeRecord() {
		return newChargeRecord;
	}

	public void setNewChargeRecord(ChargeRecord newChargeRecord) {
		this.newChargeRecord = newChargeRecord;
	}

	public boolean isHaveCost() {
		return haveCost;
	}

	public void setHaveCost(boolean haveCost) {
		this.haveCost = haveCost;
	}

	public boolean isHaveCharge() {
		return haveCharge;
	}

	public void setHaveCharge(boolean haveCharge) {
		this.haveCharge = haveCharge;
	}

	public void save() {
		this.getService(ChargeRecordRepo.class).save(this);
	}
}
