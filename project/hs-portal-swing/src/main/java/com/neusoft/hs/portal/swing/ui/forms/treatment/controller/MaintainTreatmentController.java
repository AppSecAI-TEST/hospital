package com.neusoft.hs.portal.swing.ui.forms.treatment.controller;

import java.awt.event.ItemEvent;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.swing.JButton;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;

import com.neusoft.hs.application.treatment.TreatmentAppService;
import com.neusoft.hs.domain.treatment.SimpleTreatmentItemValue;
import com.neusoft.hs.domain.treatment.TreatmentDomainService;
import com.neusoft.hs.domain.treatment.TreatmentException;
import com.neusoft.hs.domain.treatment.TreatmentItem;
import com.neusoft.hs.domain.treatment.TreatmentItemSpec;
import com.neusoft.hs.domain.visit.Visit;
import com.neusoft.hs.domain.visit.VisitDomainService;
import com.neusoft.hs.platform.exception.HsException;
import com.neusoft.hs.portal.framework.security.UserUtil;
import com.neusoft.hs.portal.swing.ui.forms.treatment.view.MaintainTreatmentFrame;
import com.neusoft.hs.portal.swing.ui.shared.controller.AbstractFrameController;
import com.neusoft.hs.portal.swing.ui.shared.model.VisitComboBoxModel;
import com.neusoft.hs.portal.swing.util.Notifications;

@Controller
public class MaintainTreatmentController extends AbstractFrameController {

	@Autowired
	private MaintainTreatmentFrame maintainTreatmentFrame;

	@Autowired
	private VisitDomainService visitDomainService;

	@Autowired
	private TreatmentDomainService treatmentDomainService;

	@Autowired
	private TreatmentAppService treatmentAppService;

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

		List<String> states = new ArrayList<String>();
		states.add(Visit.State_OutHospital);
		states.add(Visit.State_IntoWard);

		List<Visit> entities = visitDomainService.findByStatesAndDepts(states,
				UserUtil.getUser().getOperationDepts(), pageable);

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

		for (JButton button : maintainTreatmentFrame.getAutoCreateButtons()
				.keySet()) {
			TreatmentItemSpec spec = maintainTreatmentFrame
					.getAutoCreateButtons().get(button);
			registerAction(button, (e) -> autoCreateTreatment(spec));
		}

		for (JButton button : maintainTreatmentFrame.getInputCreateButtons()
				.keySet()) {
			TreatmentItemSpec spec = maintainTreatmentFrame
					.getInputCreateButtons().get(button);
			registerAction(button, (e) -> inputCreateTreatment(spec));
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

	private void autoCreateTreatment(TreatmentItemSpec spec) {
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

	private void inputCreateTreatment(TreatmentItemSpec spec) {

		VisitComboBoxModel visitComboBoxModel = maintainTreatmentFrame
				.getVisitComboBoxModel();
		Visit visit = visitComboBoxModel.getSelectedItem();
		if (visit == null) {
			Notifications.showFormValidationAlert("请选择患者");
		}

		String valueInfo = maintainTreatmentFrame.getTreatments().get(spec)
				.getText();
		if (valueInfo == null || valueInfo.length() == 0) {
			Notifications.showFormValidationAlert("请录入诊疗信息");
		}
		try {
			SimpleTreatmentItemValue value = new SimpleTreatmentItemValue();
			value.setInfo(valueInfo);

			treatmentAppService.create(visit, spec, value, UserUtil.getUser());
			refreshTreatment(visit);
		} catch (TreatmentException e) {
			e.printStackTrace();
			Notifications.showFormValidationAlert(e.getMessage());
		} catch (HsException e) {
			e.printStackTrace();
			Notifications.showFormValidationAlert(e.getMessage());
		}
	}

	private void closeWindow() {
		maintainTreatmentFrame.dispose();
	}
}
