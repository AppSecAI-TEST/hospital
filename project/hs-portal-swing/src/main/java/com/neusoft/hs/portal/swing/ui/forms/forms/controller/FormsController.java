package com.neusoft.hs.portal.swing.ui.forms.forms.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.neusoft.hs.platform.exception.HsException;
import com.neusoft.hs.portal.swing.ui.forms.cashier.controller.CashierController;
import com.neusoft.hs.portal.swing.ui.forms.forms.view.FormsFrame;
import com.neusoft.hs.portal.swing.ui.forms.inpatientdept.controller.ReceiveController;
import com.neusoft.hs.portal.swing.ui.forms.login.controller.LoginController;
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
	private ReceiveController receiveController;
	
	@Autowired
	private CashierController cashierController;

	public void prepareAndOpenFrame() {
		registerAction(mainMenuFrame.getLoginBtn(), (e) -> openLoginWindow());
		registerAction(mainMenuFrame.getRegisterBtn(),
				(e) -> openRegisterWindow());
		registerAction(mainMenuFrame.getCashierBtn(),
				(e) -> openCashierWindow());
		registerAction(mainMenuFrame.getReceiveBtn(),
				(e) -> openReceiveWindow());
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

}
