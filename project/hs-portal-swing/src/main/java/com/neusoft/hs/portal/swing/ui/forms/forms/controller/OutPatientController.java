package com.neusoft.hs.portal.swing.ui.forms.forms.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.neusoft.hs.platform.exception.HsException;
import com.neusoft.hs.portal.swing.ui.forms.forms.view.OutPatientFrame;
import com.neusoft.hs.portal.swing.ui.forms.login.controller.LoginController;
import com.neusoft.hs.portal.swing.ui.forms.medicalrecord.controller.CreateMedicalRecordController;
import com.neusoft.hs.portal.swing.ui.forms.order.controller.CreateOrderController;
import com.neusoft.hs.portal.swing.ui.forms.order.controller.OrderExecuteFinishController;
import com.neusoft.hs.portal.swing.ui.forms.outpatientdept.controller.OutPatientDeptController;
import com.neusoft.hs.portal.swing.ui.forms.registration.controller.RegistrationController;
import com.neusoft.hs.portal.swing.ui.forms.treatment.controller.MaintainTreatmentController;
import com.neusoft.hs.portal.swing.ui.shared.controller.AbstractFrameController;
import com.neusoft.hs.portal.swing.util.Notifications;

@Controller
public class OutPatientController extends AbstractFrameController {

	@Autowired
	private OutPatientFrame mainMenuFrame;

	@Autowired
	private LoginController loginController;

	@Autowired
	private RegistrationController registrationController;

	@Autowired
	private OutPatientDeptController outPatientDeptController;

	@Autowired
	private CreateOrderController createOrderController;

	@Autowired
	private MaintainTreatmentController maintainTreatmentController;

	@Autowired
	private CreateMedicalRecordController createMedicalRecordController;

	@Autowired
	private OrderExecuteFinishController orderExecuteFinishController;

	public void prepareAndOpenFrame() {
		registerAction(mainMenuFrame.getLoginBtn(), (e) -> openLoginWindow());
		registerAction(mainMenuFrame.getCreateVoucherBtn(),
				(e) -> openCreateVoucherWindow());
		registerAction(mainMenuFrame.getNextVoucherBtn(),
				(e) -> openNextVoucherWindow());
		registerAction(mainMenuFrame.getCreateOrderBtn(),
				(e) -> openCreateOrderWindow());
		registerAction(mainMenuFrame.getFinishOrderExecuteBtn(),
				(e) -> openFinishOrderExecuteWindow());
		registerAction(mainMenuFrame.getMaintainTreatmentBtn(),
				(e) -> openMaintainTreatmentWindow());
		registerAction(mainMenuFrame.getCreateMedicalRecordBtn(),
				(e) -> openCreateMedicalRecordWindow());

		mainMenuFrame.setVisible(true);
	}

	private void openLoginWindow() {
		try {
			loginController.prepareAndOpenFrame();
		} catch (HsException e) {
			e.printStackTrace();
			Notifications.showFormValidationAlert(e.getMessage());
		}
	}

	private void openCreateVoucherWindow() {
		try {
			registrationController.prepareAndOpenFrame();
		} catch (HsException e) {
			e.printStackTrace();
			Notifications.showFormValidationAlert(e.getMessage());
		}
	}

	private void openNextVoucherWindow() {
		try {
			outPatientDeptController.prepareAndOpenFrame();
		} catch (HsException e) {
			e.printStackTrace();
			Notifications.showFormValidationAlert(e.getMessage());
		}
	}

	private void openCreateOrderWindow() {
		try {
			createOrderController.prepareAndOpenFrame();
		} catch (HsException e) {
			e.printStackTrace();
			Notifications.showFormValidationAlert(e.getMessage());
		}
	}

	private void openFinishOrderExecuteWindow() {
		try {
			orderExecuteFinishController.prepareAndOpenFrame();
		} catch (HsException e) {
			e.printStackTrace();
			Notifications.showFormValidationAlert(e.getMessage());
		}
	}

	private void openMaintainTreatmentWindow() {
		try {
			maintainTreatmentController.prepareAndOpenFrame();
		} catch (HsException e) {
			e.printStackTrace();
			Notifications.showFormValidationAlert(e.getMessage());
		}
	}

	private void openCreateMedicalRecordWindow() {
		try {
			createMedicalRecordController.prepareAndOpenFrame();
		} catch (HsException e) {
			e.printStackTrace();
			Notifications.showFormValidationAlert(e.getMessage());
		}
	}
}
