package com.neusoft.hs.domain.medicalrecord;

import org.springframework.context.ApplicationEvent;

public class MedicalRecordEvent extends ApplicationEvent {

	public MedicalRecordEvent(Object source) {
		super(source);
	}

}
