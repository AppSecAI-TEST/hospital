package com.neusoft.hs.portal.swing.ui.forms.cashier.controller;

import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;

import com.neusoft.hs.application.cashier.CashierAppService;
import com.neusoft.hs.domain.visit.Visit;
import com.neusoft.hs.portal.swing.ui.forms.cashier.model.NeedInitAccountVisitTableModel;
import com.neusoft.hs.portal.swing.ui.forms.cashier.view.CashierTableFrame;
import com.neusoft.hs.portal.swing.ui.shared.controller.AbstractFrameController;

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

		Pageable pageable = new PageRequest(0, Integer.MAX_VALUE);

		List<Visit> entities = cashierAppService
				.getNeedInitAccountVisits(pageable);
		tableModel.clear();
		tableModel.addEntities(entities);

		cashierTableFrame.setVisible(true);
	}

	private void initAccount() {
		// TODO Auto-generated method stub
	}

}
