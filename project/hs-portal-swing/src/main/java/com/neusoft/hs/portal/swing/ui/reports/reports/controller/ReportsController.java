package com.neusoft.hs.portal.swing.ui.reports.reports.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.neusoft.hs.portal.swing.ui.reports.cost.controller.ChargeRecordReportController;
import com.neusoft.hs.portal.swing.ui.reports.reports.view.ReportsFrame;
import com.neusoft.hs.portal.swing.ui.shared.controller.AbstractFrameController;
import com.neusoft.hs.portal.swing.util.Notifications;

@Controller
public class ReportsController extends AbstractFrameController {

	@Autowired
	private ReportsFrame mainMenuFrame;

	@Autowired
	private ChargeRecordReportController chargeRecordReportController;

	public void prepareAndOpenFrame() {
		registerAction(mainMenuFrame.getChargeRecordReportBtn(),
				(e) -> openChargeRecordReportWindow());
		mainMenuFrame.setVisible(true);
	}

	private void openChargeRecordReportWindow() {
		try {
			chargeRecordReportController.prepareAndOpenFrame();
		} catch (Exception e) {
			e.printStackTrace();
			Notifications.showFormValidationAlert(e.getMessage());
		}
	}
}
