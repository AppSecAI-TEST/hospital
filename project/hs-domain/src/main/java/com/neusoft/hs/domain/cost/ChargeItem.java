//Source file: F:\\my_workspace\\201611������ҽ�������\\DesignModel\\DesignElement\\domain\\cost\\ChargeItem.java

package com.neusoft.hs.domain.cost;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotEmpty;

import com.neusoft.hs.domain.order.OrderExecute;
import com.neusoft.hs.domain.order.OrderType;
import com.neusoft.hs.domain.order.OrderUseMode;
import com.neusoft.hs.domain.pharmacy.DrugTypeSpec;
import com.neusoft.hs.platform.entity.SuperEntity;

@Entity
@Table(name = "domain_charge_item")
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

	@OneToOne(mappedBy = "chargeItem", cascade = { CascadeType.ALL })
	private DrugTypeSpec drugTypeSepc;

	@OneToMany(mappedBy = "chargeItem", cascade = { CascadeType.ALL })
	private List<ChargeRecord> chargeRecords;

	@OneToMany(mappedBy = "chargeItem", cascade = { CascadeType.ALL })
	private List<OrderType> orderTypes;

	@ManyToMany(mappedBy = "chargeItems", cascade = { CascadeType.ALL })
	private List<OrderUseMode> useModes;

	@ManyToMany(mappedBy = "chargeItems", cascade = { CascadeType.ALL })
	private List<OrderExecute> executes;

	@OneToMany(mappedBy = "chargeItem", cascade = { CascadeType.ALL })
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

	public DrugTypeSpec getDrugTypeSepc() {
		return drugTypeSepc;
	}

	public void setDrugTypeSepc(DrugTypeSpec drugTypeSepc) {
		this.drugTypeSepc = drugTypeSepc;
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

	public List<OrderUseMode> getUseModes() {
		return useModes;
	}

	public void setUseModes(List<OrderUseMode> useModes) {
		this.useModes = useModes;
	}

	public List<OrderExecute> getExecutes() {
		return executes;
	}

	public void setExecutes(List<OrderExecute> executes) {
		this.executes = executes;
	}

}
