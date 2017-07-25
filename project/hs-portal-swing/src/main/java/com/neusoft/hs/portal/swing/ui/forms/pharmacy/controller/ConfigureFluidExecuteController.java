package com.neusoft.hs.portal.swing.ui.forms.pharmacy.controller;

import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;

import com.neusoft.hs.application.pharmacy.ConfigureFluidAppService;
import com.neusoft.hs.domain.orderexecute.OrderExecuteAppService;
import com.neusoft.hs.domain.organization.Dept;
import com.neusoft.hs.domain.organization.InPatientAreaDept;
import com.neusoft.hs.domain.organization.OrganizationAdminDomainService;
import com.neusoft.hs.domain.pharmacy.ConfigureFluidAdminService;
import com.neusoft.hs.domain.pharmacy.ConfigureFluidBatch;
import com.neusoft.hs.domain.pharmacy.ConfigureFluidOrder;
import com.neusoft.hs.domain.pharmacy.Pharmacy;
import com.neusoft.hs.domain.pharmacy.PharmacyDomainService;
import com.neusoft.hs.platform.exception.HsException;
import com.neusoft.hs.portal.framework.security.UserUtil;
import com.neusoft.hs.portal.swing.ui.forms.pharmacy.view.ConfigureFluidExecuteFrame;
import com.neusoft.hs.portal.swing.ui.shared.controller.AbstractFrameController;
import com.neusoft.hs.portal.swing.ui.shared.model.ConfigureFluidBatchComboBoxModel;
import com.neusoft.hs.portal.swing.ui.shared.model.ConfigureFluidOrderTableModel;
import com.neusoft.hs.portal.swing.ui.shared.model.InPatientAreaDeptComboBoxModel;
import com.neusoft.hs.portal.swing.util.Notifications;

@Controller
public class ConfigureFluidExecuteController extends AbstractFrameController {

	@Autowired
	private ConfigureFluidExecuteFrame configureFluidExecuteFrame;

	@Autowired
	private OrderExecuteAppService orderExecuteAppService;

	@Autowired
	private OrganizationAdminDomainService organizationAdminDomainService;

	@Autowired
	private PharmacyDomainService pharmacyDomainService;

	@Autowired
	private ConfigureFluidAppService configureFluidAppService;

	@Autowired
	private ConfigureFluidAdminService configureFluidAdminService;

	@PostConstruct
	private void prepareListeners() {
		registerAction(configureFluidExecuteFrame.getConfigureFluidBatchCB(), (
				e) -> loadConfigureFluidOrders());
		registerAction(configureFluidExecuteFrame.getInPatientAreaDeptCB(),
				(e) -> loadConfigureFluidOrders());
		registerAction(configureFluidExecuteFrame.getCreateBtn(),
				(e) -> createConfigureFluidOrder());
		registerAction(configureFluidExecuteFrame.getCloseBtn(),
				(e) -> closeWindow());
	}

	@Override
	public void prepareAndOpenFrame() throws HsException {
		loadConfigureFluidBatchs();
		loadInPatientAreaDepts();
		loadConfigureFluidOrders();
		configureFluidExecuteFrame.setVisible(true);
	}

	private void loadConfigureFluidBatchs() throws HsException {

		Dept dept = UserUtil.getUser().getDept();

		List<ConfigureFluidBatch> entities = configureFluidAdminService
				.findConfigureFluidBatchs((Pharmacy) dept);

		ConfigureFluidBatchComboBoxModel batchComboBoxModel = configureFluidExecuteFrame
				.getConfigureFluidBatchComboBoxModel();

		batchComboBoxModel.clear();
		batchComboBoxModel.addElements(entities);
	}

	private void loadInPatientAreaDepts() throws HsException {

		Pageable pageable = new PageRequest(0, Integer.MAX_VALUE);

		List<InPatientAreaDept> entities = organizationAdminDomainService
				.findInPatientArea(pageable);

		InPatientAreaDeptComboBoxModel inPatientAreaComboBoxModel = configureFluidExecuteFrame
				.getInPatientAreaDeptComboBoxModel();

		inPatientAreaComboBoxModel.clear();
		inPatientAreaComboBoxModel.addElements(entities);
	}

	private void loadConfigureFluidOrders() {
		Pageable pageable = new PageRequest(0, Integer.MAX_VALUE);

		InPatientAreaDept area = configureFluidExecuteFrame
				.getInPatientAreaDeptComboBoxModel().getSelectedItem();

		ConfigureFluidBatch batch = configureFluidExecuteFrame
				.getConfigureFluidBatchComboBoxModel().getSelectedItem();

		if (area != null && batch != null) {
			List<ConfigureFluidOrder> entities = configureFluidAppService.find(
					area, batch, pageable);

			ConfigureFluidOrderTableModel dispensingDrugOrderTableModel = this.configureFluidExecuteFrame
					.getConfigureFluidOrderTableModel();
			dispensingDrugOrderTableModel.clear();
			dispensingDrugOrderTableModel.addEntities(entities);
		}
	}

	private void createConfigureFluidOrder() {

		InPatientAreaDept area = configureFluidExecuteFrame
				.getInPatientAreaDeptComboBoxModel().getSelectedItem();

		ConfigureFluidBatch batch = configureFluidExecuteFrame
				.getConfigureFluidBatchComboBoxModel().getSelectedItem();

		if (area != null && batch != null) {

			try {
				ConfigureFluidOrder fluidOrder = configureFluidAppService
						.print(area, batch, UserUtil.getUser());
				if (fluidOrder == null) {
					Notifications.showFormValidationAlert("没有配液任务");
				} else {
					loadConfigureFluidOrders();
				}
			} catch (HsException e) {
				e.printStackTrace();
				Notifications.showFormValidationAlert(e.getMessage());
			}

		}
	}

	private void closeWindow() {
		configureFluidExecuteFrame.dispose();
	}

}
