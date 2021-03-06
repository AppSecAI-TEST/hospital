package com.neusoft.hs.portal.swing.ui.forms.forms.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Controller;

import com.neusoft.hs.domain.organization.AbstractUser;
import com.neusoft.hs.platform.exception.HsException;
import com.neusoft.hs.portal.framework.security.LoginEvent;
import com.neusoft.hs.portal.swing.ui.forms.cashier.controller.CashierController;
import com.neusoft.hs.portal.swing.ui.forms.forms.view.InPatientFrame;
import com.neusoft.hs.portal.swing.ui.forms.inpatientdept.controller.ArrangementMedicalRecordController;
import com.neusoft.hs.portal.swing.ui.forms.inpatientdept.controller.OrderExecuteSendController;
import com.neusoft.hs.portal.swing.ui.forms.inpatientdept.controller.ReceiveVisitController;
import com.neusoft.hs.portal.swing.ui.forms.inpatientdept.controller.TransferMedicalRecordController;
import com.neusoft.hs.portal.swing.ui.forms.inpatientdept.controller.VerifyOrderController;
import com.neusoft.hs.portal.swing.ui.forms.login.controller.LoginController;
import com.neusoft.hs.portal.swing.ui.forms.medicalrecord.controller.CreateMedicalRecordController;
import com.neusoft.hs.portal.swing.ui.forms.order.controller.CreateOrderController;
import com.neusoft.hs.portal.swing.ui.forms.order.controller.OrderExecuteFinishController;
import com.neusoft.hs.portal.swing.ui.forms.pharmacy.controller.ConfigureFluidExecuteController;
import com.neusoft.hs.portal.swing.ui.forms.recordroom.controller.ArchiveMedicalRecordController;
import com.neusoft.hs.portal.swing.ui.forms.recordroom.controller.QualityControlController;
import com.neusoft.hs.portal.swing.ui.forms.register.controller.RegisterController;
import com.neusoft.hs.portal.swing.ui.forms.treatment.controller.MaintainTreatmentController;
import com.neusoft.hs.portal.swing.ui.shared.controller.AbstractFrameController;
import com.neusoft.hs.portal.swing.util.ConstMessagesCN;
import com.neusoft.hs.portal.swing.util.Notifications;

@Controller
public class InPatientController extends AbstractFrameController implements
		ApplicationListener<LoginEvent> {

	@Autowired
	private InPatientFrame mainMenuFrame;

	@Autowired
	private LoginController loginController;

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
	private ConfigureFluidExecuteController configureFluidExecuteController;

	@Autowired
	private QualityControlController qualityControlController;

	@Autowired
	private ArchiveMedicalRecordController archiveMedicalRecordController;

	public void prepareAndOpenFrame() {
		registerAction(mainMenuFrame.getLoginBtn(), (e) -> openLoginWindow());
		registerAction(mainMenuFrame.getRegisterBtn(),
				(e) -> openRegisterWindow());
		registerAction(mainMenuFrame.getCashierBtn(),
				(e) -> openCashierWindow());
		registerAction(mainMenuFrame.getReceiveBtn(),
				(e) -> openReceiveWindow());
		registerAction(mainMenuFrame.getCreateOrderBtn(),
				(e) -> openCreateOrderWindow());
		registerAction(mainMenuFrame.getCreateMedicalRecordBtn(),
				(e) -> openCreateMedicalRecordWindow());
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
		registerAction(mainMenuFrame.getConfigureFluidExecuteBtn(),
				(e) -> openConfigureFluidExecuteWindow());
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

	private void openCreateMedicalRecordWindow() {
		try {
			createMedicalRecordController.prepareAndOpenFrame();
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

	private void openConfigureFluidExecuteWindow() {
		try {
			configureFluidExecuteController.prepareAndOpenFrame();
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

	@Override
	public void onApplicationEvent(LoginEvent event) {
		if (mainMenuFrame.isVisible()) {
			AbstractUser user = (AbstractUser) event.getSource();
			if (user != null) {
				mainMenuFrame.getLoginLbl().setText(user.getName());
			} else {
				mainMenuFrame.getLoginLbl().setText(
						ConstMessagesCN.Labels.LogoutState);
			}
		}
	}

}
