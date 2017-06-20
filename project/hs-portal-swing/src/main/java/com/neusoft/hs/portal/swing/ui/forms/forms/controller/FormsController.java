package com.neusoft.hs.portal.swing.ui.forms.forms.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.neusoft.hs.platform.exception.HsException;
import com.neusoft.hs.portal.swing.ui.forms.cashier.controller.CashierController;
import com.neusoft.hs.portal.swing.ui.forms.forms.view.FormsFrame;
import com.neusoft.hs.portal.swing.ui.forms.inpatientdept.controller.OrderExecuteSendController;
import com.neusoft.hs.portal.swing.ui.forms.inpatientdept.controller.OrderVerifyController;
import com.neusoft.hs.portal.swing.ui.forms.inpatientdept.controller.ReceiveVisitController;
import com.neusoft.hs.portal.swing.ui.forms.login.controller.LoginController;
import com.neusoft.hs.portal.swing.ui.forms.order.controller.OrderController;
import com.neusoft.hs.portal.swing.ui.forms.register.controller.RegisterController;
import com.neusoft.hs.portal.swing.ui.shared.controller.AbstractFrameController;
import com.neusoft.hs.portal.swing.util.Notifications;

@Controller
public class FormsController extends AbstractFrameController {

	@Autowired
	private FormsFrame mainMenuFrame;

	@Autowired
	private LoginController loginController;

	@Autowired
	private RegisterController registerController;

	@Autowired
	private CashierController cashierController;

	@Autowired
	private ReceiveVisitController receiveController;

	@Autowired
	private OrderController orderController;

	@Autowired
	private OrderVerifyController orderVerifyController;

	@Autowired
	private OrderExecuteSendController orderExecuteSendController;

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
		registerAction(mainMenuFrame.getVerifyOrderBtn(),
				(e) -> openVerifyOrderWindow());
		registerAction(mainMenuFrame.getSendOrderExecuteBtn(),
				(e) -> openSendOrderExecuteWindow());

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
			orderController.prepareAndOpenFrame();
		} catch (HsException e) {
			e.printStackTrace();
			Notifications.showFormValidationAlert(e.getMessage());
		}
	}

	private void openVerifyOrderWindow() {
		try {
			orderVerifyController.prepareAndOpenFrame();
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

}
