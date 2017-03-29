package com.neusoft.hs.domain.order;

import org.springframework.context.ApplicationEvent;

public class OrderCanceledEvent extends ApplicationEvent {

	public OrderCanceledEvent(Object source) {
		super(source);
	}
}