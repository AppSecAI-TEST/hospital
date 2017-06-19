package com.neusoft.hs.domain.order;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.neusoft.hs.application.order.AbstractOrderBuilder;
import com.neusoft.hs.domain.diagnosis.DiagnosisTreatmentItemValue;
import com.neusoft.hs.domain.pharmacy.DrugUseMode;
import com.neusoft.hs.domain.pharmacy.Pharmacy;
import com.neusoft.hs.domain.pharmacy.Prescription;

public class PrescriptionBuilder extends AbstractOrderBuilder {

	private Pharmacy pharmacy;

	private Map<OrderType, Integer> counts = new LinkedHashMap<OrderType, Integer>();

	private DrugUseMode drugUseMode;

	private String illustrate;

	private List<DiagnosisTreatmentItemValue> diagnosisTreatmentItemValues = new ArrayList<DiagnosisTreatmentItemValue>();

	public void setPharmacy(Pharmacy pharmacy) {
		this.pharmacy = pharmacy;
	}

	public void addCount(OrderType orderType, Integer count) {
		this.counts.put(orderType, count);
	}

	public void setDrugUseMode(DrugUseMode drugUseMode) {
		this.drugUseMode = drugUseMode;
	}

	public void setIllustrate(String illustrate) {
		this.illustrate = illustrate;
	}

	public void addDiagnosisTreatmentItemValue(DiagnosisTreatmentItemValue value) {
		this.diagnosisTreatmentItemValues.add(value);
	}

	@Override
	public OrderCreateCommand createCommand() throws OrderException {

		Prescription prescription = new Prescription();
		prescription.setVisit(visit);
		prescription.setIllustrate(illustrate);

		for (DiagnosisTreatmentItemValue value : this.diagnosisTreatmentItemValues) {
			prescription.addDiagnosisTreatmentItemValue(value);
		}

		TemporaryOrder order;

		for (OrderType orderType : counts.keySet()) {

			order = new TemporaryOrder();
			order.setVisit(visit);
			order.setName(orderType.getName());
			order.setOrderType(orderType);
			order.setPlanStartDate(getPlanStartDate());
			order.setPlaceType(placeType);
			order.setCount(counts.get(orderType));
			order.setTypeApp(new DrugOrderTypeApp(pharmacy, drugUseMode));

			prescription.addOrder(order);
		}

		return prescription;
	}
}
