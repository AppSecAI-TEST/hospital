package com.neusoft.hs.domain.medicalrecord;

import org.springframework.context.ApplicationEvent;

public class MedicalRecordUpdatedEvent extends ApplicationEvent {

	public MedicalRecordUpdatedEvent(Object source) {
		super(source);
	}
}
