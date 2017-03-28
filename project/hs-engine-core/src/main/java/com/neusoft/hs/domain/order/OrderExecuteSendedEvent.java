package com.neusoft.hs.domain.order;

import org.springframework.context.ApplicationEvent;

public class OrderExecuteSendedEvent extends ApplicationEvent {

	public OrderExecuteSendedEvent(Object source) {
		super(source);
	}

}
