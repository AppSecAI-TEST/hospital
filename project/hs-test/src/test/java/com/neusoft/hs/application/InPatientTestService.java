package com.neusoft.hs.application;

import java.util.HashMap;

import org.springframework.stereotype.Service;

import com.neusoft.hs.domain.pharmacy.DrugUseModeAssistMaterial;

@Service
public class InPatientTestService extends AppTestService {

	@Override
	public void testInit() {
		super.testInit();

		choice();

		ready();
	}

	private void choice() {

		this.choices = new HashMap<ChoiceItem, Object>();
		this.choices.put(ChoiceItem.OrderUseModeAssistMaterial,
				onlyOneOrderUseModeAssistMaterial);
		this.choices.put(ChoiceItem.CancelHC, true);
	}

	private void ready() {
		this.choiceOrderUseModeAssistMaterial((DrugUseModeAssistMaterial) this.choices
				.get(ChoiceItem.OrderUseModeAssistMaterial));
	}

	private void choiceOrderUseModeAssistMaterial(
			DrugUseModeAssistMaterial orderUseModeAssistMaterial) {
		pharmacyDomainService
				.createOrderUseModeAssistMaterial(orderUseModeAssistMaterial);
	}

}
