package com.neusoft.hs.domain.organization;

import com.neusoft.hs.platform.exception.HsException;

public class UnitException extends HsException {

	private Unit unit;

	public UnitException(Unit unit) {
		super();
		this.unit = unit;
	}

	public UnitException(Unit unit, String arg0, Throwable arg1,
			Object... params) {
		super(String.format(arg0, params), arg1);
		this.unit = unit;
	}

	public UnitException(Unit unit, String arg0, Object... params) {
		super(String.format(arg0, params));
		this.unit = unit;
	}

	public UnitException(Unit unit, Throwable arg0) {
		super(arg0);
		this.unit = unit;	
	}

	public Unit getUnit() {
		return unit;
	}

}
