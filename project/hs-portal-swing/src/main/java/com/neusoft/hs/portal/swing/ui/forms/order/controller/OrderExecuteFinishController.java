package com.neusoft.hs.portal.swing.ui.forms.order.controller;

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
import com.neusoft.hs.portal.swing.ui.forms.order.view.OrderExecuteFinishFrame;
import com.neusoft.hs.portal.swing.ui.shared.controller.AbstractFrameController;
import com.neusoft.hs.portal.swing.ui.shared.model.OrderExecuteTableModel;
import com.neusoft.hs.portal.swing.util.Notifications;

@Controller
public class OrderExecuteFinishController extends AbstractFrameController {

	@Autowired
	private OrderExecuteFinishFrame orderExecuteFinishFrame;

	@Autowired
	private OrderExecuteAppService orderExecuteAppService;

	@Autowired
	private OrderExecuteTableModel orderExecuteTableModel;

	@PostConstruct
	private void prepareListeners() {
		registerAction(orderExecuteFinishFrame.getConfirmBtn(), (e) -> finish());
	}

	@Override
	public void prepareAndOpenFrame() throws HsException {
		loadOrderExecutes();
		orderExecuteFinishFrame.setVisible(true);
	}

	private void loadOrderExecutes() throws HsException {
		Pageable pageable = new PageRequest(0, Integer.MAX_VALUE);

		List<OrderExecute> entities = orderExecuteAppService
				.getNeedExecuteOrderExecutes(UserUtil.getUser(), pageable);

		orderExecuteTableModel.clear();
		orderExecuteTableModel.addEntities(entities);
	}

	private void finish() {
		try {
			OrderExecute orderExecute = this.orderExecuteFinishFrame
					.getSelectedOrderExecute();

			orderExecuteAppService.finish(orderExecute.getId(), null,
					UserUtil.getUser());

			loadOrderExecutes();

		} catch (Exception e) {
			e.printStackTrace();
			Notifications.showFormValidationAlert(e.getMessage());
		}
	}
}
