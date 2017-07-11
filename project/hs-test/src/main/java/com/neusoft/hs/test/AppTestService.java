package com.neusoft.hs.test;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.neusoft.hs.application.cashier.CashierAppService;
import com.neusoft.hs.application.cost.CostAppService;
import com.neusoft.hs.application.inpatientdept.InPatientAppService;
import com.neusoft.hs.application.inspect.InspectAppService;
import com.neusoft.hs.application.medicalrecord.MedicalRecordAppService;
import com.neusoft.hs.application.order.OrderAppService;
import com.neusoft.hs.application.outpatientdept.OutPatientDeptAppService;
import com.neusoft.hs.application.pharmacy.ConfigureFluidAppService;
import com.neusoft.hs.application.recordroom.QualityControlAppService;
import com.neusoft.hs.application.register.RegisterAppService;
import com.neusoft.hs.application.registration.RegistrationAppService;
import com.neusoft.hs.application.treatment.TreatmentAppService;
import com.neusoft.hs.application.visit.VisitAppService;
import com.neusoft.hs.data.init.ChoiceItem;
import com.neusoft.hs.data.init.DataIniter;
import com.neusoft.hs.domain.order.OrderDAO;
import com.neusoft.hs.domain.order.OrderUtil;
import com.neusoft.hs.domain.orderexecute.OrderExecuteAppService;
import com.neusoft.hs.domain.pharmacy.DrugUseModeAssistMaterial;
import com.neusoft.hs.engine.visit.VisitFacade;
import com.neusoft.hs.platform.exception.HsException;

@Service
public abstract class AppTestService extends DataIniter {

	@Autowired
	protected OrderUtil orderUtil;

	@Autowired
	protected TestUtil testUtil;

	@Autowired
	protected RegisterAppService registerAppService;

	@Autowired
	protected CashierAppService cashierAppService;

	@Autowired
	protected InPatientAppService inPatientAppService;

	@Autowired
	protected OrderAppService orderAppService;

	@Autowired
	protected OrderExecuteAppService orderExecuteAppService;

	@Autowired
	protected InspectAppService inspectAppService;

	@Autowired
	protected CostAppService costAppService;

	@Autowired
	protected VisitAppService visitAppService;

	@Autowired
	protected TreatmentAppService treatmentAppService;

	@Autowired
	protected MedicalRecordAppService medicalRecordAppService;

	@Autowired
	protected QualityControlAppService qualityControlAppService;

	@Autowired
	protected MedicalRecordTestService medicalRecordTestService;

	@Autowired
	protected RegistrationAppService registrationAppService;

	@Autowired
	protected OutPatientDeptAppService outPatientDeptAppService;

	@Autowired
	protected VisitFacade visitFacade;

	@Autowired
	protected ConfigureFluidAppService configureFluidAppService;

	@Autowired
	protected OrderDAO orderDAO;

	@Autowired
	protected CostTestService costTestService;

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
	public void execute() throws HsException {
	}

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
