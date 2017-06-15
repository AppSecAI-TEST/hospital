package com.neusoft.hs.portal.swing.ui.forms.login.controller;

import java.util.List;
import java.util.Optional;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;

import com.neusoft.hs.domain.organization.AbstractUser;
import com.neusoft.hs.domain.organization.InPatientDept;
import com.neusoft.hs.domain.organization.UserAdminDomainService;
import com.neusoft.hs.platform.exception.HsException;
import com.neusoft.hs.portal.security.UserUtil;
import com.neusoft.hs.portal.swing.ui.forms.login.model.AbstractUserComboBoxModel;
import com.neusoft.hs.portal.swing.ui.forms.login.model.LoginInfo;
import com.neusoft.hs.portal.swing.ui.forms.login.model.LoginValidator;
import com.neusoft.hs.portal.swing.ui.forms.login.view.LoginFormBtnPanel;
import com.neusoft.hs.portal.swing.ui.forms.login.view.LoginFormPanel;
import com.neusoft.hs.portal.swing.ui.forms.login.view.LoginFrame;
import com.neusoft.hs.portal.swing.ui.shared.controller.AbstractFrameController;
import com.neusoft.hs.portal.swing.util.Notifications;
import com.neusoft.hs.portal.swing.validation.ValidationError;

@Controller
public class LoginController extends AbstractFrameController {

	private LoginFrame loginFrame;
	private UserAdminDomainService userAdminDomainService;
	private LoginValidator validator;

	private AbstractUserComboBoxModel abstractUserComboBoxModel;

	@Autowired
	public LoginController(LoginFrame loginFrame,
			UserAdminDomainService userDomainService,
			AbstractUserComboBoxModel abstractUserComboBoxModel,
			LoginValidator validator) {
		this.loginFrame = loginFrame;
		this.userAdminDomainService = userDomainService;
		this.abstractUserComboBoxModel = abstractUserComboBoxModel;
		this.validator = validator;
	}

	@PostConstruct
	private void prepareListeners() {
		LoginFormBtnPanel formBtnPanel = loginFrame.getFormBtnPanel();

		registerAction(formBtnPanel.getLoginBtn(), (e) -> login());
		registerAction(formBtnPanel.getCancelBtn(), (e) -> closeModalWindow());
	}

	@Override
	public void prepareAndOpenFrame() throws HsException{
		this.loadUsers();
		showLoginFrame();
	}

	private void loadUsers() {
		Pageable pageable = new PageRequest(0, Integer.MAX_VALUE);
		List<AbstractUser> users = userAdminDomainService.find(pageable);
		abstractUserComboBoxModel.clear();
		abstractUserComboBoxModel.addElements(users);
	}

	private void showLoginFrame() {
		loginFrame.setVisible(true);
	}

	private void login() {
		LoginFormPanel formPanel = loginFrame.getFormPanel();
		AbstractUser user = formPanel.getEntityFromForm();
		UserUtil.setUser(user);
		closeModalWindow();
	}

	// private void login() {
	// LoginFormPanel formPanel = loginFrame.getFormPanel();
	// LoginInfo entity = formPanel.getEntityFromForm();
	// Optional<ValidationError> errors = validator.validate(entity);
	// if (errors.isPresent()) {
	// ValidationError validationError = errors.get();
	// Notifications.showFormValidationAlert(validationError.getMessage());
	// } else {
	// AbstractUser user = userDomainService.findTheUserByAccount(entity
	// .getAccount());
	// if (user == null) {
	// Notifications.showFormValidationAlert("用户不存在");
	// return;
	// }
	// UserUtil.setUser(user);
	// closeModalWindow();
	// }
	// }

	private void closeModalWindow() {
		loginFrame.getFormPanel().clearForm();
		loginFrame.dispose();
	}
}
