package com.neusoft.hs.portal.swing.ui.forms.pharmacy.controller;

import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;

import com.neusoft.hs.domain.order.OrderExecute;
import com.neusoft.hs.domain.orderexecute.OrderExecuteAppService;
import com.neusoft.hs.domain.organization.Dept;
import com.neusoft.hs.domain.organization.UserAdminDomainService;
import com.neusoft.hs.domain.pharmacy.DispensingDrugBatch;
import com.neusoft.hs.domain.pharmacy.Pharmacy;
import com.neusoft.hs.domain.pharmacy.PharmacyAdminService;
import com.neusoft.hs.domain.pharmacy.PharmacyDomainService;
import com.neusoft.hs.platform.exception.HsException;
import com.neusoft.hs.portal.framework.security.UserUtil;
import com.neusoft.hs.portal.swing.ui.forms.pharmacy.view.PharmacyExecuteFrame;
import com.neusoft.hs.portal.swing.ui.shared.controller.AbstractFrameController;
import com.neusoft.hs.portal.swing.ui.shared.model.DispensingDrugBatchComboBoxModel;
import com.neusoft.hs.portal.swing.ui.shared.model.OrderExecuteTableModel;

@Controller
public class PharmacyExecuteController extends AbstractFrameController {

	@Autowired
	private PharmacyExecuteFrame pharmacyExecuteFrame;

	@Autowired
	private OrderExecuteAppService orderExecuteAppService;

	@Autowired
	private UserAdminDomainService userAdminDomainService;

	@Autowired
	private PharmacyDomainService pharmacyDomainService;

	@Autowired
	private PharmacyAdminService pharmacyAdminService;

	@PostConstruct
	private void prepareListeners() {
		registerAction(pharmacyExecuteFrame.getCloseBtn(), (e) -> closeWindow());
	}

	@Override
	public void prepareAndOpenFrame() throws HsException {
		loadDispenseDrugWins();
		pharmacyExecuteFrame.setVisible(true);
	}

	private void loadDispenseDrugWins() throws HsException {

		Dept dept = UserUtil.getUser().getDept();

		List<DispensingDrugBatch> entities = pharmacyAdminService
				.findDispensingDrugBatchs((Pharmacy) dept);

		DispensingDrugBatchComboBoxModel batchComboBoxModel = pharmacyExecuteFrame
				.getDispensingDrugBatchComboBoxModel();

		batchComboBoxModel.clear();
		batchComboBoxModel.addElement(null);
		batchComboBoxModel.addElements(entities);
	}

	private void loadOrderExecutes() throws HsException {
		Pageable pageable = new PageRequest(0, Integer.MAX_VALUE);

		List<OrderExecute> entities = orderExecuteAppService
				.getNeedExecuteOrderExecutes(UserUtil.getUser(), pageable);

		OrderExecuteTableModel orderExecuteTableModel = this.pharmacyExecuteFrame
				.getOrderExecuteTableModel();
		orderExecuteTableModel.clear();
		orderExecuteTableModel.addEntities(entities);
	}

	private void closeWindow() {
		pharmacyExecuteFrame.dispose();
	}

}
