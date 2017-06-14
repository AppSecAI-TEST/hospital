package com.neusoft.hs.portal.swing.ui.forms.register.controller;

import java.util.List;
import java.util.Optional;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;

import com.neusoft.hs.application.inpatientdept.InPatientAppService;
import com.neusoft.hs.application.register.RegisterAppService;
import com.neusoft.hs.domain.organization.Doctor;
import com.neusoft.hs.domain.organization.InPatientDept;
import com.neusoft.hs.domain.organization.OrganizationAdminDomainService;
import com.neusoft.hs.domain.organization.UserAdminDomainService;
import com.neusoft.hs.domain.visit.CreateVisitVO;
import com.neusoft.hs.domain.visit.Visit;
import com.neusoft.hs.platform.exception.HsException;
import com.neusoft.hs.portal.swing.ui.forms.register.model.DoctorComboBoxModel;
import com.neusoft.hs.portal.swing.ui.forms.register.model.InPatientDeptComboBoxModel;
import com.neusoft.hs.portal.swing.ui.forms.register.model.VisitTableModel;
import com.neusoft.hs.portal.swing.ui.forms.register.view.VisitTableBtnPanel;
import com.neusoft.hs.portal.swing.ui.forms.register.view.VisitTableFrame;
import com.neusoft.hs.portal.swing.ui.forms.register.view.modal.AddVisitFrame;
import com.neusoft.hs.portal.swing.ui.forms.register.view.modal.VisitFormBtnPanel;
import com.neusoft.hs.portal.swing.ui.forms.register.view.modal.VisitFormPanel;
import com.neusoft.hs.portal.swing.ui.shared.controller.AbstractFrameController;
import com.neusoft.hs.portal.swing.util.Notifications;
import com.neusoft.hs.portal.swing.validation.ValidationError;
import com.neusoft.hs.portal.swing.validation.VisitValidator;

@Controller
public class RegisterController extends AbstractFrameController {

	private VisitTableFrame tableFrame;
	private AddVisitFrame addFrame;
	private VisitTableModel tableModel;
	private InPatientDeptComboBoxModel inPatientDeptComboBoxModel;
	private DoctorComboBoxModel doctorComboBoxModel;
	private RegisterAppService registerAppService;
	private OrganizationAdminDomainService organizationDomainService;
	private UserAdminDomainService userDomainService;
	private VisitValidator validator;

	@Autowired
	public RegisterController(VisitTableFrame tableFrame,
			AddVisitFrame addFrame, VisitTableModel tableModel,
			RegisterAppService registerAppService,
			OrganizationAdminDomainService organizationDomainService,
			InPatientDeptComboBoxModel inPatientDeptComboBoxModel,
			DoctorComboBoxModel doctorComboBoxModel,
			UserAdminDomainService userDomainService, VisitValidator validator) {
		this.tableFrame = tableFrame;
		this.addFrame = addFrame;
		this.tableModel = tableModel;
		this.registerAppService = registerAppService;
		this.organizationDomainService = organizationDomainService;
		this.userDomainService = userDomainService;
		this.inPatientDeptComboBoxModel = inPatientDeptComboBoxModel;
		this.doctorComboBoxModel = doctorComboBoxModel;
		this.validator = validator;
	}

	@PostConstruct
	private void prepareListeners() {
		VisitTableBtnPanel tableBtnPanel = tableFrame.getTableBtnPanel();
		VisitFormBtnPanel formBtnPanel = addFrame.getFormBtnPanel();

		registerAction(tableBtnPanel.getAddBtn(), (e) -> showAddModal());
		registerAction(formBtnPanel.getSaveBtn(), (e) -> saveEntity());
		registerAction(formBtnPanel.getCancelBtn(), (e) -> closeModalWindow());
	}

	@Override
	public void prepareAndOpenFrame() {
		loadEntities();
		loadInPatientDepts();
		loadDoctors();
		showTableFrame();
	}

	private void loadEntities() {
		Pageable pageable = new PageRequest(0, 15);
		List<Visit> entities = registerAppService.listVisit(pageable);
		tableModel.clear();
		tableModel.addEntities(entities);
	}

	private void loadInPatientDepts() {
		List<InPatientDept> depts = organizationDomainService
				.findAllInPatientDept();
		inPatientDeptComboBoxModel.clear();
		inPatientDeptComboBoxModel.addElements(depts);
	}

	private void loadDoctors() {
		Pageable pageable = new PageRequest(0, Integer.MAX_VALUE);
		List<Doctor> doctors = this.userDomainService.findDoctor(pageable);
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
}
