package com.neusoft.hs.portal.swing.ui.forms.login.model;

import static com.neusoft.hs.portal.swing.util.ConstMessagesEN.ValidationMessages.REQUIRED_DATA_NOT_FILLED_OR_BAD_DATA;

import java.util.Optional;

import org.springframework.stereotype.Component;

import com.neusoft.hs.portal.swing.validation.ValidationError;
import com.neusoft.hs.portal.swing.validation.ValidationSupport;
import com.neusoft.hs.portal.swing.validation.Validator;

@Component
public class LoginValidator extends ValidationSupport implements
		Validator<LoginInfo> {

	@Override
	public Optional<ValidationError> validate(LoginInfo loginInfo) {
		if (isNullValue(loginInfo.getAccount())) {
			return Optional.of(new ValidationError(
					REQUIRED_DATA_NOT_FILLED_OR_BAD_DATA));
		}
		return Optional.empty();
	}

}
