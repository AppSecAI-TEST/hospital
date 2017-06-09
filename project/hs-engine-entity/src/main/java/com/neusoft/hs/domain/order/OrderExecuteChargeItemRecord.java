package com.neusoft.hs.domain.order;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.neusoft.hs.domain.cost.ChargeItem;
import com.neusoft.hs.platform.entity.IdEntity;

@Entity
@Table(name = "domain_order_execute_charge_item")
public class OrderExecuteChargeItemRecord extends IdEntity {

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "order_execute_id")
	private OrderExecute orderExecute;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "charge_item_id")
	private ChargeItem chargeItem;

	private Integer count;

	public OrderExecute getOrderExecute() {
		return orderExecute;
	}

	public void setOrderExecute(OrderExecute orderExecute) {
		this.orderExecute = orderExecute;
	}

	public ChargeItem getChargeItem() {
		return chargeItem;
	}

	public void setChargeItem(ChargeItem chargeItem) {
		this.chargeItem = chargeItem;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}
}
