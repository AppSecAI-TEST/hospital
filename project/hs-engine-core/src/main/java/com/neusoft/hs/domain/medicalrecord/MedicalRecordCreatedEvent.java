package com.neusoft.hs.domain.medicalrecord;

import org.springframework.context.ApplicationEvent;

public class MedicalRecordCreatedEvent extends ApplicationEvent {

	public MedicalRecordCreatedEvent(Object source) {
		super(source);
	}
}
