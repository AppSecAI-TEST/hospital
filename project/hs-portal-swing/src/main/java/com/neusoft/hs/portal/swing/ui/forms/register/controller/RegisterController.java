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
import com.neusoft.hs.domain.organization.InPatientDept;
import com.neusoft.hs.domain.organization.OrganizationDomainService;
import com.neusoft.hs.domain.visit.Visit;
import com.neusoft.hs.platform.exception.HsException;
import com.neusoft.hs.portal.security.UserUtil;
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
	private InPatientDeptComboBoxModel deptComboBoxModel;
	private InPatientAppService inPatientAppService;
	private RegisterAppService registerAppService;
	private OrganizationDomainService organizationDomainService;
	private VisitValidator validator;

	@Autowired
	public RegisterController(VisitTableFrame tableFrame,
			AddVisitFrame addFrame, VisitTableModel tableModel,
			InPatientAppService inPatientAppService,
			RegisterAppService registerAppService,
			OrganizationDomainService organizationDomainService,
			VisitValidator validator) {
		this.tableFrame = tableFrame;
		this.addFrame = addFrame;
		this.tableModel = tableModel;
		this.inPatientAppService = inPatientAppService;
		this.registerAppService = registerAppService;
		this.organizationDomainService = organizationDomainService;
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
		showTableFrame();
	}

	private void loadEntities() {
		Pageable pageable = new PageRequest(0, 15);
		List<Visit> entities = registerAppService.listVisit(pageable);
		tableModel.clear();
		tableModel.addEntities(entities);
	}

	private void loadAddresses() {
		List<InPatientDept> depts = organizationDomainService.findAllInPatientDept();
		deptComboBoxModel.clear();
		deptComboBoxModel.addElements(depts);
	}

	private void showTableFrame() {
		tableFrame.setVisible(true);
	}

	private void showAddModal() {
		addFrame.setVisible(true);
	}

	private void saveEntity() {
		VisitFormPanel formPanel = addFrame.getFormPanel();
		Visit entity = formPanel.getEntityFromForm();
		Optional<ValidationError> errors = validator.validate(entity);
		if (errors.isPresent()) {
			ValidationError validationError = errors.get();
			Notifications.showFormValidationAlert(validationError.getMessage());
		} else {
			try {
				registerAppService.register(entity, UserUtil.getUser());
				tableModel.addEntity(entity);
				closeModalWindow();
			} catch (HsException e) {
				e.printStackTrace();
			}
		}
	}

	private void closeModalWindow() {
		addFrame.getFormPanel().clearForm();
		addFrame.dispose();
	}
}
