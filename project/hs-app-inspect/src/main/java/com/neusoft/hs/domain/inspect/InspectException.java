package com.neusoft.hs.domain.inspect;

import com.neusoft.hs.platform.exception.HsException;

public class InspectException extends HsException {

	public InspectException() {
		super();
	}

	public InspectException(String arg0, Throwable arg1, Object... params) {
		super(String.format(arg0, params), arg1);
	}

	public InspectException(String arg0, Object... params) {
		super(String.format(arg0, params));
	}

	public InspectException(Throwable arg0) {
		super(arg0);
	}
}
