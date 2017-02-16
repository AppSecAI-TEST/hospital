//Source file: F:\\my_workspace\\201611������ҽ�������\\DesignModel\\DesignElement\\domain\\order\\DrugOrderTypeApp.java

package com.neusoft.hs.domain.pharmacy;

import java.util.Date;
import java.util.List;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.neusoft.hs.domain.order.LongOrder;
import com.neusoft.hs.domain.order.OrderException;
import com.neusoft.hs.domain.order.OrderExecute;
import com.neusoft.hs.domain.order.OrderType;
import com.neusoft.hs.domain.order.OrderTypeApp;
import com.neusoft.hs.domain.order.TemporaryOrder;

@Entity
@DiscriminatorValue("Drug")
public class DrugOrderTypeApp extends OrderTypeApp {

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "drug_use_mode_id")
	public DrugUseMode drugUseMode;

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

}
