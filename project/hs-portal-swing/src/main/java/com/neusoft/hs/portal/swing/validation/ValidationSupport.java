package com.neusoft.hs.portal.swing.validation;

public abstract class ValidationSupport {

	boolean isNullOrEmptyString(String value) {
		return value == null || value.length() == 0;
	}

	protected boolean isNullValue(Object value) {
		return value == null;
	}

	boolean isValueGreaterThanZero(long value) {
		return value > 0;
	}

	boolean isValueGreaterThanZero(double value) {
		return value > 0;
	}

}
