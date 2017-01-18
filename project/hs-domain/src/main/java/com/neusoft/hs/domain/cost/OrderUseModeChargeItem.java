package com.neusoft.hs.domain.cost;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotEmpty;

import com.neusoft.hs.domain.order.OrderUseMode;
import com.neusoft.hs.platform.entity.SuperEntity;

@Entity
@Table(name = "domain_order_use_mode_charge_item")
public class OrderUseModeChargeItem extends SuperEntity {

	@Id
	@Column(name = "id", unique = true, nullable = false, length = 36)
	private String id;

	@NotEmpty(message = "代码不能为空")
	@Column(length = 32)
	private String code;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "use_mode_id")
	private OrderUseMode orderUseMode;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "charge_item_id")
	private ChargeItem chargeItem;

	@Column(length = 16)
	private String sign;

	@NotEmpty(message = "收费模式不能为空")
	@Column(name = "charge_mode", length = 32)
	private String chargeMode;

	public static final String everyOne = "everyOne";// 按频次收费

	public static final String everyDay = "everyDay";// 按天收费

	public static final String onlyOne = "onlyOne";// 只收一次

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

	public OrderUseMode getOrderUseMode() {
		return orderUseMode;
	}

	public void setOrderUseMode(OrderUseMode orderUseMode) {
		this.orderUseMode = orderUseMode;
	}

	public ChargeItem getChargeItem() {
		return chargeItem;
	}

	public void setChargeItem(ChargeItem chargeItem) {
		this.chargeItem = chargeItem;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public String getChargeMode() {
		return chargeMode;
	}

	public void setChargeMode(String chargeMode) {
		this.chargeMode = chargeMode;
	}
}
