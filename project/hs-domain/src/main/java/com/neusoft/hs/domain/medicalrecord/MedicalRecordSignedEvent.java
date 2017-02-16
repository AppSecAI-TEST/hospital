package com.neusoft.hs.domain.medicalrecord;

import org.springframework.context.ApplicationEvent;

public class MedicalRecordSignedEvent extends ApplicationEvent {

	public MedicalRecordSignedEvent(Object source) {
		super(source);
	}
}
