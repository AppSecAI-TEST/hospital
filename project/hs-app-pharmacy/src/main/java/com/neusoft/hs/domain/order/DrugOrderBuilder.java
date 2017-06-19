package com.neusoft.hs.domain.order;

import com.neusoft.hs.application.order.AbstractOrderBuilder;
import com.neusoft.hs.domain.pharmacy.DrugUseMode;
import com.neusoft.hs.domain.pharmacy.Pharmacy;
import com.neusoft.hs.platform.util.DateUtil;

public class DrugOrderBuilder extends AbstractOrderBuilder {

	private Pharmacy pharmacy;

	private Integer count;

	private DrugUseMode drugUseMode;

	public void setCount(Integer count) {
		this.count = count;
	}

	public void setPharmacy(Pharmacy pharmacy) {
		this.pharmacy = pharmacy;
	}

	public void setDrugUseMode(DrugUseMode drugUseMode) {
		this.drugUseMode = drugUseMode;
	}

	@Override
	public OrderCreateCommand createCommand() {
		
		TemporaryOrder order = new TemporaryOrder();
		
		order.setVisit(visit);
		order.setName(orderType.getName());
		order.setOrderType(orderType);
		order.setPlanStartDate(DateUtil.getSysDate());
		order.setPlaceType(placeType);

		order.setCount(count);

		order.setTypeApp(new DrugOrderTypeApp(pharmacy, drugUseMode));

		return order;
	}

}
