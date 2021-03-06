package com.neusoft.hs.portal.swing.init;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.neusoft.hs.domain.medicalrecord.MedicalRecordAdminService;
import com.neusoft.hs.domain.medicalrecord.MedicalRecordRender;
import com.neusoft.hs.domain.medicalrecord.MedicalRecordType;
import com.neusoft.hs.portal.swing.business.medicalrecord.InWardRecordMRRender;
import com.neusoft.hs.portal.swing.business.medicalrecord.OutPatientRecordMRRender;
import com.neusoft.hs.portal.swing.business.medicalrecord.TemporaryOrderListMRRender;

@Service
@Transactional(rollbackFor = Exception.class)
public class SwingDataInitService {

	@Autowired
	private MedicalRecordAdminService medicalRecordAdminDomainService;

	public void init() {

		medicalRecordAdminDomainService.clearRender();

		MedicalRecordType type;

		type = medicalRecordAdminDomainService
				.getMedicalRecordType(MedicalRecordType.TemporaryOrderList);
		type.setRender(new TemporaryOrderListMRRender(
				MedicalRecordRender.TemporaryOrderList));

		type.save();

		type = medicalRecordAdminDomainService
				.getMedicalRecordType(MedicalRecordType.IntoWardRecord);
		type.setRender(new InWardRecordMRRender(
				MedicalRecordRender.IntoWardRecord));

		type.save();

		type = medicalRecordAdminDomainService
				.getMedicalRecordType(MedicalRecordType.OutPatientRecord);
		type.setRender(new OutPatientRecordMRRender(
				MedicalRecordRender.OutPatientRecord));

		type.save();
	}

}
