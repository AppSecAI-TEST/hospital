package com.neusoft.hs.domain.pharmacy;

import com.neusoft.hs.platform.exception.HsException;

public class PharmacyException extends HsException {

	public PharmacyException() {
		super();
	}

	public PharmacyException(String arg0, Throwable arg1, Object... params) {
		super(String.format(arg0, params), arg1);
	}

	public PharmacyException(String arg0, Object... params) {
		super(String.format(arg0, params));
	}

	public PharmacyException(Throwable arg0) {
		super(arg0);
	}

}
