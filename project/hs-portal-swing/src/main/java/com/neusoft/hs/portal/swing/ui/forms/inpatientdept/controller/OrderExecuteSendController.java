package com.neusoft.hs.portal.swing.ui.forms.inpatientdept.controller;

import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;

import com.neusoft.hs.domain.order.OrderExecute;
import com.neusoft.hs.domain.orderexecute.OrderExecuteAppService;
import com.neusoft.hs.platform.exception.HsException;
import com.neusoft.hs.portal.framework.security.UserUtil;
import com.neusoft.hs.portal.swing.ui.forms.inpatientdept.view.OrderExecuteSendFrame;
import com.neusoft.hs.portal.swing.ui.shared.controller.AbstractFrameController;
import com.neusoft.hs.portal.swing.ui.shared.model.OrderExecuteTableModel;
import com.neusoft.hs.portal.swing.util.Notifications;

@Controller
public class OrderExecuteSendController extends AbstractFrameController {

	@Autowired
	private OrderExecuteSendFrame orderExecuteSendFrame;

	@Autowired
	private OrderExecuteAppService orderExecuteAppService;

	@PostConstruct
	private void prepareListeners() {
		registerAction(orderExecuteSendFrame.getConfirmBtn(), (e) -> send());
		registerAction(orderExecuteSendFrame.getCloseBtn(),
				(e) -> closeWindow());

	}

	@Override
	public void prepareAndOpenFrame() throws HsException {
		loadOrderExecutes();
		orderExecuteSendFrame.setVisible(true);
	}

	private void loadOrderExecutes() throws HsException {
		Pageable pageable = new PageRequest(0, Integer.MAX_VALUE);

		List<OrderExecute> entities = orderExecuteAppService
				.getNeedSendOrderExecutes(UserUtil.getUser(), pageable);
		OrderExecuteTableModel orderExecuteTableModel = orderExecuteSendFrame
				.getOrderExecuteTableModel();
		orderExecuteTableModel.clear();
		orderExecuteTableModel.addEntities(entities);
	}

	private void send() {
		try {
			List<OrderExecute> orderExecutes = this.orderExecuteSendFrame
					.getSelectedOrderExecutes();

			for (OrderExecute orderExecute : orderExecutes) {
				orderExecuteAppService.send(orderExecute.getId(),
						UserUtil.getUser());
			}
			loadOrderExecutes();

		} catch (Exception e) {
			e.printStackTrace();
			Notifications.showFormValidationAlert(e.getMessage());
		}
	}

	private void closeWindow() {
		orderExecuteSendFrame.dispose();
	}
}
