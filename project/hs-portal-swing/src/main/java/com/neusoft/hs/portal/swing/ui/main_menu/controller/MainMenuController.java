package com.neusoft.hs.portal.swing.ui.main_menu.controller;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.neusoft.hs.portal.swing.ui.forms.forms.controller.FormsController;
import com.neusoft.hs.portal.swing.ui.main_menu.view.MainMenuFrame;
import com.neusoft.hs.portal.swing.ui.reports.reports.controller.ReportsController;
import com.neusoft.hs.portal.swing.ui.shared.controller.AbstractFrameController;

@Controller
public class MainMenuController extends AbstractFrameController {

	@Autowired
	private MainMenuFrame mainMenuFrame;

	@Autowired
	private FormsController formsController;

	@Autowired
	private ReportsController reportsController;
	
	@Autowired
	private UpdateSysDateController updateSysDateController;

	@Autowired
	private LogoController logoController;

	public void prepareAndOpenFrame() {
		registerAction(mainMenuFrame.getFormsBtn(), (e) -> openFormsWindow());
		registerAction(mainMenuFrame.getReportsBtn(),
				(e) -> openReportsWindow());
		registerAction(mainMenuFrame.getUpdateSysDateBtn(),
				(e) -> openUpdateSysDateWindow());
		registerAction(mainMenuFrame.getLogoLabel(), new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				openLogoWindow(e);
			}
		});

		mainMenuFrame.setVisible(true);
	}

	private void openFormsWindow() {
		formsController.prepareAndOpenFrame();
	}

	private void openReportsWindow() {
		reportsController.prepareAndOpenFrame();
	}
	
	private void openUpdateSysDateWindow(){
		updateSysDateController.prepareAndOpenFrame();
	}

	private void openLogoWindow(MouseEvent e) {
		logoController.prepareAndOpenFrame();
	}

}
