package com.neusoft.hs.domain.order;

import org.springframework.context.ApplicationEvent;

public class InspectApplyItemCanceledEvent extends ApplicationEvent {

	public InspectApplyItemCanceledEvent(Object source) {
		super(source);
	}
}