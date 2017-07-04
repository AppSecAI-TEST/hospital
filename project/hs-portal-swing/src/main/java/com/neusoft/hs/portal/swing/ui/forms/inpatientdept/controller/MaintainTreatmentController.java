package com.neusoft.hs.portal.swing.ui.forms.inpatientdept.controller;

import java.awt.event.ItemEvent;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.swing.JButton;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;

import com.neusoft.hs.application.inpatientdept.InPatientAppService;
import com.neusoft.hs.domain.treatment.TreatmentDomainService;
import com.neusoft.hs.domain.treatment.TreatmentException;
import com.neusoft.hs.domain.treatment.TreatmentItem;
import com.neusoft.hs.domain.treatment.TreatmentItemSpec;
import com.neusoft.hs.domain.visit.Visit;
import com.neusoft.hs.domain.visit.VisitAdminDomainService;
import com.neusoft.hs.platform.exception.HsException;
import com.neusoft.hs.portal.framework.security.UserUtil;
import com.neusoft.hs.portal.swing.ui.forms.inpatientdept.view.MaintainTreatmentFrame;
import com.neusoft.hs.portal.swing.ui.shared.controller.AbstractFrameController;
import com.neusoft.hs.portal.swing.ui.shared.model.VisitComboBoxModel;
import com.neusoft.hs.portal.swing.util.Notifications;

@Controller
public class MaintainTreatmentController extends AbstractFrameController {

	@Autowired
	private MaintainTreatmentFrame maintainTreatmentFrame;

	@Autowired
	private InPatientAppService inPatientAppService;

	@Autowired
	private TreatmentDomainService treatmentDomainService;

	private List<TreatmentItemSpec> specs;

	@PostConstruct
	private void prepareListeners() {
		registerAction(maintainTreatmentFrame.getVisitCB(),
				(e) -> refreshTreatment(e));
		registerAction(maintainTreatmentFrame.getCloseBtn(),
				(e) -> closeWindow());
	}

	@Override
	public void prepareAndOpenFrame() throws HsException {
		loadVisits();
		loadTreatmentSpecs();
		maintainTreatmentFrame.setVisible(true);
	}

	private void loadVisits() throws HsException {
		Pageable pageable = new PageRequest(0, Integer.MAX_VALUE);

		List<Visit> entities = inPatientAppService.listVisit(
				UserUtil.getUser(), pageable);

		VisitComboBoxModel visitComboBoxModel = maintainTreatmentFrame
				.getVisitComboBoxModel();
		visitComboBoxModel.clear();
		visitComboBoxModel.addElement(null);
		visitComboBoxModel.addElements(entities);
	}

	private void loadTreatmentSpecs() {
		Pageable pageable = new PageRequest(0, Integer.MAX_VALUE);
		specs = this.treatmentDomainService.getAllTreatmentItemSpecs(pageable);

		maintainTreatmentFrame.showTreatment(specs);

		for (JButton button : maintainTreatmentFrame.getButtons().keySet()) {
			TreatmentItemSpec spec = maintainTreatmentFrame.getButtons().get(
					button);
			registerAction(button, (e) -> createTreatment(spec));
		}
	}

	public void refreshTreatment(ItemEvent e) {
		Visit visit = (Visit) e.getItem();

		if (visit != null) {
			refreshTreatment(visit);
		}
	}

	private void refreshTreatment(Visit visit) {
		for (TreatmentItemSpec spec : specs) {
			TreatmentItem item = this.treatmentDomainService
					.getTheTreatmentItem(visit, spec);
			maintainTreatmentFrame.showTheTreatment(spec, item);
		}
	}

	private void createTreatment(TreatmentItemSpec spec) {
		VisitComboBoxModel visitComboBoxModel = maintainTreatmentFrame
				.getVisitComboBoxModel();
		Visit visit = visitComboBoxModel.getSelectedItem();
		if (visit == null) {
			Notifications.showFormValidationAlert("请选择患者");
		}
		try {
			treatmentDomainService.create(spec.createTreatmentItem(visit));
			refreshTreatment(visit);
		} catch (TreatmentException e) {
			e.printStackTrace();
			Notifications.showFormValidationAlert(e.getMessage());
		}

	}

	private void closeWindow() {
		maintainTreatmentFrame.dispose();
	}
}
