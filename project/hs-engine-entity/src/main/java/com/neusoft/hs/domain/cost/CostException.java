package com.neusoft.hs.domain.cost;

import com.neusoft.hs.platform.exception.HsException;

public class CostException extends HsException {

	public CostException() {
		super();
	}

	public CostException(String arg0, Throwable arg1, Object... params) {
		super(String.format(arg0, params), arg1);
	}

	public CostException(String arg0, Object... params) {
		super(String.format(arg0, params));
	}

	public CostException(Throwable arg0) {
		super(arg0);
	}
}
