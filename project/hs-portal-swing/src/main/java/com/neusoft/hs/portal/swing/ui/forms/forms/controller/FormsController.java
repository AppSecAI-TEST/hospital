package com.neusoft.hs.portal.swing.ui.forms.forms.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.neusoft.hs.platform.exception.HsException;
import com.neusoft.hs.portal.swing.ui.forms.cashier.controller.CashierController;
import com.neusoft.hs.portal.swing.ui.forms.forms.view.FormsFrame;
import com.neusoft.hs.portal.swing.ui.forms.inpatientdept.controller.ArrangementMedicalRecordController;
import com.neusoft.hs.portal.swing.ui.forms.inpatientdept.controller.CreateMedicalRecordController;
import com.neusoft.hs.portal.swing.ui.forms.inpatientdept.controller.MaintainTreatmentController;
import com.neusoft.hs.portal.swing.ui.forms.inpatientdept.controller.OrderExecuteSendController;
import com.neusoft.hs.portal.swing.ui.forms.inpatientdept.controller.ReceiveVisitController;
import com.neusoft.hs.portal.swing.ui.forms.inpatientdept.controller.TransferMedicalRecordController;
import com.neusoft.hs.portal.swing.ui.forms.inpatientdept.controller.VerifyOrderController;
import com.neusoft.hs.portal.swing.ui.forms.login.controller.LoginController;
import com.neusoft.hs.portal.swing.ui.forms.order.controller.CreateOrderController;
import com.neusoft.hs.portal.swing.ui.forms.order.controller.OrderExecuteFinishController;
import com.neusoft.hs.portal.swing.ui.forms.recordroom.controller.ArchiveMedicalRecordController;
import com.neusoft.hs.portal.swing.ui.forms.recordroom.controller.QualityControlController;
import com.neusoft.hs.portal.swing.ui.forms.register.controller.RegisterController;
import com.neusoft.hs.portal.swing.ui.forms.registration.controller.RegistrationController;
import com.neusoft.hs.portal.swing.ui.shared.controller.AbstractFrameController;
import com.neusoft.hs.portal.swing.util.Notifications;

@Controller
public class FormsController extends AbstractFrameController {

	@Autowired
	private FormsFrame mainMenuFrame;

	@Autowired
	private LoginController loginController;

	@Autowired
	private RegistrationController registrationController;

	@Autowired
	private RegisterController registerController;

	@Autowired
	private CashierController cashierController;

	@Autowired
	private ReceiveVisitController receiveController;

	@Autowired
	private CreateOrderController createOrderController;

	@Autowired
	private MaintainTreatmentController maintainTreatmentController;

	@Autowired
	private CreateMedicalRecordController createMedicalRecordController;

	@Autowired
	private VerifyOrderController verifyOrderController;

	@Autowired
	private OrderExecuteSendController orderExecuteSendController;

	@Autowired
	private OrderExecuteFinishController orderExecuteFinishController;

	@Autowired
	private ArrangementMedicalRecordController arrangementMedicalRecordController;

	@Autowired
	private TransferMedicalRecordController transferMedicalRecordController;

	@Autowired
	private QualityControlController qualityControlController;

	@Autowired
	private ArchiveMedicalRecordController archiveMedicalRecordController;

	public void prepareAndOpenFrame() {
		registerAction(mainMenuFrame.getLoginBtn(), (e) -> openLoginWindow());
		registerAction(mainMenuFrame.getCreateVoucherBtn(),
				(e) -> openCreateVoucherWindow());
		registerAction(mainMenuFrame.getRegisterBtn(),
				(e) -> openRegisterWindow());
		registerAction(mainMenuFrame.getCashierBtn(),
				(e) -> openCashierWindow());
		registerAction(mainMenuFrame.getReceiveBtn(),
				(e) -> openReceiveWindow());
		registerAction(mainMenuFrame.getCreateOrderBtn(),
				(e) -> openCreateOrderWindow());
		registerAction(mainMenuFrame.getVerifyOrderBtn(),
				(e) -> openVerifyOrderWindow());
		registerAction(mainMenuFrame.getSendOrderExecuteBtn(),
				(e) -> openSendOrderExecuteWindow());
		registerAction(mainMenuFrame.getFinishOrderExecuteBtn(),
				(e) -> openFinishOrderExecuteWindow());
		registerAction(mainMenuFrame.getMaintainTreatmentBtn(),
				(e) -> openMaintainTreatmentWindow());
		registerAction(mainMenuFrame.getArrangementMedicalRecordBtn(),
				(e) -> openArrangementMedicalRecordWindow());
		registerAction(mainMenuFrame.getTransferMedicalRecordBtn(),
				(e) -> openTransferMedicalRecordWindow());
		registerAction(mainMenuFrame.getQualityControlBtn(),
				(e) -> openQualityControlWindow());
		registerAction(mainMenuFrame.getArchiveMedicalRecordBtn(),
				(e) -> openArchiveMedicalRecordWindow());

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

	private void openRegisterWindow() {
		try {
			registerController.prepareAndOpenFrame();
		} catch (HsException e) {
			e.printStackTrace();
			Notifications.showFormValidationAlert(e.getMessage());
		}
	}

	private void openCashierWindow() {
		try {
			cashierController.prepareAndOpenFrame();
		} catch (HsException e) {
			e.printStackTrace();
			Notifications.showFormValidationAlert(e.getMessage());
		}
	}

	private void openReceiveWindow() {
		try {
			receiveController.prepareAndOpenFrame();
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

	private void openVerifyOrderWindow() {
		try {
			verifyOrderController.prepareAndOpenFrame();
		} catch (HsException e) {
			e.printStackTrace();
			Notifications.showFormValidationAlert(e.getMessage());
		}
	}

	private void openSendOrderExecuteWindow() {
		try {
			orderExecuteSendController.prepareAndOpenFrame();
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

	private void openArrangementMedicalRecordWindow() {
		try {
			arrangementMedicalRecordController.prepareAndOpenFrame();
		} catch (HsException e) {
			e.printStackTrace();
			Notifications.showFormValidationAlert(e.getMessage());
		}
	}

	private void openTransferMedicalRecordWindow() {
		try {
			transferMedicalRecordController.prepareAndOpenFrame();
		} catch (HsException e) {
			e.printStackTrace();
			Notifications.showFormValidationAlert(e.getMessage());
		}
	}

	private void openQualityControlWindow() {
		try {
			qualityControlController.prepareAndOpenFrame();
		} catch (HsException e) {
			e.printStackTrace();
			Notifications.showFormValidationAlert(e.getMessage());
		}
	}

	private void openArchiveMedicalRecordWindow() {
		try {
			archiveMedicalRecordController.prepareAndOpenFrame();
		} catch (HsException e) {
			e.printStackTrace();
			Notifications.showFormValidationAlert(e.getMessage());
		}
	}

}
