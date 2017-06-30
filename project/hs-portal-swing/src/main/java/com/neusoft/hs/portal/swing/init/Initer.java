package com.neusoft.hs.portal.swing.init;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.neusoft.hs.domain.medicalrecord.MedicalRecordAdminDomainService;
import com.neusoft.hs.domain.medicalrecord.MedicalRecordRender;
import com.neusoft.hs.domain.medicalrecord.MedicalRecordType;
import com.neusoft.hs.portal.swing.business.medicalrecord.TemporaryOrderListMRRender;

@Service
@Transactional(rollbackFor = Exception.class)
public class Initer {

	@Autowired
	private MedicalRecordAdminDomainService medicalRecordAdminDomainService;

	public void init() {

		MedicalRecordType type;

		type = medicalRecordAdminDomainService
				.getMedicalRecordType(MedicalRecordType.TemporaryOrderList);
		type.setRender(new TemporaryOrderListMRRender(
				MedicalRecordRender.TemporaryOrderList));

		type.save();
	}

}
