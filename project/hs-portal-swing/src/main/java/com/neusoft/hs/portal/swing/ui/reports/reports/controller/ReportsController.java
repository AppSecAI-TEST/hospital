package com.neusoft.hs.portal.swing.ui.reports.reports.controller;

import javax.swing.JOptionPane;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.neusoft.hs.platform.util.DateUtil;
import com.neusoft.hs.portal.swing.ui.reports.cost.controller.ChargeRecordReportController;
import com.neusoft.hs.portal.swing.ui.reports.order.controller.OrderController;
import com.neusoft.hs.portal.swing.ui.reports.order.controller.OrderExecuteController;
import com.neusoft.hs.portal.swing.ui.reports.reports.view.ReportsFrame;
import com.neusoft.hs.portal.swing.ui.reports.treatment.controller.TreatmentReportController;
import com.neusoft.hs.portal.swing.ui.reports.visit.controller.VisitLogController;
import com.neusoft.hs.portal.swing.ui.shared.controller.AbstractFrameController;
import com.neusoft.hs.portal.swing.util.Notifications;
import com.neusoft.hs.test.PatientMainTestService;

@Controller
public class ReportsController extends AbstractFrameController {

	@Autowired
	private ReportsFrame mainMenuFrame;

	@Autowired
	private VisitLogController visitLogController;

	@Autowired
	private ChargeRecordReportController chargeRecordReportController;

	@Autowired
	private OrderController orderController;

	@Autowired
	private OrderExecuteController orderExecuteController;

	@Autowired
	private TreatmentReportController treatmentReportController;

	@Autowired
	private PatientMainTestService patientMainTestService;

	public void prepareAndOpenFrame() {
		registerAction(mainMenuFrame.getVisitLogBtn(),
				(e) -> openVisitLogWindow());
		registerAction(mainMenuFrame.getChargeRecordReportBtn(),
				(e) -> openChargeRecordReportWindow());
		registerAction(mainMenuFrame.getOrderBtn(), (e) -> openOrderWindow());
		registerAction(mainMenuFrame.getOrderExecuteBtn(),
				(e) -> openOrderExecuteWindow());
		registerAction(mainMenuFrame.getTreatmentBtn(),
				(e) -> openTreatmentWindow());

		registerAction(mainMenuFrame.getRunTestBtn(), (e) -> runTest());

		mainMenuFrame.setVisible(true);
	}

	private void openVisitLogWindow() {
		try {
			visitLogController.prepareAndOpenFrame();
		} catch (Exception e) {
			e.printStackTrace();
			Notifications.showFormValidationAlert(e.getMessage());
		}
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

	private void openTreatmentWindow() {
		try {
			treatmentReportController.prepareAndOpenFrame();
		} catch (Exception e) {
			e.printStackTrace();
			Notifications.showFormValidationAlert(e.getMessage());
		}
	}

	private void runTest() {
		int result = JOptionPane.showConfirmDialog(null,
				"该操作将清空已有数据，按着测试场景生成一份新数据", "标题",
				JOptionPane.YES_NO_CANCEL_OPTION);

		if (result == JOptionPane.YES_OPTION) {
			try {
				mainMenuFrame.getTipLbl().setText("初始化基础数据");
				patientMainTestService.testInit();

				mainMenuFrame.getTipLbl().setText("执行测试场景");
				patientMainTestService.execute();

				DateUtil.clearSysDate();
				mainMenuFrame.getTipLbl().setText(null);
			} catch (Exception e) {
				mainMenuFrame.getTipLbl().setText(null);
				e.printStackTrace();
				Notifications.showFormValidationAlert(e.getMessage());
			}
		}
	}
}
