package com.neusoft.hs.portal.swing.ui.reports.reports.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.neusoft.hs.portal.swing.ui.reports.cost.controller.ChargeRecordReportController;
import com.neusoft.hs.portal.swing.ui.reports.order.controller.OrderController;
import com.neusoft.hs.portal.swing.ui.reports.order.controller.OrderExecuteController;
import com.neusoft.hs.portal.swing.ui.reports.reports.view.ReportsFrame;
import com.neusoft.hs.portal.swing.ui.shared.controller.AbstractFrameController;
import com.neusoft.hs.portal.swing.util.Notifications;

@Controller
public class ReportsController extends AbstractFrameController {

	@Autowired
	private ReportsFrame mainMenuFrame;

	@Autowired
	private ChargeRecordReportController chargeRecordReportController;

	@Autowired
	private OrderController orderController;

	@Autowired
	private OrderExecuteController orderExecuteController;

	public void prepareAndOpenFrame() {
		registerAction(mainMenuFrame.getChargeRecordReportBtn(),
				(e) -> openChargeRecordReportWindow());
		registerAction(mainMenuFrame.getOrderBtn(), (e) -> openOrderWindow());
		registerAction(mainMenuFrame.getOrderExecuteBtn(),
				(e) -> openOrderExecuteWindow());

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

	private void openOrderWindow() {
		try {
			orderController.prepareAndOpenFrame();
		} catch (Exception e) {
			e.printStackTrace();
			Notifications.showFormValidationAlert(e.getMessage());
		}
	}

	private void openOrderExecuteWindow() {
		try {
			orderExecuteController.prepareAndOpenFrame();
		} catch (Exception e) {
			e.printStackTrace();
			Notifications.showFormValidationAlert(e.getMessage());
		}
	}
}
