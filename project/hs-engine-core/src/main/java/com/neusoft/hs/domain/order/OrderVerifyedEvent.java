package com.neusoft.hs.domain.order;

import org.springframework.context.ApplicationEvent;

public class OrderVerifyedEvent extends ApplicationEvent {

	public OrderVerifyedEvent(Object source) {
		super(source);
	}
}