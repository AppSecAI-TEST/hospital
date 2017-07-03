package com.neusoft.hs.portal.swing.business.medicalrecord;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
		TemporaryOrderListFrame frame = new TemporaryOrderListFrame(
				medicalRecord);
		frame.getCloseBtn().addActionListener((e) -> {
			frame.dispose();
		});

		return frame;
	}
}
