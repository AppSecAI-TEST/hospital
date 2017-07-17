package com.neusoft.hs.portal.swing.ui.main_menu.controller;

import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.swing.JOptionPane;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.neusoft.hs.platform.exception.HsException;
import com.neusoft.hs.platform.util.DateUtil;
import com.neusoft.hs.portal.swing.ui.main_menu.view.UpdateSysDateFrame;
import com.neusoft.hs.portal.swing.ui.shared.controller.AbstractFrameController;
import com.neusoft.hs.portal.swing.util.Notifications;

@Controller
public class UpdateSysDateController extends AbstractFrameController {

	@Autowired
	private UpdateSysDateFrame updateSysDateFrame;

	@PostConstruct
	private void prepareListeners() {
		registerAction(updateSysDateFrame.getConfirmBtn(),
				(e) -> updateSysDate());
		registerAction(updateSysDateFrame.getCloseBtn(), (e) -> closeWindow());
	}

	@Override
	public void prepareAndOpenFrame() {
		updateSysDateFrame.setVisible(true);
	}

	private void updateSysDate() {

		int result = JOptionPane.showConfirmDialog(null,
				"该操作将自动计算整天的自动计划任务并执行", "标题", JOptionPane.YES_NO_OPTION);

		if (result == JOptionPane.YES_OPTION) {
			try {
				Date newDate = updateSysDateFrame.getSysDateSD().getDate();
				Date oldDate = DateUtil.getSysDate();

				if (newDate.before(oldDate)) {
					throw new HsException("系统时间不能向过去调整");
				}

				List<Date> dayStarts = DateUtil.calDayStart(oldDate, newDate);
				for(Date dayStart : dayStarts){
					DateUtil.setSysDate(dayStart);
					
					
				}

			} catch (Exception e) {
				e.printStackTrace();
				Notifications.showFormValidationAlert(e.getMessage());
			}
		}

	}

	private void closeWindow() {
		updateSysDateFrame.dispose();
	}

}
