package com.neusoft.hs.domain.inspect;

import org.springframework.context.ApplicationEvent;

public class InspectApplyItemCanceledEvent extends ApplicationEvent {

	public InspectApplyItemCanceledEvent(Object source) {
		super(source);
	}
}