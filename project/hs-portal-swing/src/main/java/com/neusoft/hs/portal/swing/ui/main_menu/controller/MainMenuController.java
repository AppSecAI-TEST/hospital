package com.neusoft.hs.portal.swing.ui.main_menu.controller;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.neusoft.hs.portal.framework.util.SysDateService;
import com.neusoft.hs.portal.swing.ui.forms.forms.controller.InPatientController;
import com.neusoft.hs.portal.swing.ui.forms.forms.controller.OutPatientController;
import com.neusoft.hs.portal.swing.ui.main_menu.view.MainMenuFrame;
import com.neusoft.hs.portal.swing.ui.reports.reports.controller.MaintainController;
import com.neusoft.hs.portal.swing.ui.shared.controller.AbstractFrameController;

@Controller
public class MainMenuController extends AbstractFrameController {

	@Autowired
	private MainMenuFrame mainMenuFrame;

	@Autowired
	private InPatientController inPatientController;
	
	@Autowired
	private OutPatientController outPatientController;

	@Autowired
	private MaintainController maintainController;

	@Autowired
	private UpdateSysDateController updateSysDateController;

	@Autowired
	private LogoController logoController;
	
	@Autowired
	private SysDateService sysDateService;

	public void prepareAndOpenFrame() {
		
		sysDateService.init();
		
		registerAction(mainMenuFrame.getOutPatientBtn(),
				(e) -> openOutPatientWindow());
		registerAction(mainMenuFrame.getInPatientBtn(),
				(e) -> openInPatientWindow());
		registerAction(mainMenuFrame.getMaintainBtn(),
				(e) -> openMaintainWindow());
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
	
	private void openOutPatientWindow() {
		outPatientController.prepareAndOpenFrame();
	}

	private void openInPatientWindow() {
		inPatientController.prepareAndOpenFrame();
	}

	private void openMaintainWindow() {
		maintainController.prepareAndOpenFrame();
	}

	private void openUpdateSysDateWindow() {
		updateSysDateController.prepareAndOpenFrame();
	}

	private void openLogoWindow(MouseEvent e) {
		logoController.prepareAndOpenFrame();
	}

}
