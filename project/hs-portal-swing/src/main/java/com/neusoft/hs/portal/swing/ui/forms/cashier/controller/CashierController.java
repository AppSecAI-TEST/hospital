package com.neusoft.hs.portal.swing.ui.forms.cashier.controller;

import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;

import com.neusoft.hs.application.cashier.CashierAppService;
import com.neusoft.hs.domain.visit.Visit;
import com.neusoft.hs.platform.exception.HsException;
import com.neusoft.hs.portal.security.UserUtil;
import com.neusoft.hs.portal.swing.ui.forms.cashier.model.NeedInitAccountVisitTableModel;
import com.neusoft.hs.portal.swing.ui.forms.cashier.view.CashierTableFrame;
import com.neusoft.hs.portal.swing.ui.shared.controller.AbstractFrameController;
import com.neusoft.hs.portal.swing.util.Notifications;

@Controller
public class CashierController extends AbstractFrameController {

	@Autowired
	private CashierAppService cashierAppService;

	@Autowired
	private CashierTableFrame cashierTableFrame;

	@Autowired
	private NeedInitAccountVisitTableModel tableModel;

	@PostConstruct
	private void prepareListeners() {
		registerAction(cashierTableFrame.getConfirmBtn(), (e) -> initAccount());
	}

	@Override
	public void prepareAndOpenFrame() {
		loadNeedInitAccountVisits();
		cashierTableFrame.setVisible(true);
	}

	private void loadNeedInitAccountVisits() {
		Pageable pageable = new PageRequest(0, Integer.MAX_VALUE);

		List<Visit> entities = cashierAppService
				.getNeedInitAccountVisits(pageable);
		tableModel.clear();
		tableModel.addEntities(entities);
	}

	private void initAccount() {
		try {
			String visitId = cashierTableFrame.getSelectedVisitId();
			Float balance = cashierTableFrame.getBalance();
			cashierAppService.initAccount(visitId, balance, UserUtil.getUser());

			loadNeedInitAccountVisits();
			cashierTableFrame.clearBalance();
		} catch (HsException e) {
			e.printStackTrace();
			Notifications.showFormValidationAlert(e.getMessage());
		}
	}

}
