package com.neusoft.hs.portal.swing.ui.reports.reports.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.neusoft.hs.portal.swing.ui.reports.register.controller.RegisterReportController;
import com.neusoft.hs.portal.swing.ui.reports.reports.view.ReportsFrame;
import com.neusoft.hs.portal.swing.ui.shared.controller.AbstractFrameController;

@Controller
public class ReportsController extends AbstractFrameController {

	private ReportsFrame mainMenuFrame;
	private RegisterReportController registerReportController;

	@Autowired
	public ReportsController(ReportsFrame mainMenuFrame,
			RegisterReportController registerReportController) {
		this.mainMenuFrame = mainMenuFrame;
		this.registerReportController = registerReportController;
	}

	public void prepareAndOpenFrame() {
		registerAction(mainMenuFrame.getRegisterReportBtn(),
				(e) -> openRegisterReportWindow());
		mainMenuFrame.setVisible(true);
	}

	private void openRegisterReportWindow() {
		registerReportController.prepareAndOpenFrame();
	}
}
