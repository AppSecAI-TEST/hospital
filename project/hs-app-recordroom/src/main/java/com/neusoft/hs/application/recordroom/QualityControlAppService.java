package com.neusoft.hs.application.recordroom;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.neusoft.hs.domain.medicalrecord.MedicalRecordClip;
import com.neusoft.hs.domain.medicalrecord.MedicalRecordDomainService;
import com.neusoft.hs.domain.medicalrecord.MedicalRecordException;
import com.neusoft.hs.domain.organization.AbstractUser;

@Service
@Transactional(rollbackFor = Exception.class)
public class QualityControlAppService {

	@Autowired
	private MedicalRecordDomainService medicalRecordDomainService;

	public List<MedicalRecordClip> findNeedCheckRecordClips(AbstractUser user,
			Pageable pageable) {
		return medicalRecordDomainService.findClips(
				MedicalRecordClip.State_Checking, user.getDept(), pageable);
	}

	public void pass(String clipId, AbstractUser user)
			throws MedicalRecordException {
		MedicalRecordClip clip = medicalRecordDomainService.findClip(clipId);
		if (clip == null) {
			throw new MedicalRecordException(null, "id=[%s]病历夹不存在", clipId);
		}
		medicalRecordDomainService.toArchive(clip, user);

	}

}
