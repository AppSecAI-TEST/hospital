package com.neusoft.hs.portal.swing.ui.main_menu.controller;

import java.util.Date;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.neusoft.hs.platform.util.DateUtil;
import com.neusoft.hs.portal.swing.ui.main_menu.view.UpdateSysDateFrame;
import com.neusoft.hs.portal.swing.ui.shared.controller.AbstractFrameController;

@Controller
public class UpdateSysDateController extends AbstractFrameController {

	@Autowired
	private UpdateSysDateFrame updateSysDateFrame;

	@PostConstruct
	private void prepareListeners() {
		registerAction(updateSysDateFrame.getConfirmBtn(), (e) -> updateSysDate());
		registerAction(updateSysDateFrame.getCloseBtn(), (e) -> closeWindow());
	}

	@Override
	public void prepareAndOpenFrame() {
		updateSysDateFrame.setVisible(true);
	}
	
	private void updateSysDate(){
		Date sysDate = updateSysDateFrame.getSysDateSD().getDate();
		Date oldDate = DateUtil.getSysDate();
		
		
	}

	private void closeWindow() {
		updateSysDateFrame.dispose();
	}

}
