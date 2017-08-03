package com.neusoft.hs.portal.swing.ui.reports.treatment.controller;

import java.awt.event.ItemEvent;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;

import com.neusoft.hs.domain.treatment.TreatmentDomainService;
import com.neusoft.hs.domain.treatment.TreatmentItem;
import com.neusoft.hs.domain.treatment.TreatmentItemSpec;
import com.neusoft.hs.domain.visit.Visit;
import com.neusoft.hs.domain.visit.VisitAdminDomainService;
import com.neusoft.hs.platform.exception.HsException;
import com.neusoft.hs.portal.swing.ui.reports.treatment.view.TreamentReportFrame;
import com.neusoft.hs.portal.swing.ui.shared.controller.AbstractFrameController;
import com.neusoft.hs.portal.swing.ui.shared.model.VisitComboBoxModel;
import com.neusoft.hs.portal.swing.util.Notifications;

@Controller
public class TreatmentReportController extends AbstractFrameController {

	@Autowired
	private TreamentReportFrame treamentReportFrame;

	@Autowired
	private VisitAdminDomainService visitAdminDomainService;

	@Autowired
	private TreatmentDomainService treatmentDomainService;

	private List<TreatmentItemSpec> specs;

	@PostConstruct
	private void prepareListeners() {
		registerAction(treamentReportFrame.getNextPageBtn(), (e) -> nextPage());
		registerAction(treamentReportFrame.getVisitCB(), (e) -> refreshTreatment(e));
		registerAction(treamentReportFrame.getCloseBtn(), (e) -> closeWindow());

	}

	@Override
	public void prepareAndOpenFrame() throws HsException {
		loadVisits();
		loadTreatmentSpecs();
		treamentReportFrame.setVisible(true);
	}
	
	public void nextPage() {
		this.treamentReportFrame.nextPage();
		try {
			this.loadVisits();
		} catch (HsException e) {
			e.printStackTrace();
			Notifications.showFormValidationAlert(e.getMessage());
		}
	}

	private void loadVisits() throws HsException {
		Pageable pageable = new PageRequest(this.treamentReportFrame.getPageNumber(),
				15);

		List<Visit> entities = visitAdminDomainService.find(pageable);

		VisitComboBoxModel visitComboBoxModel = this.treamentReportFrame
				.getVisitComboBoxModel();
		visitComboBoxModel.clear();
		visitComboBoxModel.addElement(null);
		visitComboBoxModel.addElements(entities);
	}

	private void loadTreatmentSpecs() {
		Pageable pageable = new PageRequest(0, Integer.MAX_VALUE);
		specs = this.treatmentDomainService.getAllTreatmentItemSpecs(pageable);

		treamentReportFrame.showTreatment(specs);
	}

	public void refreshTreatment(ItemEvent e) {
		Visit visit = (Visit) e.getItem();

		if (visit != null) {
			for (TreatmentItemSpec spec : specs) {
				TreatmentItem item = this.treatmentDomainService
						.getTheTreatmentItem(visit, spec);
				treamentReportFrame.showTheTreatment(spec, item);
			}
		}
	}

	private void closeWindow() {
		treamentReportFrame.dispose();
	}
}
