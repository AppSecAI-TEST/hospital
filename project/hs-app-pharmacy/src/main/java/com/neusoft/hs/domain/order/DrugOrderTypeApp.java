//Source file: F:\\my_workspace\\201611������ҽ�������\\DesignModel\\DesignElement\\domain\\order\\DrugOrderTypeApp.java

package com.neusoft.hs.domain.order;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import com.neusoft.hs.domain.order.OrderType;
import com.neusoft.hs.domain.order.OrderTypeApp;
import com.neusoft.hs.domain.pharmacy.DrugType;
import com.neusoft.hs.domain.pharmacy.DrugUseMode;

@Entity
@DiscriminatorValue("Drug")
public class DrugOrderTypeApp extends OrderTypeApp {

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "drug_use_mode_id")
	public DrugUseMode drugUseMode;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "drug_type_id")
	private DrugType drugType;

	public DrugOrderTypeApp() {
		super();
	}

	public DrugOrderTypeApp(OrderType orderType, DrugUseMode drugUseMode) {
		super(orderType);
		this.drugUseMode = drugUseMode;
	}

	public DrugUseMode getDrugUseMode() {
		return drugUseMode;
	}

	public void setDrugUseMode(DrugUseMode drugUseMode) {
		this.drugUseMode = drugUseMode;
	}

	public DrugType getDrugType() {
		return drugType;
	}

	public void setDrugType(DrugType drugType) {
		this.drugType = drugType;
	}
}
