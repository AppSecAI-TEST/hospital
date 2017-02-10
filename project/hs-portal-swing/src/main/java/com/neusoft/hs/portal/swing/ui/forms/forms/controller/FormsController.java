package com.neusoft.hs.portal.swing.ui.forms.forms.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.neusoft.hs.portal.swing.ui.forms.forms.view.FormsFrame;
import com.neusoft.hs.portal.swing.ui.forms.register.controller.RegisterController;
import com.neusoft.hs.portal.swing.ui.shared.controller.AbstractFrameController;

@Controller
public class FormsController extends AbstractFrameController {

	private FormsFrame mainMenuFrame;
	private RegisterController registerController;

	@Autowired
	public FormsController(FormsFrame mainMenuFrame,
			RegisterController registerController) {
		this.mainMenuFrame = mainMenuFrame;
		this.registerController = registerController;
	}

	public void prepareAndOpenFrame() {
		registerAction(mainMenuFrame.getRegisterBtn(),
				(e) -> openRegisterWindow());
		mainMenuFrame.setVisible(true);
	}

	private void openRegisterWindow() {
		registerController.prepareAndOpenFrame();
	}

}
