package com.neusoft.hs.domain.recordroom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.neusoft.hs.domain.medicalrecord.MedicalRecordClip;
import com.neusoft.hs.domain.organization.AbstractUser;
import com.neusoft.hs.platform.util.DateUtil;

@Service
@Transactional(rollbackFor = Exception.class)
public class RecordRoomDomainService {
	
	@Autowired
	private MedicalCaseRepo medicalCaseRepo;

	public void createCase(MedicalRecordClip clip, String position, AbstractUser user) {
		MedicalCase medicalCase = new MedicalCase(clip);
		medicalCase.setPosition(position);
		medicalCase.setCreator(user);
		medicalCase.setCreateDate(DateUtil.getSysDate());
		
		medicalCaseRepo.save(medicalCase);
	}

}
