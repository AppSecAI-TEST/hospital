package com.neusoft.hs.portal.swing.business.medicalrecord;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.swing.JButton;
import javax.swing.JFrame;

import com.neusoft.hs.application.medicalrecord.MedicalRecordAppService;
import com.neusoft.hs.domain.medicalrecord.MedicalRecord;
import com.neusoft.hs.domain.medicalrecord.MedicalRecordRender;
import com.neusoft.hs.domain.treatment.Itemable;
import com.neusoft.hs.domain.treatment.SimpleTreatmentItemValue;
import com.neusoft.hs.domain.treatment.TreatmentItemSpec;
import com.neusoft.hs.portal.swing.util.Notifications;

@Entity
@DiscriminatorValue("OutPatientRecord")
public class OutPatientRecordMRRender extends MedicalRecordRender {

	public OutPatientRecordMRRender() {
		super();
	}

	public OutPatientRecordMRRender(String id) {
		super(id);
		this.setId(id);
	}

	@Override
	public JFrame play(MedicalRecord medicalRecord) {
		OutPatientRecordFrame frame = new OutPatientRecordFrame(medicalRecord);
		frame.getCloseBtn().addActionListener((e) -> {
			frame.dispose();
		});
		JButton saveBtn = frame.getSaveBtn();
		if (saveBtn != null) {
			saveBtn.addActionListener((e) -> {
				try {
					// 修改主诉
					String mainDescribe = frame.getMainDescribeTF().getText();
					Itemable item = medicalRecord.getDatas().get(
							TreatmentItemSpec.MainDescribe);
					if (item.getValues() != null
							&& item.getValues().size() >= 0) {
						// 修改主诉
						((SimpleTreatmentItemValue) item.getValues().get(0))
								.setInfo(mainDescribe);
					} else {
						// 创建主诉
						SimpleTreatmentItemValue value = new SimpleTreatmentItemValue();
						value.setInfo(mainDescribe);
						item.addValue(value);
					}

					getService(MedicalRecordAppService.class).save(
							medicalRecord);
				} catch (Exception e1) {
					e1.printStackTrace();
					Notifications.showFormValidationAlert(e1.getMessage());
				}
			});
		}
		return frame;
	}
}
