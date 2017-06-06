package com.neusoft.hs.test;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import com.neusoft.hs.domain.order.OrderUtil;
import com.neusoft.hs.domain.pharmacy.DrugUseModeAssistMaterial;
import com.neusoft.hs.platform.bean.ApplicationContextUtil;
import com.neusoft.hs.platform.exception.HsException;

@Service
public abstract class AppTestService extends DataIniter {

	@Autowired
	protected OrderUtil orderUtil;

	@Autowired
	protected TestUtil testUtil;

	public void testInit() {
		clear();

		initData();

		choice();

		ready();
	}

	/**
	 * 
	 * @throws HsException
	 */
	public abstract void execute() throws HsException;

	public void choice() {

		this.choices = new HashMap<ChoiceItem, Object>();
		this.choices.put(ChoiceItem.OrderUseModeAssistMaterialToInPatient,
				everyOneOrderUseModeAssistMaterialToInPatient);
		this.choices.put(ChoiceItem.OrderUseModeAssistMaterialToOutPatient,
				everyOneOrderUseModeAssistMaterialToOutPatient);
		this.choices.put(ChoiceItem.CancelHC, true);
	}

	public void ready() {
		this.choiceOrderUseModeAssistMaterial((DrugUseModeAssistMaterial) this.choices
				.get(ChoiceItem.OrderUseModeAssistMaterialToInPatient));
		this.choiceOrderUseModeAssistMaterial((DrugUseModeAssistMaterial) this.choices
				.get(ChoiceItem.OrderUseModeAssistMaterialToOutPatient));
	}

	private void choiceOrderUseModeAssistMaterial(
			DrugUseModeAssistMaterial orderUseModeAssistMaterial) {
		// 持久化
		pharmacyAdminService
				.createOrderUseModeAssistMaterial(orderUseModeAssistMaterial);
		// 同步内存
		orderUseModeAssistMaterial.getOrderUseMode()
				.addOrderUseModeAssistMaterial(orderUseModeAssistMaterial);
	}

}
