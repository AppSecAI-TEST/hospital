package com.neusoft.hs.domain.recordroom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.neusoft.hs.domain.medicalrecord.MedicalRecordClip;
import com.neusoft.hs.domain.medicalrecord.MedicalRecordDomainService;
import com.neusoft.hs.domain.medicalrecord.MedicalRecordException;
import com.neusoft.hs.domain.organization.AbstractUser;
import com.neusoft.hs.platform.util.DateUtil;

@Service
@Transactional(rollbackFor = Exception.class)
public class RecordRoomDomainService {

	@Autowired
	private MedicalCaseRepo medicalCaseRepo;

	@Autowired
	private MedicalRecordDomainService medicalRecordDomainService;

	public void archive(String id, String position, AbstractUser user)
			throws MedicalRecordException {

		MedicalRecordClip clip = medicalRecordDomainService.findClip(id);
		if (clip == null) {
			throw new MedicalRecordException(null, "id=[" + id + "]病历夹不存在");
		}
		clip.setState(MedicalRecordClip.State_Archived);

		MedicalCase medicalCase = new MedicalCase(clip);
		medicalCase.setPosition(position);
		medicalCase.setCreator(user);
		medicalCase.setCreateDate(DateUtil.getSysDate());

		medicalCaseRepo.save(medicalCase);
	}

	public void clear() {
		medicalCaseRepo.deleteAll();
	}
}
