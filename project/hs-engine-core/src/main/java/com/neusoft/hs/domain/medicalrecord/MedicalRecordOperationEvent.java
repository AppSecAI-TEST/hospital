package com.neusoft.hs.domain.medicalrecord;

import org.springframework.context.ApplicationEvent;

public class MedicalRecordOperationEvent extends ApplicationEvent {

	public MedicalRecordOperationEvent(Object source) {
		super(source);
	}

}
