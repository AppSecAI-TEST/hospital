package com.neusoft.hs.domain.treatment;

import com.neusoft.hs.platform.exception.HsException;

public class TreatmentException extends HsException {

	public TreatmentException() {
		super();
	}

	public TreatmentException(String arg0, Throwable arg1, Object... params) {
		super(String.format(arg0, params), arg1);
	}

	public TreatmentException(String arg0, Object... params) {
		super(String.format(arg0, params));
	}

	public TreatmentException(Throwable arg0) {
		super(arg0);
	}

}
