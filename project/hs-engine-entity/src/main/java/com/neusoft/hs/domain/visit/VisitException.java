package com.neusoft.hs.domain.visit;

import com.neusoft.hs.platform.exception.HsException;

public class VisitException extends HsException {

	private Visit visit;

	public VisitException(Visit visit) {
		super();
		this.visit = visit;
	}

	public VisitException(Visit visit, String arg0, Throwable arg1) {
		super(arg0, arg1);
		this.visit = visit;
	}

	public VisitException(Visit visit, String arg0) {
		super(arg0);
		this.visit = visit;
	}

	public VisitException(Visit visit, Throwable arg0) {
		super(arg0);
		this.visit = visit;
	}

	public Visit getVisit() {
		return visit;
	}

}
