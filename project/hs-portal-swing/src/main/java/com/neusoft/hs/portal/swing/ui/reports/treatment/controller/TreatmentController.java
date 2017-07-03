package com.neusoft.hs.portal.swing.ui.reports.treatment.controller;

import java.awt.GridLayout;
import java.awt.event.ItemEvent;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;

import com.neusoft.hs.domain.treatment.TreatmentDomainService;
import com.neusoft.hs.domain.treatment.TreatmentItemSpec;
import com.neusoft.hs.domain.visit.Visit;
import com.neusoft.hs.domain.visit.VisitAdminDomainService;
import com.neusoft.hs.platform.exception.HsException;
import com.neusoft.hs.portal.swing.ui.reports.treatment.view.TreamentFrame;
import com.neusoft.hs.portal.swing.ui.shared.controller.AbstractFrameController;
import com.neusoft.hs.portal.swing.ui.shared.model.VisitComboBoxModel;
import com.neusoft.hs.portal.swing.util.ConstMessagesCN;

@Controller
public class TreatmentController extends AbstractFrameController {

	@Autowired
	private TreamentFrame treamentFrame;

	@Autowired
	private VisitAdminDomainService visitAdminDomainService;

	@Autowired
	private TreatmentDomainService treatmentDomainService;

	@PostConstruct
	private void prepareListeners() {
		registerAction(treamentFrame.getVisitCB(), (e) -> refreshTreatment(e));
		registerAction(treamentFrame.getCloseBtn(), (e) -> closeWindow());

	}

	@Override
	public void prepareAndOpenFrame() throws HsException {
		loadVisits();
		loadTreatmentSpecs();
		treamentFrame.setVisible(true);
	}

	private void loadVisits() throws HsException {
		Pageable pageable = new PageRequest(0, Integer.MAX_VALUE);

		List<Visit> entities = visitAdminDomainService.find(pageable);

		VisitComboBoxModel visitComboBoxModel = this.treamentFrame
				.getVisitComboBoxModel();
		visitComboBoxModel.clear();
		visitComboBoxModel.addElement(null);
		visitComboBoxModel.addElements(entities);
	}

	private void loadTreatmentSpecs() {
		Pageable pageable = new PageRequest(0, Integer.MAX_VALUE);
		List<TreatmentItemSpec> specs = this.treatmentDomainService
				.getAllTreatmentItemSpecs(pageable);

		treamentFrame.showTreatment(specs);
	}

	public void refreshTreatment(ItemEvent e) {
		Visit visit = (Visit) e.getItem();

		if (visit != null) {

		}
	}

	private void closeWindow() {
		treamentFrame.dispose();
	}
}
