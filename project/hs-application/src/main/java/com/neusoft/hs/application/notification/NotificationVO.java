package com.neusoft.hs.application.notification;

import java.util.ArrayList;
import java.util.List;

public class NotificationVO {

	private String message;

	private List<String> receivers = new ArrayList<String>();

	private String sourceId;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public List<String> getReceivers() {
		return receivers;
	}

	public void setReceivers(List<String> receivers) {
		this.receivers = receivers;
	}
	
	public void addReceiver(String receiver){
		this.receivers.add(receiver);
	}

	public String getSourceId() {
		return sourceId;
	}

	public void setSourceId(String sourceId) {
		this.sourceId = sourceId;
	}
}
