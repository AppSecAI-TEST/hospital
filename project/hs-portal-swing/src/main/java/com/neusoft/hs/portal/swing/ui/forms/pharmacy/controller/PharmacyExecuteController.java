package com.neusoft.hs.portal.swing.ui.forms.pharmacy.controller;

import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;

import com.neusoft.hs.application.pharmacy.InPatientPharmacyAppService;
import com.neusoft.hs.domain.orderexecute.OrderExecuteAppService;
import com.neusoft.hs.domain.organization.Dept;
import com.neusoft.hs.domain.organization.InPatientAreaDept;
import com.neusoft.hs.domain.organization.OrganizationAdminDomainService;
import com.neusoft.hs.domain.pharmacy.DispensingDrugBatch;
import com.neusoft.hs.domain.pharmacy.DispensingDrugOrder;
import com.neusoft.hs.domain.pharmacy.Pharmacy;
import com.neusoft.hs.domain.pharmacy.PharmacyAdminService;
import com.neusoft.hs.domain.pharmacy.PharmacyDomainService;
import com.neusoft.hs.platform.exception.HsException;
import com.neusoft.hs.portal.framework.security.UserUtil;
import com.neusoft.hs.portal.swing.ui.forms.pharmacy.view.PharmacyExecuteFrame;
import com.neusoft.hs.portal.swing.ui.shared.controller.AbstractFrameController;
import com.neusoft.hs.portal.swing.ui.shared.model.DispensingDrugBatchComboBoxModel;
import com.neusoft.hs.portal.swing.ui.shared.model.DispensingDrugOrderTableModel;
import com.neusoft.hs.portal.swing.ui.shared.model.InPatientAreaDeptComboBoxModel;

@Controller
public class PharmacyExecuteController extends AbstractFrameController {

	@Autowired
	private PharmacyExecuteFrame pharmacyExecuteFrame;

	@Autowired
	private OrderExecuteAppService orderExecuteAppService;

	@Autowired
	private OrganizationAdminDomainService organizationAdminDomainService;

	@Autowired
	private PharmacyDomainService pharmacyDomainService;

	@Autowired
	private InPatientPharmacyAppService inPatientPharmacyAppService;

	@Autowired
	private PharmacyAdminService pharmacyAdminService;

	@PostConstruct
	private void prepareListeners() {
		registerAction(pharmacyExecuteFrame.getCloseBtn(), (e) -> closeWindow());
	}

	@Override
	public void prepareAndOpenFrame() throws HsException {
		loadDispensingDrugBatchs();
		loadInPatientAreaDepts();
		loadDispensingDrugOrders();
		pharmacyExecuteFrame.setVisible(true);
	}

	private void loadDispensingDrugBatchs() throws HsException {

		Dept dept = UserUtil.getUser().getDept();

		List<DispensingDrugBatch> entities = pharmacyAdminService
				.findDispensingDrugBatchs((Pharmacy) dept);

		DispensingDrugBatchComboBoxModel batchComboBoxModel = pharmacyExecuteFrame
				.getDispensingDrugBatchComboBoxModel();

		batchComboBoxModel.clear();
		batchComboBoxModel.addElements(entities);
	}

	private void loadInPatientAreaDepts() throws HsException {

		Pageable pageable = new PageRequest(0, Integer.MAX_VALUE);

		List<InPatientAreaDept> entities = organizationAdminDomainService
				.findInPatientArea(pageable);

		InPatientAreaDeptComboBoxModel inPatientAreaComboBoxModel = pharmacyExecuteFrame
				.getInPatientAreaDeptComboBoxModel();

		inPatientAreaComboBoxModel.clear();
		inPatientAreaComboBoxModel.addElements(entities);
	}

	private void loadDispensingDrugOrders() throws HsException {
		Pageable pageable = new PageRequest(0, Integer.MAX_VALUE);

		InPatientAreaDept area = pharmacyExecuteFrame
				.getInPatientAreaDeptComboBoxModel().getSelectedItem();

		DispensingDrugBatch batch = pharmacyExecuteFrame
				.getDispensingDrugBatchComboBoxModel().getSelectedItem();

		if (area != null && batch != null) {
			List<DispensingDrugOrder> entities = inPatientPharmacyAppService
					.find(area, batch, pageable);

			DispensingDrugOrderTableModel dispensingDrugOrderTableModel = this.pharmacyExecuteFrame
					.getDispensingDrugOrderTableModel();
			dispensingDrugOrderTableModel.clear();
			dispensingDrugOrderTableModel.addEntities(entities);
		}
	}

	private void closeWindow() {
		pharmacyExecuteFrame.dispose();
	}

}
