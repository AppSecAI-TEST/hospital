//Source file: F:\\my_workspace\\201611������ҽ�������\\DesignModel\\DesignElement\\domain\\cost\\ChargeItem.java

package com.neusoft.hs.domain.cost;

import java.util.List;

import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.validator.constraints.NotEmpty;

import com.neusoft.hs.domain.order.OrderExecuteChargeItemRecord;
import com.neusoft.hs.domain.order.OrderType;
import com.neusoft.hs.platform.entity.SuperEntity;

/**
 * 计费项目 任何一条收费条目都需要关联计费项目
 * 
 * @author kingbox
 *
 */
@Entity
@Table(name = "domain_cost_charge_item")
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE, region = "chargeItemCache")
public class ChargeItem extends SuperEntity {

	@Id
	@Column(name = "id", unique = true, nullable = false, length = 36)
	private String id;

	@NotEmpty(message = "代码不能为空")
	@Column(length = 32)
	private String code;

	@Column(length = 64)
	private String name;

	private float price;

	@Column(length = 16)
	private String unit;

	@Column(name = "charging_mode", length = 16)
	private String chargingMode;

	@OneToMany(mappedBy = "chargeItem")
	private List<ChargeRecord> chargeRecords;

	@OneToMany(mappedBy = "chargeItem")
	private List<OrderType> orderTypes;

	@OneToMany(mappedBy = "chargeItem")
	private List<OrderExecuteChargeItemRecord> orderExecuteChargeItemRecords;

	@OneToMany(mappedBy = "chargeItem")
	private List<VisitChargeItem> visitChargeItems;

	public static final String ChargingMode_Day = "每天";
	public static final String ChargingMode_Amount = "数量";

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

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public String getChargingMode() {
		return chargingMode;
	}

	public void setChargingMode(String chargingMode) {
		this.chargingMode = chargingMode;
	}

	public List<ChargeRecord> getChargeRecords() {
		return chargeRecords;
	}

	public void setChargeRecords(List<ChargeRecord> chargeRecords) {
		this.chargeRecords = chargeRecords;
	}

	public List<VisitChargeItem> getVisitChargeItems() {
		return visitChargeItems;
	}

	public void setVisitChargeItems(List<VisitChargeItem> visitChargeItems) {
		this.visitChargeItems = visitChargeItems;
	}

	public List<OrderType> getOrderTypes() {
		return orderTypes;
	}

	public void setOrderTypes(List<OrderType> orderTypes) {
		this.orderTypes = orderTypes;
	}

	public List<OrderExecuteChargeItemRecord> getOrderExecuteChargeItemRecords() {
		return orderExecuteChargeItemRecords;
	}

	public void setOrderExecuteChargeItemRecords(
			List<OrderExecuteChargeItemRecord> orderExecuteChargeItemRecords) {
		this.orderExecuteChargeItemRecords = orderExecuteChargeItemRecords;
	}

	public void delete() {
		this.getService(ChargeItemRepo.class).delete(this);
	}

}
