package com.neusoft.hs.domain.order;

import com.neusoft.hs.platform.exception.HsException;

public class OrderExecuteException extends HsException {

	private OrderExecute execute;

	public OrderExecuteException(OrderExecute execute) {
		super();
		this.execute = execute;
	}

	public OrderExecuteException(OrderExecute execute, String arg0,
			Throwable arg1) {
		super(arg0, arg1);
		this.execute = execute;
	}

	public OrderExecuteException(OrderExecute execute, String arg0) {
		super(arg0);
		this.execute = execute;
	}

	public OrderExecuteException(OrderExecute execute, Throwable arg0) {
		super(arg0);
		this.execute = execute;
	}

	public OrderExecute getExecute() {
		return execute;
	}

}
