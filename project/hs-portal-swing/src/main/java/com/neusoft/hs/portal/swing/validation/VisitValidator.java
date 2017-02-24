package com.neusoft.hs.portal.swing.validation;

import static com.neusoft.hs.portal.swing.util.ConstMessagesEN.ValidationMessages.REQUIRED_DATA_NOT_FILLED_OR_BAD_DATA;

import java.util.Optional;

import org.springframework.stereotype.Component;

import com.neusoft.hs.domain.visit.CreateVisitVO;

@Component
public class VisitValidator extends ValidationSupport implements
		Validator<CreateVisitVO> {

	@Override
	public Optional<ValidationError> validate(CreateVisitVO visit) {
		if (isNullValue(visit.getName())) {
			return Optional.of(new ValidationError(
					REQUIRED_DATA_NOT_FILLED_OR_BAD_DATA));
		}
		return Optional.empty();
	}

}
