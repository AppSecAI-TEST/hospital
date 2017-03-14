package com.neusoft.hs.domain.medicalrecord;

import org.springframework.context.ApplicationEvent;

public class MedicalRecordFixedEvent extends ApplicationEvent {

	public MedicalRecordFixedEvent(Object source) {
		super(source);
	}
}
