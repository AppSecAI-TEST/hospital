package com.neusoft.hs.platform.util;

import java.util.Date;

import org.springframework.context.ApplicationEvent;

public class SysDateUpdateEvent extends ApplicationEvent {

	private Date oldDate;

	private Date newDate;

	public SysDateUpdateEvent(Date oldDate, Date newDate) {
		super(newDate);

		this.oldDate = oldDate;
		this.newDate = newDate;
	}

	public Date getOldDate() {
		return oldDate;
	}

	public Date getNewDate() {
		return newDate;
	}
}
