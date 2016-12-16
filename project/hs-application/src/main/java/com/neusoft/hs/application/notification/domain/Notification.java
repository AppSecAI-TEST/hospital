package com.neusoft.hs.application.notification.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.neusoft.hs.platform.entity.IdEntity;

@Entity
@Table(name = "app_notification")
public class Notification extends IdEntity {

	@Column
	private String message;

	@Column
	private String state;

	@Column
	private String sender;

	@Column(name = "sender_name")
	private String senderName;

	@Column(name = "create_date")
	private Date createDate;

	@Column
	private String receiver;
	
	public static final String unRead = "未读";
	public static final String readed = "已读";

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}

	public String getSenderName() {
		return senderName;
	}

	public void setSenderName(String senderName) {
		this.senderName = senderName;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getReceiver() {
		return receiver;
	}

	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}

}
