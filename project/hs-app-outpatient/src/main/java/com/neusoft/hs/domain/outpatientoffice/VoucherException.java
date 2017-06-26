package com.neusoft.hs.domain.outpatientoffice;

import com.neusoft.hs.platform.exception.HsException;

public class VoucherException extends HsException {

	public VoucherException() {
		super();
	}

	public VoucherException(String arg0, Throwable arg1, Object... params) {
		super(String.format(arg0, params), arg1);
	}

	public VoucherException(String arg0, Object... params) {
		super(String.format(arg0, params));
	}

	public VoucherException(Throwable arg0) {
		super(arg0);
	}

}
