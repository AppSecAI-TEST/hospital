package com.neusoft.hs.portal.swing.ui.forms.login.controller;

import java.util.Optional;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.neusoft.hs.domain.organization.AbstractUser;
import com.neusoft.hs.domain.organization.UserDomainService;
import com.neusoft.hs.portal.security.UserUtil;
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
	private UserDomainService userDomainService;
	private LoginValidator validator;

	@Autowired
	public LoginController(LoginFrame loginFrame,
			UserDomainService userDomainService, LoginValidator validator) {
		this.loginFrame = loginFrame;
		this.userDomainService = userDomainService;
		this.validator = validator;
	}

	@PostConstruct
	private void prepareListeners() {
		LoginFormBtnPanel formBtnPanel = loginFrame.getFormBtnPanel();

		registerAction(formBtnPanel.getLoginBtn(), (e) -> login());
		registerAction(formBtnPanel.getCancelBtn(), (e) -> closeModalWindow());
	}

	@Override
	public void prepareAndOpenFrame() {
		showLoginFrame();
	}

	private void showLoginFrame() {
		loginFrame.setVisible(true);
	}

	private void login() {
		LoginFormPanel formPanel = loginFrame.getFormPanel();
		LoginInfo entity = formPanel.getEntityFromForm();
		Optional<ValidationError> errors = validator.validate(entity);
		if (errors.isPresent()) {
			ValidationError validationError = errors.get();
			Notifications.showFormValidationAlert(validationError.getMessage());
		} else {
			AbstractUser user = userDomainService.findTheUserByAccount(entity
					.getAccount());
			if (user == null) {
				Notifications.showFormValidationAlert("用户不存在");
				return;
			}
			UserUtil.setUser(user);
			closeModalWindow();
		}
	}

	private void closeModalWindow() {
		loginFrame.getFormPanel().clearForm();
		loginFrame.dispose();
	}
}
