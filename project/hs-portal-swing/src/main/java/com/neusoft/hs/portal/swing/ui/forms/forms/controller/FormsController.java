package com.neusoft.hs.portal.swing.ui.forms.forms.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.neusoft.hs.portal.swing.ui.forms.cashier.controller.CashierController;
import com.neusoft.hs.portal.swing.ui.forms.forms.view.FormsFrame;
import com.neusoft.hs.portal.swing.ui.forms.inpatientdept.controller.ReceiveController;
import com.neusoft.hs.portal.swing.ui.forms.login.controller.LoginController;
import com.neusoft.hs.portal.swing.ui.forms.register.controller.RegisterController;
import com.neusoft.hs.portal.swing.ui.shared.controller.AbstractFrameController;

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
		loginController.prepareAndOpenFrame();
	}

	private void openRegisterWindow() {
		registerController.prepareAndOpenFrame();
	}

	private void openCashierWindow() {
		cashierController.prepareAndOpenFrame();
	}
	
	private void openReceiveWindow() {
		receiveController.prepareAndOpenFrame();
	}

}
