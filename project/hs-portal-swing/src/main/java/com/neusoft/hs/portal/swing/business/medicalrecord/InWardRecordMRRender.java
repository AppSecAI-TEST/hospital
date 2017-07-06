package com.neusoft.hs.portal.swing.business.medicalrecord;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.swing.JButton;
import javax.swing.JFrame;

import com.neusoft.hs.application.medicalrecord.MedicalRecordAppService;
import com.neusoft.hs.domain.medicalrecord.MedicalRecord;
import com.neusoft.hs.domain.medicalrecord.MedicalRecordRender;
import com.neusoft.hs.portal.framework.security.UserUtil;
import com.neusoft.hs.portal.swing.util.Notifications;

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
		JButton signBtn = frame.getSignBtn();
		if (signBtn != null) {
			signBtn.addActionListener((e) -> {
				try {
					getService(MedicalRecordAppService.class).sign(
							medicalRecord.getId(), UserUtil.getUser());
				} catch (Exception e1) {
					e1.printStackTrace();
					Notifications.showFormValidationAlert(e1.getMessage());
				}
			});
		}

		return frame;
	}
}
