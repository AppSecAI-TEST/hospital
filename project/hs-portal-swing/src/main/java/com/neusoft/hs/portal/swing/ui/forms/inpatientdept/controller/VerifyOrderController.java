package com.neusoft.hs.portal.swing.ui.forms.inpatientdept.controller;

import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;

import com.neusoft.hs.application.order.OrderAppService;
import com.neusoft.hs.domain.order.Order;
import com.neusoft.hs.platform.exception.HsException;
import com.neusoft.hs.portal.framework.security.UserUtil;
import com.neusoft.hs.portal.swing.ui.forms.inpatientdept.view.OrderVerifyFrame;
import com.neusoft.hs.portal.swing.ui.shared.controller.AbstractFrameController;
import com.neusoft.hs.portal.swing.ui.shared.model.OrderTableModel;
import com.neusoft.hs.portal.swing.util.Notifications;

@Controller
public class VerifyOrderController extends AbstractFrameController {

	@Autowired
	private OrderVerifyFrame orderVerifyFrame;

	@Autowired
	private OrderAppService orderAppService;

	@PostConstruct
	private void prepareListeners() {
		registerAction(orderVerifyFrame.getConfirmBtn(), (e) -> verify());
		registerAction(orderVerifyFrame.getCloseBtn(), (e) -> closeWindow());

	}

	@Override
	public void prepareAndOpenFrame() throws HsException {
		loadOrders();
		orderVerifyFrame.setVisible(true);
	}

	private void loadOrders() throws HsException {
		Pageable pageable = new PageRequest(0, Integer.MAX_VALUE);

		List<Order> entities = orderAppService.getNeedVerifyOrders(
				UserUtil.getUser(), pageable);

		OrderTableModel orderTableModel = orderVerifyFrame.getOrderTableModel();
		orderTableModel.clear();
		orderTableModel.addEntities(entities);
	}

	private void verify() {
		try {
			List<Order> orders = this.orderVerifyFrame.getSelectedOrders();

			for (Order order : orders) {
				orderAppService.verify(order.getId(), UserUtil.getUser());
			}

			loadOrders();

		} catch (Exception e) {
			e.printStackTrace();
			Notifications.showFormValidationAlert(e.getMessage());
		}
	}

	private void closeWindow() {
		orderVerifyFrame.dispose();
	}
}
