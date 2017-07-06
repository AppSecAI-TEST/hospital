package com.neusoft.hs.portal.swing.business.medicalrecord;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.swing.JFrame;

import com.neusoft.hs.domain.medicalrecord.MedicalRecord;
import com.neusoft.hs.domain.medicalrecord.MedicalRecordRender;

@Entity
@DiscriminatorValue("InWardRecord")
public class InWardRecordMRRender extends MedicalRecordRender {

	public InWardRecordMRRender() {
		super();
	}

	public InWardRecordMRRender(String id) {
		super(id);
		this.setId(id);
	}

	@Override
	public JFrame play(MedicalRecord medicalRecord) {
		InWardRecordFrame frame = new InWardRecordFrame(medicalRecord);
		frame.getCloseBtn().addActionListener((e) -> {
			frame.dispose();
		});

		return frame;
	}
}
