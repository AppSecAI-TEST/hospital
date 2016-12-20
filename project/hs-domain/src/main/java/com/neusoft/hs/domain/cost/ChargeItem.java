//Source file: F:\\my_workspace\\201611������ҽ�������\\DesignModel\\DesignElement\\domain\\cost\\ChargeItem.java

package com.neusoft.hs.domain.cost;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotEmpty;

import com.neusoft.hs.domain.pharmacy.DrugType;
import com.neusoft.hs.domain.pharmacy.DrugTypeSpec;
import com.neusoft.hs.platform.entity.IdEntity;

@Entity
@Table(name = "domain_charge_item")
public class ChargeItem extends IdEntity {

	@NotEmpty(message = "代码不能为空")
	@Column(length = 16)
	private String code;

	private float price;

	@Column(name = "charging_mode", length = 16)
	private String chargingMode;

	@OneToOne(mappedBy = "chargeItem", cascade = { CascadeType.ALL })
	private DrugTypeSpec drugTypeSepc;

	@OneToMany(mappedBy = "chargeItem", cascade = { CascadeType.ALL })
	private List<ChargeRecord> chargeRecords;

	@OneToMany(mappedBy = "chargeItem", cascade = { CascadeType.ALL })
	private List<VisitChargeItem> visitChargeItems;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
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

}
