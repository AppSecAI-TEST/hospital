package com.neusoft.hs.domain.outpatientdept;

import com.neusoft.hs.platform.exception.HsException;

public class OutPatientDeptException extends HsException {

	public OutPatientDeptException() {
		super();
	}

	public OutPatientDeptException(String arg0, Throwable arg1, Object... params) {
		super(String.format(arg0, params), arg1);
	}

	public OutPatientDeptException(String arg0, Object... params) {
		super(String.format(arg0, params));
	}

	public OutPatientDeptException(Throwable arg0) {
		super(arg0);
	}
}
