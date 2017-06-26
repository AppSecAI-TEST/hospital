package com.neusoft.hs.platform.exception;

public class HsException extends Exception {

	public HsException() {
		super();
	}

	public HsException(String arg0, Throwable arg1, Object... params) {
		super(String.format(arg0, params), arg1);
	}

	public HsException(String arg0, Object... params) {
		super(String.format(arg0, params));
	}

	public HsException(Throwable arg0) {
		super(arg0);
	}
}
