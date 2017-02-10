package com.neusoft.hs.portal.swing.ui.forms.forms.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.neusoft.hs.portal.swing.ui.forms.forms.view.FormsFrame;
import com.neusoft.hs.portal.swing.ui.forms.login.controller.LoginController;
import com.neusoft.hs.portal.swing.ui.forms.register.controller.RegisterController;
import com.neusoft.hs.portal.swing.ui.shared.controller.AbstractFrameController;

@Controller
public class FormsController extends AbstractFrameController {

	private FormsFrame mainMenuFrame;
	private LoginController loginController;
	private RegisterController registerController;

	@Autowired
	public FormsController(FormsFrame mainMenuFrame,
			LoginController loginController,
			RegisterController registerController) {
		this.mainMenuFrame = mainMenuFrame;
		this.loginController = loginController;
		this.registerController = registerController;
	}

	public void prepareAndOpenFrame() {
		registerAction(mainMenuFrame.getLoginBtn(), (e) -> openLoginWindow());
		registerAction(mainMenuFrame.getRegisterBtn(),
				(e) -> openRegisterWindow());

		mainMenuFrame.setVisible(true);
	}

	private void openLoginWindow() {
		loginController.prepareAndOpenFrame();
	}

	private void openRegisterWindow() {
		registerController.prepareAndOpenFrame();
	}

}
