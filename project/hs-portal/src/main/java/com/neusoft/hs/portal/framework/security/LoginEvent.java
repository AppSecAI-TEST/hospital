package com.neusoft.hs.portal.framework.security;

import org.springframework.context.ApplicationEvent;

public class LoginEvent extends ApplicationEvent {

	public LoginEvent(Object source) {
		super(source);
	}
}
