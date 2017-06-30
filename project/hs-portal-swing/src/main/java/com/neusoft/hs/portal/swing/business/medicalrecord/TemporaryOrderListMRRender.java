package com.neusoft.hs.portal.swing.business.medicalrecord;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.swing.JFrame;

import com.neusoft.hs.domain.medicalrecord.MedicalRecord;
import com.neusoft.hs.domain.medicalrecord.MedicalRecordRender;

@Entity
@DiscriminatorValue("TemporaryOrderList")
public class TemporaryOrderListMRRender extends MedicalRecordRender {

	public TemporaryOrderListMRRender() {
		super();
	}

	public TemporaryOrderListMRRender(String id) {
		super(id);
		this.setId(id);
	}

	@Override
	public JFrame play(MedicalRecord medicalRecord) {
		return new TemporaryOrderListFrame(medicalRecord);
	}
}
