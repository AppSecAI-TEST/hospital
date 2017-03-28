package com.neusoft.hs.domain.order;

import org.springframework.context.ApplicationEvent;

public class OrderDeletedEvent extends ApplicationEvent {

	public OrderDeletedEvent(Object source) {
		super(source);
	}
}