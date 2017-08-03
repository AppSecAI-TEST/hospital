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
import com.neusoft.hs.domain.visit.Visit;
import com.neusoft.hs.platform.entity.IdEntity;
import com.neusoft.hs.platform.util.DateUtil;

/**
 * 费用条目
 * 
 * @author kingbox
 *
 */
@Entity
@Table(name = "domain_cost_charge_record")
public class ChargeRecord extends IdEntity {

	private Float amount;

	private Integer count;

	private Float price;

	@Column(length = 32)
	private String type;

	@Column(name = "create_date")
	private Date createDate;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "cost_record_id")
	private CostRecord costRecord;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "charge_item_id")
	private ChargeItem chargeItem;

	@Column(name = "charge_item_name", length = 64)
	private String chargeItemName;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "original_id")
	private ChargeRecord original;

	@OneToOne(mappedBy = "original", fetch = FetchType.LAZY, cascade = {
			CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE })
	private ChargeRecord newChargeRecord;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "order_execute_id")
	private OrderExecute orderExecute;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "charge_bill_id")
	private ChargeBill chargeBill;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "visit_id")
	private Visit visit;

	@Column(length = 16)
	private String visitName;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "charge_dept_id")
	private Dept chargeDept;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "belong_dept_id")
	private Dept belongDept;

	@Transient
	private boolean haveCost = true;

	public static final String Type_PreCharge = "预存";

	public static final String Type_ShouldCharge = "应扣";

	public static final String Type_Charged = "已扣";

	public static final String Type_BackCharge = "退费";

	public static final String Type_Balance = "结账";

	/**
	 * 根据费用条目创建成本条目
	 * 
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
	 * 创建取消收费条目的费用条目
	 * 
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
		chargeRecord.setType(Type_BackCharge);

		chargeRecord.setChargeDept(chargeDept);
		chargeRecord.setBelongDept(belongDept);
		chargeRecord.setCreateDate(DateUtil.getSysDate());
		chargeRecord.setVisit(visit);

		this.setNewChargeRecord(chargeRecord);

		return chargeRecord;
	}

	/**
	 * 创建收费条目对应的付费条目
	 * 
	 * @return
	 */
	public ChargeRecord createPayRecord() {
		ChargeRecord chargeRecord = new ChargeRecord();

		chargeRecord.setCount(count);
		chargeRecord.setPrice(price);
		chargeRecord.setAmount(-amount);
		chargeRecord.setChargeItem(chargeItem);
		chargeRecord.setOrderExecute(orderExecute);
		chargeRecord.setChargeDept(chargeDept);
		chargeRecord.setBelongDept(belongDept);
		chargeRecord.setType(Type_Charged);
		chargeRecord.setCreateDate(createDate);
		chargeRecord.setVisit(visit);

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
		if (chargeItem != null) {
			this.chargeItemName = chargeItem.getName();
		}
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
		this.setVisit(chargeBill.getVisit());
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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getChargeItemName() {
		return chargeItemName;
	}

	public void setChargeItemName(String chargeItemName) {
		this.chargeItemName = chargeItemName;
	}

	public Visit getVisit() {
		return visit;
	}

	public void setVisit(Visit visit) {
		this.visit = visit;
		this.visitName = visit.getName();
	}

	public String getVisitName() {
		return visitName;
	}

	public void setVisitName(String visitName) {
		this.visitName = visitName;
	}

	public void save() {
		this.getService(ChargeRecordRepo.class).save(this);
	}
}
