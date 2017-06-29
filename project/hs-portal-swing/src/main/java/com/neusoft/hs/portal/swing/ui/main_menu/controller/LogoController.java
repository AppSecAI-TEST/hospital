package com.neusoft.hs.portal.swing.ui.main_menu.controller;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.neusoft.hs.application.cashier.CashierAppService;
import com.neusoft.hs.platform.exception.HsException;
import com.neusoft.hs.portal.swing.ui.main_menu.view.LogoFrame;
import com.neusoft.hs.portal.swing.ui.shared.controller.AbstractFrameController;

@Controller
public class LogoController extends AbstractFrameController {

	@Autowired
	private CashierAppService cashierAppService;

	@Autowired
	private LogoFrame logoFrame;

	@PostConstruct
	private void prepareListeners() {
		registerAction(logoFrame.getCloseBtn(), (e) -> closeWindow());
	}

	@Override
	public void prepareAndOpenFrame() {
		logoFrame.setVisible(true);
	}

	private void closeWindow() {
		logoFrame.dispose();
	}

}
