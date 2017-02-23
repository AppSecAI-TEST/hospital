package com.neusoft.hs.application.recordroom;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.neusoft.hs.domain.medicalrecord.MedicalRecordClip;
import com.neusoft.hs.domain.medicalrecord.MedicalRecordDomainService;
import com.neusoft.hs.domain.organization.AbstractUser;
import com.neusoft.hs.domain.recordroom.RecordRoomDomainService;

@Service
@Transactional(rollbackFor = Exception.class)
public class QualityControlAppService {

	@Autowired
	private MedicalRecordDomainService medicalRecordDomainService;

	@Autowired
	private RecordRoomDomainService recordRoomDomainService;

	public List<MedicalRecordClip> findNeedCheckRecordClips(AbstractUser user) {
		return medicalRecordDomainService.findClips(
				MedicalRecordClip.State_Checking, user.getDept());
	}

	public void pass(MedicalRecordClip clip, String position, AbstractUser user) {
		medicalRecordDomainService.archive(clip);
		recordRoomDomainService.createCase(clip, position, user);
	}

}
