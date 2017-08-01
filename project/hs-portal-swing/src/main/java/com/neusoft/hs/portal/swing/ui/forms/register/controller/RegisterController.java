package com.neusoft.hs.portal.swing.ui.forms.register.controller;

import java.util.List;
import java.util.Optional;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;

import com.neusoft.hs.application.register.RegisterAppService;
import com.neusoft.hs.domain.organization.Doctor;
import com.neusoft.hs.domain.organization.InPatientAreaDept;
import com.neusoft.hs.domain.organization.InPatientDept;
import com.neusoft.hs.domain.organization.OrganizationAdminDomainService;
import com.neusoft.hs.domain.organization.UserAdminDomainService;
import com.neusoft.hs.domain.visit.CreateVisitVO;
import com.neusoft.hs.domain.visit.Visit;
import com.neusoft.hs.platform.exception.HsException;
import com.neusoft.hs.portal.swing.ui.forms.register.view.VisitTableBtnPanel;
import com.neusoft.hs.portal.swing.ui.forms.register.view.VisitTableFrame;
import com.neusoft.hs.portal.swing.ui.forms.register.view.VisitTablePanel;
import com.neusoft.hs.portal.swing.ui.forms.register.view.modal.AddVisitFrame;
import com.neusoft.hs.portal.swing.ui.forms.register.view.modal.VisitFormBtnPanel;
import com.neusoft.hs.portal.swing.ui.forms.register.view.modal.VisitFormPanel;
import com.neusoft.hs.portal.swing.ui.shared.controller.AbstractFrameController;
import com.neusoft.hs.portal.swing.ui.shared.model.DoctorComboBoxModel;
import com.neusoft.hs.portal.swing.ui.shared.model.InPatientAreaComboBoxModel;
import com.neusoft.hs.portal.swing.ui.shared.model.InPatientDeptComboBoxModel;
import com.neusoft.hs.portal.swing.ui.shared.model.StringComboBoxModel;
import com.neusoft.hs.portal.swing.ui.shared.model.VisitTableModel;
import com.neusoft.hs.portal.swing.util.Notifications;
import com.neusoft.hs.portal.swing.validation.ValidationError;
import com.neusoft.hs.portal.swing.validation.VisitValidator;

@Controller
public class RegisterController extends AbstractFrameController {

	@Autowired
	private VisitTableFrame tableFrame;

	@Autowired
	private AddVisitFrame addFrame;

	@Autowired
	private RegisterAppService registerAppService;

	@Autowired
	private OrganizationAdminDomainService organizationDomainService;

	@Autowired
	private UserAdminDomainService userDomainService;

	@Autowired
	private VisitValidator validator;

	private VisitTableModel tableModel;

	@PostConstruct
	private void prepareListeners() {
		VisitTableBtnPanel tableBtnPanel = tableFrame.getTableBtnPanel();
		VisitFormBtnPanel formBtnPanel = addFrame.getFormBtnPanel();
		VisitTablePanel visitTablePanel = tableFrame.getTablePanel();

		registerAction(visitTablePanel.getVisitStateCB(), (e) -> loadEntities());
		registerAction(tableBtnPanel.getAddBtn(), (e) -> showAddModal());
		registerAction(formBtnPanel.getSaveBtn(), (e) -> saveEntity());
		registerAction(formBtnPanel.getCancelBtn(), (e) -> closeModalWindow());
		registerAction(tableBtnPanel.getCloseBtn(), (e) -> closeWindow());
	}

	@Override
	public void prepareAndOpenFrame() throws HsException {
		loadVisitStates();
		loadEntities();
		loadInPatientDepts();
		loadInPatientAreas();
		loadDoctors();
		showTableFrame();
	}

	public void loadVisitStates() {
		StringComboBoxModel visitStateComboBoxModel = this.tableFrame
				.getTablePanel().getVisitStateComboBoxModel();

		visitStateComboBoxModel.clear();
		visitStateComboBoxModel.addElement(null);
		visitStateComboBoxModel.addElements(Visit.getStates());
		
		visitStateComboBoxModel.setSelectedItem(Visit.State_NeedInitAccount);
	}

	private void loadEntities() {
		StringComboBoxModel visitStateComboBoxModel = this.tableFrame
				.getTablePanel().getVisitStateComboBoxModel();
		String visitState = visitStateComboBoxModel.getSelectedItem();
		if (visitState != null) {
			Pageable pageable = new PageRequest(0, 15);
			List<Visit> entities = registerAppService.listVisit(visitState, pageable);

			tableModel = this.tableFrame.getTablePanel().getTableModel();
			tableModel.clear();
			tableModel.addEntities(entities);
		}
	}

	private void loadInPatientDepts() {
		Pageable pageable = new PageRequest(0, Integer.MAX_VALUE);
		List<InPatientDept> depts = organizationDomainService
				.findInPatientDept(pageable);

		InPatientDeptComboBoxModel inPatientDeptComboBoxModel = this.addFrame
				.getFormPanel().getRespDeptComboBoxModel();
		inPatientDeptComboBoxModel.clear();
		inPatientDeptComboBoxModel.addElements(depts);
	}

	private void loadInPatientAreas() {
		Pageable pageable = new PageRequest(0, Integer.MAX_VALUE);
		List<InPatientAreaDept> areas = organizationDomainService
				.findInPatientArea(pageable);

		InPatientAreaComboBoxModel inPatientAreaComboBoxModel = this.addFrame
				.getFormPanel().getRespAreaComboBoxModel();
		inPatientAreaComboBoxModel.clear();
		inPatientAreaComboBoxModel.addElements(areas);
	}

	private void loadDoctors() {
		Pageable pageable = new PageRequest(0, Integer.MAX_VALUE);
		List<Doctor> doctors = this.userDomainService.findDoctor(pageable);

		DoctorComboBoxModel doctorComboBoxModel = this.addFrame.getFormPanel()
				.getRespDoctorComboBoxModel();
		doctorComboBoxModel.clear();
		doctorComboBoxModel.addElements(doctors);
	}

	private void showTableFrame() {
		tableFrame.setVisible(true);
	}

	private void showAddModal() {
		addFrame.setVisible(true);
	}

	private void saveEntity() {
		VisitFormPanel formPanel = addFrame.getFormPanel();
		CreateVisitVO entity = null;
		try {
			entity = formPanel.getEntityFromForm();
		} catch (HsException e) {
			Notifications.showFormValidationAlert(e.getMessage());
		}
		Optional<ValidationError> errors = validator.validate(entity);
		if (errors.isPresent()) {
			ValidationError validationError = errors.get();
			Notifications.showFormValidationAlert(validationError.getMessage());
		} else {
			Visit visit = registerAppService.register(entity);
			tableModel.addEntity(visit);
			closeModalWindow();
		}
	}

	private void closeModalWindow() {
		addFrame.getFormPanel().clearForm();
		addFrame.dispose();
	}

	private void closeWindow() {
		tableFrame.dispose();
	}
}
