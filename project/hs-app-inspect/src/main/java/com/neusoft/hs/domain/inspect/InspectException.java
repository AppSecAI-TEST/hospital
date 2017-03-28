package com.neusoft.hs.domain.inspect;

import com.neusoft.hs.platform.exception.HsException;

public class InspectException extends HsException {

	public InspectException() {
		super();
	}

	public InspectException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public InspectException(String arg0) {
		super(arg0);
	}

	public InspectException(Throwable arg0) {
		super(arg0);
	}
}
