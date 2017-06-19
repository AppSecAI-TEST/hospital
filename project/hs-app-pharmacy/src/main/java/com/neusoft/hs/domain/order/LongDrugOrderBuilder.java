package com.neusoft.hs.domain.order;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import com.neusoft.hs.application.order.AbstractOrderBuilder;
import com.neusoft.hs.domain.pharmacy.DrugUseMode;
import com.neusoft.hs.domain.pharmacy.Pharmacy;

public class LongDrugOrderBuilder extends AbstractOrderBuilder {

	private Pharmacy pharmacy;

	private DrugUseMode drugUseMode;

	private Map<OrderType, Integer> counts = new LinkedHashMap<OrderType, Integer>();

	private OrderFrequencyType frequencyType;

	private Date planEndDate;

	public void setPharmacy(Pharmacy pharmacy) {
		this.pharmacy = pharmacy;
	}

	public void setDrugUseMode(DrugUseMode drugUseMode) {
		this.drugUseMode = drugUseMode;
	}

	public void addCount(OrderType orderType, Integer count) {
		this.counts.put(orderType, count);
	}

	public void setFrequencyType(OrderFrequencyType frequencyType) {
		this.frequencyType = frequencyType;
	}

	public void setPlanEndDate(Date planEndDate) {
		this.planEndDate = planEndDate;
	}

	@Override
	public OrderCreateCommand createCommand() throws OrderException {

		CompsiteOrder compsiteOrder = new CompsiteOrder();
		LongOrder order;

		for (OrderType orderType : counts.keySet()) {

			order = new LongOrder();
			order.setVisit(visit);
			order.setName(orderType.getName());
			order.setOrderType(orderType);
			order.setPlanStartDate(getPlanStartDate());
			order.setPlanEndDate(planEndDate);
			order.setFrequencyType(frequencyType);
			order.setPlaceType(placeType);
			
			order.setExecuteDept(executeDept);
			
			order.setCount(counts.get(orderType));

			order.setTypeApp(new DrugOrderTypeApp(pharmacy, drugUseMode));

			compsiteOrder.addOrder(order);
		}

		return compsiteOrder;

	}

}
