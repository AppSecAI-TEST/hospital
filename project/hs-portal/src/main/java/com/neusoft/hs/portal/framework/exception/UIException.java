package com.neusoft.hs.portal.framework.exception;

import com.neusoft.hs.platform.exception.HsException;

public class UIException extends HsException {

	public UIException() {
		super();
	}

	public UIException(String arg0, Throwable arg1, Object... params) {
		super(String.format(arg0, params), arg1);
	}

	public UIException(String arg0, Object... params) {
		super(String.format(arg0, params));
	}

	public UIException(Throwable arg0) {
		super(arg0);
	}

}
