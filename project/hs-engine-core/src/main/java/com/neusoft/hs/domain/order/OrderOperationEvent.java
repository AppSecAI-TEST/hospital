package com.neusoft.hs.domain.order;

import org.springframework.context.ApplicationEvent;

public class OrderOperationEvent extends ApplicationEvent {

	public OrderOperationEvent(Object source) {
		super(source);
	}

}
