package com.neusoft.hs.domain.order;

import org.springframework.context.ApplicationEvent;

public class OrderStopedEvent extends ApplicationEvent {

	public OrderStopedEvent(Object source) {
		super(source);
	}

}
