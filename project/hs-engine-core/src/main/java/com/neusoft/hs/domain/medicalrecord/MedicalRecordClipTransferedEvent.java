package com.neusoft.hs.domain.medicalrecord;

import org.springframework.context.ApplicationEvent;

public class MedicalRecordClipTransferedEvent extends ApplicationEvent {

	public MedicalRecordClipTransferedEvent(Object source) {
		super(source);
	}
}
