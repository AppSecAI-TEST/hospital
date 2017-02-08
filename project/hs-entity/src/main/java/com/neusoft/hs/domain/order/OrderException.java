package com.neusoft.hs.domain.order;

import com.neusoft.hs.platform.exception.HsException;

public class OrderException extends HsException {

	private Order order;

	public OrderException(Order order) {
		super();
		this.order = order;
	}

	public OrderException(Order order, String arg0, Throwable arg1) {
		super(arg0, arg1);
		this.order = order;
	}

	public OrderException(Order order, String arg0) {
		super(arg0);
		this.order = order;
	}

	public OrderException(Order order, Throwable arg0) {
		super(arg0);
		this.order = order;
	}

	public Order getOrder() {
		return order;
	}

}
